package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.Chat;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ChatRegistryService {

    Optional<Chat> findChat(char c);
    Optional<Chat> findChat(@NonNull String id);
    List<Chat> getChats();
    void registerChat(@NonNull Chat chat);
    void clear();
    void unregisterChat(@NonNull Chat chat);
    void setDefaultChat(@NonNull Chat chat);
    Chat getDefaultChat();

}
