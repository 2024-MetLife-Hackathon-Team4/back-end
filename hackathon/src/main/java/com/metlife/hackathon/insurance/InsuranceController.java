package com.metlife.hackathon.insurance;

import com.metlife.hackathon.controller.dto.InsuranceAnswerResponse;
import com.metlife.hackathon.controller.dto.InsuranceRequest;
import java.util.List;

import com.metlife.hackathon.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InsuranceController {

    private final InsuranceService insuranceService;
    private final ChatService chatService;

    @GetMapping("/insurance-categories")
    public List<InsuranceCategoryResponse> getCategories() {
        return insuranceService.getCategories();
    }

    @GetMapping("/insurances")
    public List<InsuranceResponse> getList(@RequestParam(required = false) Long categoryId) {
        return insuranceService.getList(categoryId);
    }

    @PostMapping("/extract")
    public ResponseEntity<InsuranceAnswerResponse> extractAnswers(@RequestBody InsuranceRequest insuranceRequest) {
        InsuranceAnswerResponse response = insuranceService.extract(insuranceRequest);
        return ResponseEntity.ok(response);
    }

    // @GetMapping("/extract2")
    // public ResponseEntity<String> extractAnswers2(@RequestParam InsuranceRequest insuranceRequest) {
    //     Insurance insurance = insuranceService.findByName(insuranceRequest.productName());
    //     String response = insuranceService.extract2(insurance, insuranceRequest);
    //     return ResponseEntity.ok(response);
    // }

    @PostMapping("/end")
    public ConversationEndResponse endConversation(@RequestBody ConversationEndRequest request) {
        return chatService.endConversation(request.memberId());
    }
}
