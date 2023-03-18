package com.lairon.plugins.xchat.service.impl;

import com.lairon.plugins.xchat.service.ConsoleService;
import lombok.NonNull;

public class DefaultConsoleService implements ConsoleService {
    @Override
    public void sendToConsole(@NonNull String message) {
        System.out.println(message);
    }

}
