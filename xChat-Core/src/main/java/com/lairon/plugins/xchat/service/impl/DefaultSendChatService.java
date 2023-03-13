package com.lairon.plugins.xchat.service.impl;

import com.lairon.plugins.xchat.AbstractPlayer;
import com.lairon.plugins.xchat.Chat;
import com.lairon.plugins.xchat.service.PlaceholderService;
import com.lairon.plugins.xchat.service.PlayerService;
import com.lairon.plugins.xchat.service.SendChatService;
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
        message = placeholderService.setPlaceholders(player, chat.getFormat(), "message", message, "player", player.getName());
        for (AbstractPlayer listener : listeners) {
            playerService.sendMessage(listener, message);
        }
    }
}
