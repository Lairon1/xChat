package com.lairon.plugins.xchat.command.impl;

import com.lairon.plugins.xchat.command.CommandSettings;
import com.lairon.plugins.xchat.command.SubCommand;
import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.permission.Permissions;
import com.lairon.plugins.xchat.service.PlaceholderService;
import com.lairon.plugins.xchat.service.PlayerService;
import com.lairon.plugins.xchat.service.ReloadConfigurationsService;
import lombok.NonNull;

import java.util.List;

public class ReloadCommand extends SubCommand {

    private final ReloadConfigurationsService handler;
    private final LangConfig langConfig;
    private final PlayerService playerService;
    private final PlaceholderService placeholderService;

    public ReloadCommand(@NonNull ReloadConfigurationsService handler,
                         @NonNull LangConfig langConfig,
                         @NonNull PlayerService playerService,
                         @NonNull PlaceholderService placeholderService) {
        super(CommandSettings
                .builder()
                .id("reload")
                .permission(Permissions.COMMAND.RELOAD)
                .alliances(List.of("restart"))
                .onlyPlayer(false)
                .build());
        this.handler = handler;
        this.langConfig = langConfig;
        this.playerService = playerService;
        this.placeholderService = placeholderService;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull String[] args) {
        try {
            long time = handler.reload(sender);
            playerService.sendMessage(sender, placeholderService.setPlaceholders(
                    sender,
                    langConfig.getReloadSuccessfully(),
                    "time", String.valueOf(time)
            ));
        } catch (Exception e) {
            playerService.sendMessage(sender, placeholderService.setPlaceholders(
                    sender,
                    langConfig.getReloadError(),
                    "error", e.getClass().getSimpleName()
            ));
            throw new RuntimeException(e);
        }
        return false;
    }
}
