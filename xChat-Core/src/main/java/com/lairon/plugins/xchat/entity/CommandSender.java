package com.lairon.plugins.xchat.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class CommandSender {

    private final UUID uuid;
    private final String name;

}
