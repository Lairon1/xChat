package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.AbstractPlayer;
import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import com.lairon.plugins.xchat.service.PlayerService;
import lombok.NonNull;
import me.minidigger.minimessage.MiniMessageParser;
import net.kyori.text.Component;
import net.kyori.text.adapter.bukkit.TextAdapter;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
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
        Player bukkitPlayer = BukkitAdapter.adapt(player);
        if(bukkitPlayer == null) return;
        bukkitPlayer.sendMessage(LegacyComponentSerializer.INSTANCE.serialize(MiniMessageParser.parseFormat(ChatColor.translateAlternateColorCodes('&', message))));
    }
}
