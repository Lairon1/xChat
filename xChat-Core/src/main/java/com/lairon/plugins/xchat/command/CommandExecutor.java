package com.lairon.plugins.xchat.command;


import com.lairon.plugins.xchat.command.exception.DontHavePermissionException;
import com.lairon.plugins.xchat.command.exception.OnlyPlayerException;
import com.lairon.plugins.xchat.command.impl.DefaultCommand;
import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.service.PlaceholderService;
import com.lairon.plugins.xchat.service.PlayerService;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class CommandExecutor {

    private final CommandRegistry commandRegistry;
    private final PlayerService playerService;
    private final PlaceholderService placeholderService;
    private final LangConfig langConfig;
    private SubCommand defaultCommand;

    public CommandExecutor(@NonNull CommandRegistry commandRegistry,
                           @NonNull PlayerService playerService,
                           @NonNull PlaceholderService placeholderService,
                           @NonNull LangConfig langConfig) {
        this.commandRegistry = commandRegistry;
        this.playerService = playerService;
        this.placeholderService = placeholderService;
        this.langConfig = langConfig;
        defaultCommand = new DefaultCommand(langConfig, playerService);
    }

    public boolean onCommand(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 0) {
            if (defaultCommand != null) defaultCommand.onCommand(sender, args);
            return false;
        } else {
            SubCommand command = commandRegistry.getCommand(args[0]);
            if (command == null) {
                playerService.sendMessage(sender, placeholderService.setPlaceholders(sender, langConfig.getCommandNotFound()));
                return false;
            }
            try {
                if (isAvailableCommand(command, sender))
                    command.onCommand(sender, args);
            } catch (DontHavePermissionException e) {
                playerService.sendMessage(sender, placeholderService.setPlaceholders(sender, langConfig.getCommandNotFound()));
            } catch (OnlyPlayerException e) {
                playerService.sendMessage(sender, placeholderService.setPlaceholders(sender, langConfig.getOnlyPlayer()));
            }
        }
        return false;
    }

    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull String[] args) {
        List<String> tabs = new ArrayList<>();
        if (args.length == 1) {
            for (SubCommand command : commandRegistry.getCommands()) {
                try {
                    if (isAvailableCommand(command, sender))
                        tabs.add(command.getCommandSettings().getId());
                    tabs.addAll(command.getCommandSettings().getAlliances());
                } catch (Exception e) {
                }
            }
        } else {
            SubCommand command = commandRegistry.getCommand(args[0]);
            try {
                if (command != null && isAvailableCommand(command, sender)) {
                    List<String> strings = command.onTabComplete(sender, args);
                    if (strings != null)
                        tabs.addAll(strings);
                }
            } catch (Exception e) {
            }
        }
        return filter(tabs, args);
    }

    public boolean isAvailableCommand(@NonNull SubCommand command,
                                      @NonNull CommandSender sender) throws DontHavePermissionException, OnlyPlayerException {
        if (command.getCommandSettings().getPermission() != null && !playerService.hasPermission(sender, command.getCommandSettings().getPermission()))
            throw new DontHavePermissionException(sender, command.getCommandSettings().getPermission());
        if (command.getCommandSettings().isOnlyPlayer() && !(sender instanceof Player))
            throw new OnlyPlayerException(command);
        return true;
    }

    private List<String> filter(List<String> list, String[] args) {
        if (list == null) return null;
        if (list.isEmpty()) return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for (String arg : list) {
            if (arg.toLowerCase().contains(last.toLowerCase()))
                result.add(arg);
        }
        return result;
    }
}