package com.lairon.plugins.xchat;

import com.lairon.lairconfig.LairConfig;
import com.lairon.lairconfig.StorageClass;
import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.config.SettingsConfig;
import com.lairon.plugins.xchat.config.YamlLangConfig;
import com.lairon.plugins.xchat.config.YamlSettingsConfig;
import com.lairon.plugins.xchat.handler.ChatHandler;
import com.lairon.plugins.xchat.handler.impl.DefaultChatHandler;
import com.lairon.plugins.xchat.listener.ChatListener;
import com.lairon.plugins.xchat.loader.ChatLoader;
import com.lairon.plugins.xchat.loader.YamlChatLoader;
import com.lairon.plugins.xchat.service.*;
import com.lairon.plugins.xchat.service.impl.ArrayChatRegistryService;
import com.lairon.plugins.xchat.service.impl.DefaultSendChatService;
import com.lairon.plugins.xchat.service.impl.placeholder.StrSubstitutorPlaceholderService;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ChatPlugin extends JavaPlugin {

    private LairConfig langConfig;
    private LairConfig settingsConfig;
    private LangConfig lang = new YamlLangConfig();
    private SettingsConfig settings = new YamlSettingsConfig();

    private PlayerService playerService;
    private PlaceholderService placeholderService;
    private SendChatService sendChatService;
    private ChatRegistryService chatRegistryService;
    private ChatLoader chatLoader;
    private ChatHandler chatHandler;

    /**
     * Добавить загрузку чатов из конфига ✓
     * Добавить загрузку lang конфига ✓
     * Добавить settings конфиг
     * Добавить команды
     * Добавить кеширование ироков
     * Добавить фильтры чата
     * Добавить private message
     * Добавить команду ignore
     * Добавить spy
     * Добавить MiniMessage ✓
     * Добавить авто-анонсы
     * Добавить больше настроек в чат
     * Добавить уведомления
     * Добавить упоминания
     */

    @Override
    public void onEnable() {

        langConfig = new LairConfig(getDataFolder() + File.separator + "lang.yml");
        settingsConfig = new LairConfig(getDataFolder() + File.separator + "settings.yml");

        try {
            langConfig.registerStorageClass((StorageClass) lang);
            langConfig.reload();
            settingsConfig.registerStorageClass((StorageClass) settings);
            settingsConfig.reload();
        } catch (Exception e) {
            getLog4JLogger().error("An error occurred while loading the configuration files, check them for syntax" +
                    " errors and for correct data types in the fields");
            e.printStackTrace();
        }


        playerService = new BukkitPlayerService();
        placeholderService = setupPlaceholderService();

        sendChatService = new DefaultSendChatService(playerService, placeholderService);
        chatRegistryService = new ArrayChatRegistryService();

        chatLoader = new YamlChatLoader(chatRegistryService, this, settings);
        chatLoader.reloadChats();
        getLog4JLogger().info("Loaded " + chatRegistryService.getChats().size() + " chats.");

        chatHandler = new DefaultChatHandler(sendChatService, playerService, chatRegistryService, settings, lang);

        ChatListener chatListener = new ChatListener(chatHandler);
        Bukkit.getPluginManager().registerEvent(
                AsyncChatEvent.class,
                chatListener,
                EventPriority.NORMAL,
                chatListener,
                this,
                true);

    }

    private PlaceholderService setupPlaceholderService(){
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            getLog4JLogger().info("PlaceholderAPI dependency installed.");
            return new PapiPlaceholderService();
        }else{
            getLog4JLogger().warn("PlaceholderAPI not found. Using the default placeholder system.");
            return new StrSubstitutorPlaceholderService();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
