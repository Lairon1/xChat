package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.entity.CommandSender;
import lombok.NonNull;

public interface PrivateMessageService {

    void sendPrivateMessage(@NonNull CommandSender sender, @NonNull CommandSender recipient, @NonNull String message);

}
