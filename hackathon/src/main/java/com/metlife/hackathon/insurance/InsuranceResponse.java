package com.metlife.hackathon.insurance;

import lombok.Builder;

@Builder
public record InsuranceResponse(
        Long id,

        String name,
        String summary,

        String type,

        String paymentReason,

        String duration,

        String coverage,

        Long startAge,

        Long endAge,

        String paymentPeriod,

        String paymentCycle,

        String virtualName
) {

    public InsuranceResponse(Insurance insurance) {
        this(
                insurance.getId(),
                insurance.getName(),
                insurance.getSummary(),
                insurance.getType(),
                insurance.getPaymentReason(),
                insurance.getDuration(),
                insurance.getCoverage(),
                insurance.getStartAge(),
                insurance.getEndAge(),
                insurance.getPaymentPeriod(),
                insurance.getPaymentCycle(),
                insurance.getVirtualName()
        );
    }
}
