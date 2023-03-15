package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.AbstractPlayer;
import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BukkitPlayerService implements PlayerService {
    @Override
    public List<AbstractPlayer> getOnlinePlayers() {
        return Bukkit
                .getOnlinePlayers()
                .stream()
                .map(BukkitAdapter::adapt)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbstractPlayer> getPlayersWithRange(@NonNull AbstractPlayer player, int range) {
        Player bukkitPlayer = BukkitAdapter.adapt(player);
        if(player == null) return new ArrayList<>();
        Location location = bukkitPlayer.getLocation();
        return Bukkit.getOnlinePlayers()
                .stream()
                .filter(op -> op.getLocation().distance(location) <= range)
                .map(BukkitAdapter::adapt)
                .collect(Collectors.toList());
    }

    @Override
    public void sendMessage(@NonNull AbstractPlayer player, @NonNull String message) {
        if(message.isEmpty()) return;
        Player bukkitPlayer = BukkitAdapter.adapt(player);
        if (bukkitPlayer == null) return;
        TextComponent deserialize = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        MiniMessage build = MiniMessage.builder().build();
        bukkitPlayer.sendMessage(build.deserialize(build.serialize(deserialize)));
    }

    @Override
    public boolean hasPermission(@NonNull AbstractPlayer player, @NonNull String permission) {
        Player bukkitPlayer = BukkitAdapter.adapt(player);
        if(bukkitPlayer == null) return false;
        return bukkitPlayer.hasPermission(permission);
    }
}
