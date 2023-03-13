package com.lairon.plugins.service.impl;

import com.lairon.plugins.AbstractPlayer;
import com.lairon.plugins.Chat;
import com.lairon.plugins.service.PlaceholderService;
import com.lairon.plugins.service.PlayerService;
import com.lairon.plugins.service.SendChatService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DefaultSendChatService implements SendChatService {

    private final PlayerService playerService;
    private final PlaceholderService placeholderService;

    @Override
    public void sendOnChat(@NonNull AbstractPlayer player, @NonNull Chat chat, @NonNull String message) {
        List<AbstractPlayer> listeners = null;
        if (chat.getRange() < 0) {
            listeners = playerService.getOnlinePlayers();
        } else {
            listeners = playerService.getPlayersWithRange(player, chat.getRange());
        }
        message = placeholderService.setPlaceholders(player, chat.getFormat(), "message", message);
        for (AbstractPlayer listener : listeners) {
            playerService.sendMessage(listener, message);
        }
    }
}
