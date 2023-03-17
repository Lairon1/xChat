package com.lairon.plugins.xchat.command.impl;

import com.lairon.plugins.xchat.command.CommandSettings;
import com.lairon.plugins.xchat.command.SubCommand;
import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.permission.Permissions;
import com.lairon.plugins.xchat.service.PlayerService;
import lombok.NonNull;


public class DefaultCommand extends SubCommand {

    private final LangConfig langConfig;
    private final PlayerService playerService;

    public DefaultCommand(@NonNull LangConfig langConfig, @NonNull PlayerService playerService) {
        super(CommandSettings.
                builder()
                .id("default")
                .onlyPlayer(false)
                .permission(Permissions.COMMAND.DEFAULT_COMMAND)
                .build());
        this.langConfig = langConfig;
        this.playerService = playerService;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull String[] args) {
        playerService.sendMessage(sender, langConfig.getDefaultCommand());
        return false;
    }
}
