package com.lairon.plugins.xchat.handler.impl;

import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.config.SettingsConfig;
import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.filter.ChatFilter;
import com.lairon.plugins.xchat.filter.FilterResponse;
import com.lairon.plugins.xchat.handler.PrivateMessageHandler;
import com.lairon.plugins.xchat.service.PlaceholderService;
import com.lairon.plugins.xchat.service.PlayerService;
import com.lairon.plugins.xchat.service.PrivateMessageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultPrivateMessageHandler implements PrivateMessageHandler {

    private final PlayerService playerService;
    private final PrivateMessageService privateMessageService;
    private final PlaceholderService placeholderService;
    private final LangConfig lang;
    private final SettingsConfig settings;

    @Override
    public void handlePrivateMessage(@NonNull CommandSender sender, @NonNull CommandSender recipient, @NonNull String message) {
        if (message.isEmpty()) {
            playerService.sendMessage(sender, placeholderService.setPlaceholders(sender, lang.getYourMessageIsEmpty()));
            return;
        }
        for (ChatFilter filter : settings.getChatFilters()) {
            FilterResponse response = filter.filter(sender, message);
            if (playerService.hasPermission(sender, filter.bypassPermission())) continue;
            if (response.result()) {
                playerService.sendMessage(sender, placeholderService.setPlaceholders(sender, response.message()));
                return;
            }
        }
        privateMessageService.sendPrivateMessage(sender, recipient, message);
    }


}
