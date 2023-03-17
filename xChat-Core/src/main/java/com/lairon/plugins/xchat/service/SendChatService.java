package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.Chat;
import lombok.NonNull;

public interface SendChatService {

    void sendOnChat(@NonNull Player player, @NonNull Chat chat, @NonNull String message);

}
