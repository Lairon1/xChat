package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public interface PlaceholderService {

    String setPlaceholders(@NonNull CommandSender player, @NonNull String message, @NonNull Map<String, String> placeholders);

    String setPlaceholders(@NonNull CommandSender player, @NonNull String message, @NonNull List<String> placeholders);
    String setPlaceholders(@NonNull CommandSender player, @NonNull String message, @NonNull String... placeholders);

}
