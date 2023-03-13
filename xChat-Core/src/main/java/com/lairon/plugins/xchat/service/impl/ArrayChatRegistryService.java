package com.lairon.plugins.xchat.service.impl;

import com.lairon.plugins.xchat.Chat;
import com.lairon.plugins.xchat.service.ChatRegistryService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ArrayChatRegistryService implements ChatRegistryService {

    private final List<Chat> chats = new ArrayList<>();
    @Getter
    @Setter
    private Chat defaultChat;

    @Override
    public Chat getChat(char c) {
        return getChat(c, defaultChat);
    }

    @Override
    public Chat getChat(@NonNull String id) {
        return getChat(id, defaultChat);
    }

    @Override
    public Chat getChat(char c, Chat defaultChat) {
        return chats
                .stream()
                .filter(chat -> chat.getSymbol() == c)
                .findFirst()
                .orElse(defaultChat);
    }

    @Override
    public Chat getChat(@NonNull String id, Chat defaultChat) {
        return chats
                .stream()
                .filter(chat -> chat.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(defaultChat);
    }

    @Override
    public List<Chat> getChats() {
        List<Chat> list = new ArrayList<>(chats);
        if (defaultChat != null) list.add(defaultChat);
        return list;
    }

    @Override
    public void registerChat(@NonNull Chat chat) {
        if (getChat(chat.getId(), null) != null)
            throw new IllegalArgumentException("Chat " + chat.getId() + " is already registered");
        if (getChat(chat.getSymbol(), null) != null)
            throw new IllegalArgumentException("Chat with symbol " + chat.getSymbol() + " is already registered");
        chats.add(chat);
    }

    @Override
    public void clear() {
        chats.clear();
    }

    @Override
    public void unregisterChat(@NonNull Chat chat) {
        chats.remove(chat);
    }
}
