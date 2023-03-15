package com.lairon.plugins.xchat.service.impl;

import com.lairon.plugins.xchat.Chat;
import com.lairon.plugins.xchat.service.ChatRegistryService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArrayChatRegistryService implements ChatRegistryService {

    private final List<Chat> chats = new ArrayList<>();

    @Override
    public Optional<Chat> findChat(char c) {
        return chats
                .stream()
                .filter(chat -> chat.getSymbol() == c)
                .findFirst();
    }

    @Override
    public Optional<Chat> findChat(@NonNull String id) {
        return chats
                .stream()
                .filter(chat -> chat.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    @Override
    public List<Chat> getChats() {
        List<Chat> list = new ArrayList<>(chats);
        return list;
    }

    @Override
    public void registerChat(@NonNull Chat chat) {
        if (findChat(chat.getId()).isPresent())
            throw new IllegalArgumentException("Chat " + chat.getId() + " is already registered");
        if (findChat(chat.getSymbol()).isPresent())
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
