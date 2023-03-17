package com.lairon.plugins.xchat.command;

import com.lairon.plugins.xchat.entity.CommandSender;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public abstract class SubCommand {

    private final CommandSettings commandSettings;

    public abstract boolean onCommand(@NonNull CommandSender sender, @NonNull String[] args);

    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }

}
