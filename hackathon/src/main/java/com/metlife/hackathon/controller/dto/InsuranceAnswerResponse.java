package com.metlife.hackathon.controller.dto;

import java.util.List;

public record InsuranceAnswerResponse(
        String id,
        List<String> answers,
        List<FeatureResponseDto> notContainsFeature
) {

    public static InsuranceAnswerResponse from(String id, List<String> answers, List<FeatureResponseDto> notContainsFeature) {
        return new InsuranceAnswerResponse(id,answers, notContainsFeature);
    }
}
