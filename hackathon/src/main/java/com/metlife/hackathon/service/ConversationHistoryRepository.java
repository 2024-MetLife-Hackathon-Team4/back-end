package com.metlife.hackathon.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationHistoryRepository extends JpaRepository<ConversationHistory, Long> {

    Optional<ConversationHistory> findByMemberId(Long memberId);
}
