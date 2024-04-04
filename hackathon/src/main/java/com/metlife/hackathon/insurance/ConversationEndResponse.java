package com.metlife.hackathon.insurance;

import com.metlife.hackathon.service.ConversationHistory;

public record ConversationEndResponse(String summary) {

    public static final ConversationEndResponse EMPTY = new ConversationEndResponse("");

    public ConversationEndResponse(ConversationHistory conversationHistory) {
        this(conversationHistory.getSummary());
    }
}
