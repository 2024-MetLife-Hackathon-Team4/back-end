package com.metlife.hackathon.insurance;

public record InsuranceCategoryResponse(Long id, String name) {

    public InsuranceCategoryResponse(InsuranceCategory insuranceCategory) {
        this(insuranceCategory.getId(), insuranceCategory.getName());
    }
}
