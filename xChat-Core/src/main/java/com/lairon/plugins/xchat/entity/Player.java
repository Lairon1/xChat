package com.lairon.plugins.xchat.entity;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public class Player extends CommandSender {


    private final String displayname;

    public Player(@NonNull UUID uuid, @NonNull String name, @NonNull String displayname) {
        super(uuid, name);
        this.displayname = displayname;
    }


}
