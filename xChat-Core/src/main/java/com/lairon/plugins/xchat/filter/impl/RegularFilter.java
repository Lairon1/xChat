package com.lairon.plugins.xchat.filter.impl;

import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.filter.ChatFilter;
import com.lairon.plugins.xchat.filter.FilterResponse;
import com.lairon.plugins.xchat.permission.Permissions;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class RegularFilter implements ChatFilter {

    private final Map<String, String> regulars;

    @Override
    public FilterResponse filter(@NonNull Player player, @NonNull String message) {
        for (Map.Entry<String, String> stringStringEntry : regulars.entrySet()) {
            if(message.matches(stringStringEntry.getKey())) return FilterResponse.message(stringStringEntry.getValue());
        }
        return FilterResponse.empty();
    }

    @Override
    public String bypassPermission() {
        return Permissions.BYPASS.REGULAR_BYPASS;
    }
}
