package com.lairon.plugins.xchat;

import lombok.Data;

import java.util.UUID;

@Data
public class AbstractPlayer {

    private final UUID uuid;
    private final String name;

}
