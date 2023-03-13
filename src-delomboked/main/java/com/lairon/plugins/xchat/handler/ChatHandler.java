package com.lairon.plugins.xchat.handler;

import com.lairon.plugins.xchat.AbstractPlayer;
import lombok.NonNull;

public interface ChatHandler {
    void handleChat(@NonNull AbstractPlayer player, @NonNull String message);
}
