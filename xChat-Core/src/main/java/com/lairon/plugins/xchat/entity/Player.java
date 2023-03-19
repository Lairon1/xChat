package com.lairon.plugins.xchat.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Player extends CommandSender {

    private boolean socialSpyEnabled = false;
    private List<UUID> ignoredPlayers = new ArrayList<>();

    public Player(@NonNull UUID uuid, @NonNull String name) {
        super(uuid, name);
    }


}
