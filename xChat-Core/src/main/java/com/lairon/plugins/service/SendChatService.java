package com.lairon.plugins.service;

import com.lairon.plugins.AbstractPlayer;
import com.lairon.plugins.Chat;
import lombok.NonNull;

public interface SendChatService {

    void sendOnChat(@NonNull AbstractPlayer player, @NonNull Chat chat, @NonNull String message);

}
