package com.metlife.hackathon.insurance;

import com.metlife.hackathon.controller.dto.FeatureResponseDto;
import com.metlife.hackathon.controller.dto.InsuranceAnswerResponse;
import com.metlife.hackathon.controller.dto.InsuranceRequest;
import com.metlife.hackathon.feature.Feature;
import com.metlife.hackathon.feature.FeatureRepository;
import com.metlife.hackathon.member.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.metlife.hackathon.member.MemberRepository;
import com.metlife.hackathon.service.AnswerSummarizer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final InsuranceCategoryRepository categoryRepository;
    private final FeatureRepository featureRepository;
    final ChatClient client;
    private final AnswerSummarizer answerSummarizer;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<InsuranceResponse> getList(Long categoryId) {
        if (categoryId == null) {
            return insuranceRepository.findAll()
                    .stream()
                    .map(InsuranceResponse::new)
                    .toList();
        }
        return insuranceRepository.findAllByInsuranceCategory_Id(categoryId)
                .stream()
                .map(InsuranceResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InsuranceCategoryResponse> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(InsuranceCategoryResponse::new)
                .toList();
    }

    public Insurance findByName(String name) {
        return insuranceRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found: " + name));
    }

    @Transactional
    public InsuranceAnswerResponse extract(InsuranceRequest insuranceRequest) {
        Insurance insurance = findByName(insuranceRequest.productName());
        String memberId = insuranceRequest.id();
        if (memberId == null) {
            memberId = String.valueOf(UUID.randomUUID());
        }

        Member savedMember = Member.of(memberId);

        if(savedMember.getRealFeatures() == null) {
            savedMember.setRealFeatures(new ArrayList<>());
        }

        // gpt에 이거 제외하고 주세요..
        List<String> collect = featureRepository.findByInsuranceId(insurance.getId())
                .stream()
                .map(Feature::getFeature)
                .filter((data) -> savedMember.getRealFeatures().contains(data))
                .toList(); // 처음에 GPT로 반환된 결과 값이랑 저장되어있는 결과값이 같은 리스트 반환

        List<String> features = featureRepository.findByInsuranceId(insurance.getId())
                .stream()
                .map(Feature::getFeature)
                .toList(); // 모든 Feature 반환

        String featureList = "";
        for (String feature: features) {
            if (collect.contains(feature)) continue;
            if (!featureList.isEmpty()) featureList = featureList.concat(",");
            featureList = featureList.concat(feature.split(":")[0]);
        }

        String res = client.call(insuranceRequest.question() + "에 가장 가까운 특징을 알려줘. 특징값을 리턴해줘. 특징: " + featureList);


        //GPT에 collect만 보내기
        List<String> answers = new ArrayList<>();
        answers.add(res);

        // 얘를 다시 gpt에 물어봐서 방금대답이 어떤거에 해당되는지
        List<String> featureTitlesNotSaved = featureRepository.findByInsuranceId(insurance.getId())
            .stream()
            .map(Feature::getFeature)
            // .filter((data) -> !savedMember.getRealFeatures().contains(data))
            .map(data -> data.split(":")[0])
            .toList();

        System.out.println(featureTitlesNotSaved.size());
        for(String key : featureTitlesNotSaved) {
            System.out.println(key);
        }
        String res2 = "";
        for (String feature: featureTitlesNotSaved) {
            if (!res2.isEmpty()) res2 = res2.concat(",");
            res2 = res2.concat(feature.split(":")[0]);
        }

        String resAgain = client.call(res + " 이 응답이 다음 제목들 중에 어떤거에 해당되는지 알려줘. 제목: " + res2);

        System.out.println(resAgain);
        // 응답으로 받은거 저장하기
        String finalResAgain = resAgain;
        List<String> featureToBeSaved = featureRepository.findByInsuranceId(insurance.getId())
            .stream()
            .map(Feature::getFeature)
            .filter(data -> finalResAgain.contains(data.split(":")[0]))
            .toList();



        savedMember.getRealFeatures().addAll(featureToBeSaved.isEmpty() || featureToBeSaved == null ? new ArrayList<>() : featureToBeSaved);

       // 아직 응답이 안된 특장점들 넘겨주기
       List<FeatureResponseDto> notContains = featureRepository.findByInsuranceId(insurance.getId())
           .stream()
           .map(Feature::getFeature)
           .filter((data) -> !savedMember.getRealFeatures().contains(data))
           .filter(data -> !"res".contains(data.split(":")[0]))
           .map(FeatureResponseDto::of)
           .toList();

        memberRepository.save(savedMember);
        // List<FeatureResponseDto> notContains;

        return InsuranceAnswerResponse.from(memberId, answers, notContains);
    }

    // public String extract2(Insurance insurance, InsuranceRequest insuranceRequest) {
    //     String memberId = insuranceRequest.id();
    //
    //     Member savedMember = Member.of(memberId);
    //
    //     // gpt에 이거 제외하고 주세요..
    //     List<String> features = featureRepository.findByInsuranceId(insurance.getId())
    //             .stream()
    //             .map(Feature::getFeature)
    //             .filter((data) -> savedMember.getRealFeatures().contains(data))
    //             .toList(); // 처음에 GPT로 반환된 결과 값이랑 저장되어있는 결과값이 같은 리스트 반환
    //
    //     String featureList = "";
    //     for (String feature: features) {
    //         if (!featureList.isEmpty()) featureList = featureList.concat(",");
    //         featureList = featureList.concat(feature.split(":")[0]);
    //     }
    //
    //     return client.call(insuranceRequest.question() + "에 가장 가까운 특징을 알려줘. 특징값을 리턴해줘. 특징: " + featureList);
    // }
}
