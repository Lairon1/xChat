package com.lairon.plugins.xchat.command.exception;

import com.lairon.plugins.xchat.entity.CommandSender;
import lombok.NonNull;

public class DontHavePermissionException extends Exception {
    public DontHavePermissionException(@NonNull CommandSender sender, @NonNull String permission) {
        super("Sender " + sender.getName() + " dont have permission " + permission);
    }
}
