package com.lairon.plugins.xchat.listener;

import com.lairon.plugins.xchat.data.DataProvider;
import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import com.lairon.plugins.xchat.handler.ChatHandler;
import com.lairon.plugins.xchat.permission.Permissions;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ChatListener implements EventExecutor, Listener {

    private final ChatHandler chatHandler;
    private final DataProvider provider;

    public void onAsyncChat(AsyncChatEvent event) {
        Player player = provider.loadPlayer(event.getPlayer().getUniqueId()).orElse(BukkitAdapter.createNewPlayer(event.getPlayer()));
        String message = LegacyComponentSerializer.legacyAmpersand().serialize(event.message());
        if (player == null || message == null) return;
        event.setCancelled(true);

        if (!event.getPlayer().hasPermission(Permissions.CHAT.COLORED)) {
            message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));
            message = MiniMessage.builder().build().stripTags(message);
        }

        chatHandler.handleChat(player, message);
    }

    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) {
        if (event instanceof AsyncChatEvent chatEvent)
            onAsyncChat(chatEvent);
    }
}
