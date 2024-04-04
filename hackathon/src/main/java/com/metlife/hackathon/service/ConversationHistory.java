package com.metlife.hackathon.service;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ConversationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("회원 ID")
    private Long memberId;

    @Column
    @Comment("저장된 요청 및 응답")
    private String conversationHistory;

    @Column(nullable = false)
    @Comment("요약")
    private String summary;

    public ConversationHistory(Long memberId) {
        this.memberId = memberId;
        this.conversationHistory = "";
    }

    public void add(String conversation) {
        this.conversationHistory += " " + conversation;
    }

    public void updateSummary(String summary) {
        this.summary = summary;
    }
}
