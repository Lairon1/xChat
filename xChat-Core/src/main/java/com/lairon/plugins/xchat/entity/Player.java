package com.lairon.plugins.xchat.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Player extends CommandSender {


    private final String displayname;
    private boolean socialSpyEnabled = true;

    public Player(@NonNull UUID uuid, @NonNull String name, @NonNull String displayname) {
        super(uuid, name);
        this.displayname = displayname;
    }


}
