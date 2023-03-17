package com.lairon.plugins.xchat.command;

import com.lairon.plugins.xchat.adapter.BukkitAdapter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequiredArgsConstructor
public class BukkitCommandExecutor implements CommandExecutor, TabCompleter {

    private final com.lairon.plugins.xchat.command.CommandExecutor commandExecutor;
    private final Plugin plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (sender instanceof Player player) {
                commandExecutor.onCommand(BukkitAdapter.adapt(player), args);
            } else {
                commandExecutor.onCommand(BukkitAdapter.consoleSender(), args);
            }
        });
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        if (sender instanceof Player player) {
            return commandExecutor.onTabComplete(BukkitAdapter.adapt(player), args);
        } else {
            return commandExecutor.onTabComplete(BukkitAdapter.consoleSender(), args);
        }
    }


}
