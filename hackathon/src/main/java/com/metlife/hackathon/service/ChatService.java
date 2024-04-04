package com.metlife.hackathon.service;

import com.metlife.hackathon.insurance.ConversationEndResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ConversationHistoryRepository conversationHistoryRepository;

    public ConversationEndResponse endConversation(Long memberId) {
        return conversationHistoryRepository.findByMemberId(memberId)
                .map(ConversationEndResponse::new)
                .orElse(ConversationEndResponse.EMPTY);
    }
}
