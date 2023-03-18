package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BukkitConsoleService implements ConsoleService {

    private final PlayerService playerService;

    @Override
    public void sendToConsole(@NonNull String message) {
        playerService.sendMessage(BukkitAdapter.consoleSender(), message);
    }

}
