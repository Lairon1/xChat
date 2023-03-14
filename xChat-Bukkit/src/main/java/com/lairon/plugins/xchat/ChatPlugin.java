package com.lairon.plugins.xchat;

import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.config.YamlLangConfig;
import com.lairon.plugins.xchat.handler.ChatHandler;
import com.lairon.plugins.xchat.handler.impl.DefaultChatHandler;
import com.lairon.plugins.xchat.listener.ChatListener;
import com.lairon.plugins.xchat.service.*;
import com.lairon.plugins.xchat.service.impl.ArrayChatRegistryService;
import com.lairon.plugins.xchat.service.impl.DefaultSendChatService;
import com.lairon.plugins.xchat.service.impl.placeholder.StrSubstitutorPlaceholderService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatPlugin extends JavaPlugin {

    private PlayerService playerService;
    private PlaceholderService placeholderService;
    private SendChatService sendChatService;
    private ChatRegistryService chatRegistryService;
    private LangConfig langConfig = new YamlLangConfig();
    private ChatHandler chatHandler;

    /**
     * Добавить загрузку чатов из конфига
     * Добавить загрузку lang конфига
     * Добавить команды
     * Добавить кеширование ироков
     * Добавить фильтры чата
     * Добавить private message
     * Добавить spy
     * Добавить MiniMessage
     * Добавить авто-анонсы
     * Добавить больше настроек в чат
     */

    @Override
    public void onEnable() {
        playerService = new BukkitPlayerService();
        placeholderService = setupPlaceholderService();
        sendChatService = new DefaultSendChatService(playerService, placeholderService);
        chatRegistryService = new ArrayChatRegistryService();

        chatRegistryService.setDefaultChat(Chat
                .builder()
                .id("local")
                .format("&7[&eL&7] %vault_prefix%&7{player}&e: {message}")
                .range(200)
                .symbol(' ')
                .build());
        chatRegistryService.registerChat(Chat
                .builder()
                .id("global")
                .format("&7[&2G&7] %vault_prefix%&7{player}&2: {message}")
                .range(Chat.GLOBAL_RANGE)
                .symbol('!')
                .build());

        chatHandler = new DefaultChatHandler(sendChatService, playerService, chatRegistryService, langConfig);

        Bukkit.getPluginManager().registerEvents(new ChatListener(chatHandler), this);

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
