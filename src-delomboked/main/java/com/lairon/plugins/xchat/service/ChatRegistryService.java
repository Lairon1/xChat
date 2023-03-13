package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.Chat;
import lombok.NonNull;

import java.util.List;

public interface ChatRegistryService {

    Chat getChat(char c);

    Chat getChat(@NonNull String id);
    Chat getChat(char c, Chat defaultChat);
    Chat getChat(@NonNull String id, Chat defaultChat);
    List<Chat> getChats();
    void registerChat(@NonNull Chat chat);
    void clear();
    void unregisterChat(@NonNull Chat chat);
    void setDefaultChat(@NonNull Chat chat);
    Chat getDefaultChat();

}
