package com.lairon.plugins.xchat.loader;

import com.lairon.plugins.xchat.Chat;
import com.lairon.plugins.xchat.config.SettingsConfig;
import com.lairon.plugins.xchat.filter.ChatFilter;
import com.lairon.plugins.xchat.filter.impl.CapsFilter;
import com.lairon.plugins.xchat.filter.impl.SpamFilter;
import com.lairon.plugins.xchat.filter.impl.RegularFilter;
import com.lairon.plugins.xchat.filter.impl.SwearFilter;
import com.lairon.plugins.xchat.service.ChatRegistryService;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlChatLoader extends AbstractChatLoader {

    private final Plugin plugin;
    private final Logger logger;
    private final SettingsConfig settingsConfig;

    private File file;
    private YamlConfiguration config;

    public YamlChatLoader(@NonNull ChatRegistryService chatRegistryService,
                          @NonNull Plugin plugin,
                          @NonNull SettingsConfig settingsConfig) {
        super(chatRegistryService);
        this.plugin = plugin;
        this.logger = plugin.getSLF4JLogger();
        this.settingsConfig = settingsConfig;
    }

    @Override
    protected List<Chat> reload() {
        reloadFiles();
        List<Chat> chats = new ArrayList<>();
        List<ChatFilter> chatFilters = settingsConfig.getChatFilters();
        for (String chatID : config.getConfigurationSection("chats").getKeys(false)) {
            ConfigurationSection chatSection = config.getConfigurationSection("chats." + chatID);
            if (!checkChat(chatSection)) continue;
            Chat.ChatBuilder builder = Chat.builder().id(chatID)
                    .format(chatSection.getString("format"));
            if (chatSection.contains("symbol"))
                builder.symbol(chatSection.getString("symbol").charAt(0));
            if (chatSection.isInt("range")) {
                builder.range(chatSection.getInt("range"));
            } else {
                String range = chatSection.getString("range");
                switch (range.toUpperCase()) {
                    case "WORLD":
                        builder.range(Chat.WORLD_RANGE);
                        break;
                    case "GLOBAL":
                        builder.range(Chat.GLOBAL_RANGE);
                        break;
                    default:
                        logger.error("Range " + range + " not found. Use GLOBAL, WORLD or number. Chat: " + chatID);
                        continue;
                }
            }
            builder.filters(chatFilters);
            chats.add(builder.build());
        }
        return chats;
    }

    private boolean checkChat(@NonNull ConfigurationSection chat) {
        if (!chat.contains("format")) {
            logger.error("Format is a mandatory argument for chat. Chat: " + chat.getName());
            return false;
        }
        if (!chat.contains("range")) {
            logger.error("Range is a mandatory argument for chat. Chat: " + chat.getName());
            return false;
        }
        return true;
    }

    private void reloadFiles() {
        if (file == null)
            file = new File(plugin.getDataFolder() + File.separator + "chats.yml");
        if (!file.exists())
            plugin.saveResource("chats.yml", true);
        config = YamlConfiguration.loadConfiguration(file);
    }

}
