package com.lairon.plugins.xchat.handler.impl;

import com.lairon.plugins.xchat.AbstractPlayer;
import com.lairon.plugins.xchat.Chat;
import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.handler.ChatHandler;
import com.lairon.plugins.xchat.service.ChatRegistryService;
import com.lairon.plugins.xchat.service.PlayerService;
import com.lairon.plugins.xchat.service.SendChatService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultChatHandler implements ChatHandler {

    private final SendChatService sendChatService;
    private final PlayerService playerService;
    private final ChatRegistryService chatService;
    private final LangConfig lang;

    public void handleChat(@NonNull AbstractPlayer player, @NonNull String message){
        if(message.isEmpty()){
            playerService.sendMessage(player, lang.getYourMessageIsEmpty());
            return;
        }
        char c = message.charAt(0);
        Chat chat = chatService.findChat(c).orElse(chatService.getDefaultChat());
        if(chat == null){
            playerService.sendMessage(player, lang.getChatNotFound());
            return;
        }
        message = message.substring(1);
        sendChatService.sendOnChat(player, chat, message.trim());
    }

}
