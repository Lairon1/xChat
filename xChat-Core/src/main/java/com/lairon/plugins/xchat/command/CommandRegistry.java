package com.lairon.plugins.xchat.command;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry {

    private Map<Class<? extends SubCommand>, SubCommand> commands = new HashMap<>();
    private Map<String, SubCommand> commandByID = new HashMap<>();

    public void registerCommand(@NonNull SubCommand command) {
        if (getCommand(command) != null)
            throw new IllegalArgumentException("Command " + command.getCommandSettings().getId() + " is already registered");
        commands.put(command.getClass(), command);
        commandByID.put(command.getCommandSettings().getId(), command);
        for (String alliance : command.getCommandSettings().getAlliances()) {
            commandByID.put(alliance, command);
        }
    }

    public void unregisterCommand(@NonNull SubCommand command) {
        commands.remove(command.getClass());
        List<String> alliances = new ArrayList<>();
        for (Map.Entry<String, SubCommand> commandEntry : commandByID.entrySet()) {
            if (commandEntry.getValue() == command) alliances.add(commandEntry.getKey());
        }
        alliances.forEach(commandByID::remove);
    }

    public SubCommand getCommand(@NonNull SubCommand command) {
        return getCommand(command.getCommandSettings().getId());
    }

    public SubCommand getCommand(@NonNull String id) {
        return commandByID
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(id))
                .map(entry -> entry.getValue())
                .findFirst()
                .orElse(null);
    }

    public SubCommand getCommand(@NonNull Class<? extends SubCommand> aClass) {
        return commands.get(aClass);
    }

    public List<SubCommand> getCommands() {
        return new ArrayList<>(commands.values());
    }
}
