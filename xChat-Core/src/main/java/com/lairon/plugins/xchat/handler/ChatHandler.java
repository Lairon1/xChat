package com.lairon.plugins.xchat.handler;

import com.lairon.plugins.xchat.entity.Player;
import lombok.NonNull;

public interface ChatHandler {
    void handleChat(@NonNull Player player, @NonNull String message);
}
