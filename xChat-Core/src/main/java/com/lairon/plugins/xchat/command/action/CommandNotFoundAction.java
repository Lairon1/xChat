package com.lairon.plugins.xchat.command.action;

import com.lairon.plugins.xchat.entity.CommandSender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandNotFoundAction {

    private CommandSender commandSender;
    private String commandID;

}
