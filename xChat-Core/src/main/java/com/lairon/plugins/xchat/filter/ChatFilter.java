package com.lairon.plugins.xchat.filter;

import com.lairon.plugins.xchat.AbstractPlayer;
import lombok.NonNull;

public interface ChatFilter {

    boolean filter(@NonNull AbstractPlayer player, @NonNull String message);
    String message();
    String bypassPermission();

}
