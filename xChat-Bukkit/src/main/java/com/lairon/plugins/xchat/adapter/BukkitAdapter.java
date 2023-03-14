package com.lairon.plugins.xchat.adapter;

import com.lairon.plugins.xchat.AbstractPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BukkitAdapter {

    public static AbstractPlayer adapt(@NotNull Player player){
        return new AbstractPlayer(player.getUniqueId(), player.getName(), player.getDisplayName());
    }

    public static Player adapt(@NotNull AbstractPlayer player){
        return Bukkit.getPlayer(player.getUuid());
    }

}
