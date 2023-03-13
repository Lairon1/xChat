package com.lairon.plugins.service;

import com.lairon.plugins.Chat;
import lombok.NonNull;

import java.util.List;

public interface ChatRegistryService {

    Chat getChat(char c);

    Chat getChat(@NonNull String id);

    List<Chat> getChats();
    void registerChat(@NonNull Chat chat);
    void clear();
    void unregisterChat(@NonNull Chat chat);
    void setDefaultChat(@NonNull Chat chat);
    Chat getDefaultChat();

}
