package com.lairon.plugins.service;

import com.lairon.plugins.AbstractPlayer;
import lombok.NonNull;

import java.util.List;

public interface PlayerService {

    List<AbstractPlayer> getOnlinePlayers();

    List<AbstractPlayer> getPlayersWithRange(@NonNull AbstractPlayer player, int range);

    void sendMessage(@NonNull AbstractPlayer player, @NonNull String message);

}
