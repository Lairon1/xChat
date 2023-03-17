package com.lairon.plugins.xchat.filter.impl;

import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.filter.ChatFilter;
import com.lairon.plugins.xchat.filter.FilterResponse;
import com.lairon.plugins.xchat.permission.Permissions;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CapsFilter implements ChatFilter {

    private final double ratio;
    private final String message;

    @Override
    public FilterResponse filter(@NonNull Player player, @NonNull String message) {
        int upperChars = 0;
        for (char c : message.toCharArray()) {
            if (Character.isUpperCase(c)) upperChars++;
        }
        boolean result = ((double) upperChars) / ((double) message.length()) > ratio;
        return new FilterResponse(result, this.message);
    }


    @Override
    public String bypassPermission() {
        return Permissions.BYPASS.CAPS_BYPASS;
    }
}
