package com.lairon.plugins.xchat.service.impl.placeholder;

import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.service.PlaceholderService;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPlaceholderService implements PlaceholderService {

    @Override
    public String setPlaceholders(@NonNull CommandSender player, @NonNull String message, @NonNull List<String> placeholders) {
        Map<String, String> placeholdersMap = new HashMap<>();
        if (placeholders.size() % 2 != 0)
            throw new IllegalArgumentException("Each placeholder must have a value");
        for (int i = 0; i < placeholders.size(); i++) {
            String placeholder = placeholders.get(i);
            String value = placeholders.get(++i);
            placeholdersMap.put(placeholder, value);
        }
        return setPlaceholders(player, message, placeholdersMap);
    }

    @Override
    public String setPlaceholders(@NonNull CommandSender player, @NonNull String message, @NonNull String... placeholders) {
        Map<String, String> placeholdersMap = new HashMap<>();
        if (placeholders.length % 2 != 0)
            throw new IllegalArgumentException("Each placeholder must have a value");
        for (int i = 0; i < placeholders.length; i++) {
            String placeholder = placeholders[i];
            String value = placeholders[++i];
            placeholdersMap.put(placeholder, value);
        }
        return setPlaceholders(player, message, placeholdersMap);
    }

}
