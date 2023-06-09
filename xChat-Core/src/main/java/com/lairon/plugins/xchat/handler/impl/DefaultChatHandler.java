package com.lairon.plugins.xchat.handler.impl;

import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.Chat;
import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.config.SettingsConfig;
import com.lairon.plugins.xchat.filter.ChatFilter;
import com.lairon.plugins.xchat.filter.FilterResponse;
import com.lairon.plugins.xchat.handler.ChatHandler;
import com.lairon.plugins.xchat.service.ChatRegistryService;
import com.lairon.plugins.xchat.service.PlaceholderService;
import com.lairon.plugins.xchat.service.PlayerService;
import com.lairon.plugins.xchat.service.SendChatService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultChatHandler implements ChatHandler {

    private final SendChatService sendChatService;
    private final PlayerService playerService;
    private final PlaceholderService placeholderService;
    private final ChatRegistryService chatService;
    private final SettingsConfig settings;
    private final LangConfig lang;

    public void handleChat(@NonNull Player player, @NonNull String message){
        if(message.isEmpty()){
            playerService.sendMessage(player, placeholderService.setPlaceholders(player, lang.getYourMessageIsEmpty()));
            return;
        }
        char c = message.charAt(0);
        Chat chat = chatService.findChat(c).orElse(chatService.findChat(settings.getDefaultChatID()).orElse(null));
        if (chat == null) {
            playerService.sendMessage(player, placeholderService.setPlaceholders(player, lang.getChatNotFound()));
            return;
        }
        if (message.charAt(0) == chat.getSymbol())
            message = message.substring(1);

        for (ChatFilter filter : chat.getFilters()) {
            FilterResponse response = filter.filter(player, message);
            if(playerService.hasPermission(player, filter.bypassPermission())) continue;
            if (response.result()) {
                playerService.sendMessage(player, placeholderService.setPlaceholders(player, response.message()));
                return;
            }
        }

        sendChatService.sendOnChat(player, chat, message.trim());
    }

}
