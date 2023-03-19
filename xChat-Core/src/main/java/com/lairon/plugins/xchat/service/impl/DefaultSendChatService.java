package com.lairon.plugins.xchat.service.impl;

import com.lairon.plugins.xchat.Chat;
import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.service.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultSendChatService implements SendChatService {

    private final PlayerService playerService;
    private final PlaceholderService placeholderService;
    private final SocialSpyService socialSpyService;
    private final ConsoleService consoleService;

    @Override
    public void sendOnChat(@NonNull Player player, @NonNull Chat chat, @NonNull String message) {
        List<Player> listeners = null;
        if (chat.getRange() < 0) {
            listeners = switch (chat.getRange()) {
                case Chat.GLOBAL_RANGE -> playerService.getOnlinePlayers();
                default -> playerService.getPlayersWithRange(player, Integer.MAX_VALUE);
            };
        } else {
            listeners = playerService.getPlayersWithRange(player, chat.getRange());
        }

        listeners.removeIf(listener -> listener.getIgnoredPlayers().contains(player.getUuid()));

        message = placeholderService.setPlaceholders(player, chat.getFormat(),
                "message", message,
                "name", player.getName(),
                "displayname", playerService.getFormattedDisplayName(player));
        for (Player listener : listeners) {
            playerService.sendMessage(listener, message);
        }
        socialSpyService.sendToSocialSpy(listeners.stream().map(p -> (CommandSender) p).collect(Collectors.toList()), message);
        consoleService.sendToConsole(message);
    }
}
