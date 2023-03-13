package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.AbstractPlayer;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public interface PlaceholderService {

    String setPlaceholders(@NonNull AbstractPlayer player, @NonNull String message, @NonNull Map<String, String> placeholders);

    String setPlaceholders(@NonNull AbstractPlayer player, @NonNull String message, @NonNull List<String> placeholders);
    String setPlaceholders(@NonNull AbstractPlayer player, @NonNull String message, @NonNull String... placeholders);

}
