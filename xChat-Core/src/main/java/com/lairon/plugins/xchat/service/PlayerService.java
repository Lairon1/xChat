package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.AbstractPlayer;
import lombok.NonNull;

import java.util.List;

public interface PlayerService {

    List<AbstractPlayer> getOnlinePlayers();

    List<AbstractPlayer> getPlayersWithRange(@NonNull AbstractPlayer player, int range);

    void sendMessage(@NonNull AbstractPlayer player, @NonNull String message);

    boolean hasPermission(@NonNull AbstractPlayer player, @NonNull String permission);

}
