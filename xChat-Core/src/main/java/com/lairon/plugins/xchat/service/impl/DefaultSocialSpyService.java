package com.lairon.plugins.xchat.service.impl;

import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.permission.Permissions;
import com.lairon.plugins.xchat.service.PlaceholderService;
import com.lairon.plugins.xchat.service.PlayerService;
import com.lairon.plugins.xchat.service.SocialSpyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DefaultSocialSpyService implements SocialSpyService {

    private final PlayerService playerService;
    private final LangConfig langConfig;
    private final PlaceholderService placeholderService;

    @Override
    public void sendToSocialSpy(@NonNull List<CommandSender> excluded, @NonNull String message) {
        playerService
                .getOnlinePlayersStream()
                .filter(player ->
                        playerService.hasPermission(player, Permissions.CHAT.SOCIAL_SPY) &&
                        player.isSocialSpyEnabled() && !excluded.contains(player))
                .forEach(player -> {
                    playerService.sendMessage(player,
                            placeholderService.setPlaceholders(player, langConfig.getSocialSpyFormat(), "message", message));
                });
    }

}
