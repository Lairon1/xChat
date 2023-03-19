package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import com.lairon.plugins.xchat.data.DataProvider;
import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class BukkitPlayerService implements PlayerService {

    private final DataProvider provider;

    @Override
    public List<Player> getOnlinePlayers() {
        return getOnlinePlayersStream().collect(Collectors.toList());
    }

    @Override
    public Stream<Player> getOnlinePlayersStream() {
        return Bukkit
                .getOnlinePlayers()
                .stream()
                .map(player -> provider.loadPlayer(player.getUniqueId()).orElse(BukkitAdapter.createNewPlayer(player)));
    }

    @Override
    public List<Player> getPlayersWithRange(@NonNull Player player, int range) {
        org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
        if (player == null) return new ArrayList<>();
        Location location = bukkitPlayer.getLocation();
        return Bukkit.getOnlinePlayers().stream()
                .filter(op -> op.getWorld() == location.getWorld() && op.getLocation().distance(location) <= range)
                .map(playerMap -> provider.loadPlayer(playerMap.getUniqueId()).orElse(BukkitAdapter.createNewPlayer(playerMap)))
                .collect(Collectors.toList());
    }

    @Override
    public void sendMessage(@NonNull CommandSender recipient, @NonNull String message) {
        if (message.isEmpty()) return;

        TextComponent deserialize = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        MiniMessage build = MiniMessage.builder().build();
        Component finalMessage = build.deserialize(build.serialize(deserialize));
        if (recipient instanceof Player player) {
            org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
            if (bukkitPlayer == null) return;
            bukkitPlayer.sendMessage(finalMessage);
        } else if (recipient instanceof CommandSender) {
            Bukkit.getConsoleSender().sendMessage(finalMessage);
        }
    }

    @Override
    public boolean hasPermission(@NonNull CommandSender player, @NonNull String permission) {
        if(player instanceof Player player1){
            org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player1);
            if (bukkitPlayer == null) return false;
            return bukkitPlayer.hasPermission(permission);
        } else if (player instanceof CommandSender) {
            return Bukkit.getConsoleSender().hasPermission(permission);
        }
        return false;
    }

    @Override
    public String getFormattedDisplayName(@NonNull CommandSender sender) {
        if(sender instanceof Player player){
            org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
            return bukkitPlayer.getName().equals(bukkitPlayer.getDisplayName()) ? bukkitPlayer.getName() : "~" + bukkitPlayer.getDisplayName();
        }
        return sender.getName();
    }
}
