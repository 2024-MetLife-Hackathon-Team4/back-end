package com.metlife.hackathon.insurance;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("보험명")
    private String name;

    @Column(columnDefinition = "text")
    @Comment("보험 요약")
    private String summary;

    @Column(nullable = false)
    @Comment("보험 유형")
    private String type;

    @Column(nullable = false)
    @Comment("지급 사유")
    private String paymentReason;

    @Column(nullable = false)
    @Comment("보험 기간")
    private String duration;

    @Column(nullable = false)
    @Comment("보장 내용")
    private String coverage;

    @Column(nullable = false)
    @Comment("가입 가능 시작 나이")
    private Long startAge;

    @Column(nullable = false)
    @Comment("가입 가능 종료 나이")
    private Long endAge;

    @Column(nullable = false)
    @Comment("납입 기간")
    private String paymentPeriod;

    @Column(nullable = false)
    @Comment("납입 주기")
    private String paymentCycle;

    @Column(nullable = false)
    @Comment("가상 보험 명")
    private String virtualName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "insurance_category_id")
    private InsuranceCategory insuranceCategory;

    @Builder
    public Insurance(String name,
                     String summary,
                     String type,
                     String paymentReason,
                     String duration,
                     String coverage,
                     Long startAge,
                     Long endAge,
                     String paymentPeriod,
                     String paymentCycle,
                     String virtualName,
                     InsuranceCategory insuranceCategory) {
        this.name = name;
        this.summary = summary;
        this.type = type;
        this.paymentReason = paymentReason;
        this.duration = duration;
        this.coverage = coverage;
        this.startAge = startAge;
        this.endAge = endAge;
        this.paymentPeriod = paymentPeriod;
        this.paymentCycle = paymentCycle;
        this.virtualName = virtualName;
        this.insuranceCategory = insuranceCategory;
    }
}
