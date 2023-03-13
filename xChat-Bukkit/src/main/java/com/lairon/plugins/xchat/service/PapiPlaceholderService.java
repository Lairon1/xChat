package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.AbstractPlayer;
import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import com.lairon.plugins.xchat.service.impl.placeholder.StrSubstitutorPlaceholderService;
import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.Map;

public class PapiPlaceholderService extends StrSubstitutorPlaceholderService {

    @Override
    public String setPlaceholders(@NonNull AbstractPlayer player, @NonNull String message, @NonNull Map<String, String> placeholders) {
        message = super.setPlaceholders(player, message, placeholders);
        Player bukkitPlayer = BukkitAdapter.adapt(player);
        if (bukkitPlayer != null) {
            message = PlaceholderAPI.setPlaceholders(bukkitPlayer, message);
        }
        return message;
    }
}
