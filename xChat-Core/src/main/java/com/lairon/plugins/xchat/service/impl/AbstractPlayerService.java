package com.lairon.plugins.xchat.service.impl;

import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.service.PlayerService;
import lombok.NonNull;

public abstract class AbstractPlayerService implements PlayerService {

    @Override
    public String getFormattedDisplayName(@NonNull CommandSender sender) {
        if(sender instanceof Player player){
            return player.getDisplayname().equals(player.getName()) ? player.getDisplayname() : "~" + player.getDisplayname();
        }else{
            return sender.getName();
        }
    }
}
