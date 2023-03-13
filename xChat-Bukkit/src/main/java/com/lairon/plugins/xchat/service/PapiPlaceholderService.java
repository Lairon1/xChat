package com.lairon.plugins.xchat.service;

import com.lairon.libs.xmessageutils.MessageUtils;
import com.lairon.plugins.AbstractPlayer;
import com.lairon.plugins.service.impl.AbstractPlaceholderService;
import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.Map;

public class PapiPlaceholderService extends AbstractPlaceholderService {

    @Override
    public String setPlaceholders(@NonNull AbstractPlayer player, @NonNull String message, @NonNull Map<String, String> placeholders) {
        message = MessageUtils.applyColors(MessageUtils.applyPlaceholders(message, placeholders));
        Player bukkitPlayer = BukkitAdapter.adapt(player);
        if (bukkitPlayer != null)
            message = PlaceholderAPI.setPlaceholders(bukkitPlayer, message);
        return message;
    }

}
