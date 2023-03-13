package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.AbstractPlayer;
import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import com.lairon.plugins.xchat.service.PlayerService;
import lombok.NonNull;
import org.bukkit.Bukkit;
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
        return bukkitPlayer
                .getNearbyEntities(range, range, range)
                .stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> BukkitAdapter.adapt((Player) entity))
                .collect(Collectors.toList());
    }

    @Override
    public void sendMessage(@NonNull AbstractPlayer player, @NonNull String message) {
        Player bukkitPlayer = BukkitAdapter.adapt(player);
        if(bukkitPlayer == null) return;
        bukkitPlayer.sendMessage(message);
    }
}
