package com.lairon.plugins.handler;

import com.lairon.plugins.AbstractPlayer;
import lombok.NonNull;

public interface ChatHandler {
    void handleChat(@NonNull AbstractPlayer player, @NonNull String message);
}
