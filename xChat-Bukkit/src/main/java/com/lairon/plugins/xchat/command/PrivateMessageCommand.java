package com.lairon.plugins.xchat.command;

import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.data.DataProvider;
import com.lairon.plugins.xchat.handler.PrivateMessageHandler;
import com.lairon.plugins.xchat.permission.Permissions;
import com.lairon.plugins.xchat.service.PlaceholderService;
import com.lairon.plugins.xchat.service.PlayerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PrivateMessageCommand implements CommandExecutor {

    private final Plugin plugin;
    private final PrivateMessageHandler privateMessageHandler;
    private final LangConfig lang;
    private final PlayerService playerService;
    private final PlaceholderService placeholderService;
    private final DataProvider provider;

    @Override
    public boolean onCommand(@NotNull CommandSender bukkitSender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            com.lairon.plugins.xchat.entity.CommandSender sender;
            if(bukkitSender instanceof Player bukkitPlayer){
                sender = provider.loadPlayer(bukkitPlayer.getUniqueId()).orElse(BukkitAdapter.createNewPlayer(bukkitPlayer));
            }else{
                sender = BukkitAdapter.consoleSender();
            }
            if(args.length < 2){
                playerService.sendMessage(sender, placeholderService.setPlaceholders(sender, lang.getPrivateMessageUsage()));
                return;
            }
            String recipientName = args[0];
            Player bukkitRecipient = Bukkit.getPlayer(recipientName);
            if(bukkitRecipient == null){
                playerService.sendMessage(sender, placeholderService.setPlaceholders(sender, lang.getPlayerNotFound(), Map.of(
                        "player", recipientName
                )));
                return;
            }

            com.lairon.plugins.xchat.entity.Player recipient = provider.loadPlayer(bukkitRecipient.getUniqueId())
                    .orElse(BukkitAdapter.createNewPlayer(bukkitRecipient));

            args[0] = "";

            String message = String.join(" ", args).strip();

            if (!playerService.hasPermission(sender, Permissions.CHAT.COLORED)) {
                message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));
                message = MiniMessage.builder().build().stripTags(message);
            }
            privateMessageHandler.handlePrivateMessage(sender, recipient, message);
        });
        return false;
    }

}
