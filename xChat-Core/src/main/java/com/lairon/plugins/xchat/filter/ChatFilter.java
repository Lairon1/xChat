package com.lairon.plugins.xchat.filter;

import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import lombok.NonNull;

public interface ChatFilter {

    FilterResponse filter(@NonNull CommandSender sender, @NonNull String message);
    String bypassPermission();

}
