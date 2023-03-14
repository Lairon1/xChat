package com.lairon.plugins.xchat;

import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.config.YamlLangConfig;
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

public final class ChatPlugin extends JavaPlugin {

    private PlayerService playerService;
    private PlaceholderService placeholderService;
    private SendChatService sendChatService;
    private ChatRegistryService chatRegistryService;
    private ChatLoader chatLoader;
    private LangConfig langConfig = new YamlLangConfig();
    private ChatHandler chatHandler;

    /**
     * Добавить загрузку чатов из конфига ✓
     * Добавить загрузку lang конфига
     * Добавить settings конфиг
     * Добавить команды
     * Добавить кеширование ироков
     * Добавить фильтры чата
     * Добавить private message
     * Добавить команду ignore
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

        chatLoader = new YamlChatLoader(chatRegistryService, this);
        chatLoader.reloadChats();
        getLog4JLogger().info("Loaded " + chatRegistryService.getChats().size() + " chats.");

        chatHandler = new DefaultChatHandler(sendChatService, playerService, chatRegistryService, langConfig);

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
