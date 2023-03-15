package com.lairon.plugins.xchat.filter;

import com.lairon.plugins.xchat.AbstractPlayer;
import lombok.NonNull;

public interface ChatFilter {

    FilterResponse filter(@NonNull AbstractPlayer player, @NonNull String message);
    String bypassPermission();

}
