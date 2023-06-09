package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Stream;

public interface PlayerService {

    List<Player> getOnlinePlayers();

    Stream<Player> getOnlinePlayersStream();

    List<Player> getPlayersWithRange(@NonNull Player player, int range);

    void sendMessage(@NonNull CommandSender recipient, @NonNull String message);

    boolean hasPermission(@NonNull CommandSender player, @NonNull String permission);

    String getFormattedDisplayName(@NonNull CommandSender sender);
}
