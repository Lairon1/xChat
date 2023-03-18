package com.lairon.plugins.xchat.handler;

import com.lairon.plugins.xchat.entity.CommandSender;
import lombok.NonNull;

public interface PrivateMessageHandler {

    void handlePrivateMessage(@NonNull CommandSender sender, @NonNull CommandSender recipient, @NonNull String message);

}
