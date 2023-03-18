package com.lairon.plugins.xchat.adapter;

import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BukkitAdapter {

    public static Player adapt(@NotNull org.bukkit.entity.Player player){

        return new Player(player.getUniqueId(), player.getName(), player.getDisplayName());
    }

    public static org.bukkit.entity.Player adapt(@NotNull Player player) {
        return Bukkit.getPlayer(player.getUuid());
    }

    public static CommandSender consoleSender() {
        return new CommandSender(UUID.randomUUID(), "Console");
    }

    public static CommandSender adapt(@NotNull org.bukkit.command.CommandSender commandSender) {
        if (commandSender instanceof org.bukkit.entity.Player player) {
            return adapt(player);
        } else {
            return consoleSender();
        }
    }

}
