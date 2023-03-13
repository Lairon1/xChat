package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.AbstractPlayer;
import com.lairon.plugins.xchat.Chat;
import lombok.NonNull;

public interface SendChatService {

    void sendOnChat(@NonNull AbstractPlayer player, @NonNull Chat chat, @NonNull String message);

}
