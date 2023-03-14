package com.lairon.plugins.xchat.listener;

import com.lairon.plugins.xchat.AbstractPlayer;
import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import com.lairon.plugins.xchat.handler.ChatHandler;
import com.lairon.plugins.xchat.permission.BukkitPermissions;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import me.minidigger.minimessage.MiniMessageParser;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ChatListener implements EventExecutor, Listener {

    private final ChatHandler chatHandler;

    public void onAsyncChat(AsyncChatEvent event) {
        AbstractPlayer player = BukkitAdapter.adapt(event.getPlayer());
        String message = LegacyComponentSerializer.legacyAmpersand().serialize(event.message());
        if (player == null || message == null) return;
        event.setCancelled(true);

        if (!event.getPlayer().hasPermission(BukkitPermissions.CHAT.CHAT_COLORED)) {
            message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));
            message = MiniMessageParser.stripTokens(message);
        }

        chatHandler.handleChat(player, message);
    }

    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) throws EventException {
        if (event instanceof AsyncChatEvent chatEvent)
            onAsyncChat(chatEvent);
    }
}
