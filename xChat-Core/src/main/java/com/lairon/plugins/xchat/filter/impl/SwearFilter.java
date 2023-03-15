package com.lairon.plugins.xchat.filter.impl;

import com.lairon.plugins.xchat.AbstractPlayer;
import com.lairon.plugins.xchat.filter.ChatFilter;
import com.lairon.plugins.xchat.filter.FilterResponse;
import com.lairon.plugins.xchat.permission.Permissions;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SwearFilter implements ChatFilter {

    private final List<String> swears;
    private final String message;


    @Override
    public FilterResponse filter(@NonNull AbstractPlayer player, @NonNull String message) {
        return new FilterResponse(swearContains(message), this.message);
    }

    private boolean isSwear(@NonNull String word) {
        System.out.println(word);
        return swears.contains(word.toLowerCase());
    }

    private boolean swearContains(@NonNull String message) {
        for (String word : message.trim().split(" ")) {
            if (isSwear(word.trim())) return true;
        }
        return false;
    }


    @Override
    public String bypassPermission() {
        return Permissions.BYPASS.SWEAR_BYPASS;
    }
}
