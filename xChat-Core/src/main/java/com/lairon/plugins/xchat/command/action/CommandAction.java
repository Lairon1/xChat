package com.lairon.plugins.xchat.command.action;

import com.lairon.plugins.xchat.command.SubCommand;
import com.lairon.plugins.xchat.entity.CommandSender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandAction {

    private final CommandSender commandSender;
    private final SubCommand command;

}
