package com.lairon.plugins.xchat.listener;

import com.lairon.plugins.AbstractPlayer;
import com.lairon.plugins.handler.ChatHandler;
import com.lairon.plugins.handler.impl.DefaultChatHandler;
import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class ChatListener implements Listener {

    private final ChatHandler chatHandler;

    @EventHandler(ignoreCancelled = true)
    public void onAsyncChat(AsyncChatEvent event) {
        AbstractPlayer player = BukkitAdapter.adapt(event.getPlayer());
        String message = LegacyComponentSerializer.legacyAmpersand().serialize(event.message());
        if (player == null || message == null) return;
        event.setCancelled(true);
        chatHandler.handleChat(player, message);
    }
}
