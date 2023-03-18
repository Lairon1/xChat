package com.lairon.plugins.xchat.service.impl;

import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.service.PlaceholderService;
import com.lairon.plugins.xchat.service.PlayerService;
import com.lairon.plugins.xchat.service.PrivateMessageService;
import com.lairon.plugins.xchat.service.SocialSpyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class DefaultPrivateMessageService implements PrivateMessageService {

    private final LangConfig langConfig;
    private final PlayerService playerService;
    private final PlaceholderService placeholderService;
    private final SocialSpyService socialSpyService;

    @Override
    public void sendPrivateMessage(@NonNull CommandSender sender, @NonNull CommandSender recipient, @NonNull String message) {
        String privateMessageFormat = langConfig.getPrivateMessageFormat();
        String senderMessage = placeholderService.setPlaceholders(sender, privateMessageFormat, Map.of(
                "sender", playerService.getFormattedDisplayName(sender),
                "recipient", playerService.getFormattedDisplayName(recipient),
                "message", message
        ));
        String recipientMessage = placeholderService.setPlaceholders(recipient, privateMessageFormat, Map.of(
                "sender", playerService.getFormattedDisplayName(sender),
                "recipient", playerService.getFormattedDisplayName(recipient),
                "message", message
        ));
        playerService.sendMessage(sender, senderMessage);
        playerService.sendMessage(recipient, recipientMessage);
        socialSpyService.sendToSocialSpy(List.of(sender, recipient), message);
    }

}
