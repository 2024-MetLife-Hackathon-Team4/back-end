package com.metlife.hackathon.service;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class AnswerSummarizer {

    private final ConversationHistoryRepository conversationHistoryRepository;
    private final ChatClient chatClient;

    @Async
    @Transactional
    public void summarize(Long memberId, String conversation) {
        ConversationHistory conversationHistory = conversationHistoryRepository.findByMemberId(memberId)
                .orElse(new ConversationHistory(memberId));
        conversationHistory.add(conversation);
        String summary = chatClient.call("summarize " + conversationHistory.getConversationHistory());
        conversationHistory.updateSummary(summary);
    }
}
