package com.lairon.plugins.xchat.command.exception;

import com.lairon.plugins.xchat.command.SubCommand;
import lombok.NonNull;

public class OnlyPlayerException extends Exception {

    public OnlyPlayerException(@NonNull SubCommand command) {
        super("The " + command.getCommandSettings().getId() + " command can only be executed by the player");
    }

}
