package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import com.lairon.plugins.xchat.service.impl.placeholder.StrSubstitutorPlaceholderService;
import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.Map;

public class PapiPlaceholderService extends StrSubstitutorPlaceholderService {

    @Override
    public String setPlaceholders(@NonNull CommandSender sender, @NonNull String message, @NonNull Map<String, String> placeholders) {
        message = super.setPlaceholders(sender, message, placeholders);
        if(sender instanceof Player player){
            org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
            if (bukkitPlayer != null) {
                message = PlaceholderAPI.setPlaceholders(bukkitPlayer, message);
            }
        }
        return message;
    }
}
