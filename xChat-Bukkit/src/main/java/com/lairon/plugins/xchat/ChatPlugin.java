package com.lairon.plugins.xchat;

import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.config.YamlLangConfig;
import com.lairon.plugins.xchat.handler.ChatHandler;
import com.lairon.plugins.xchat.handler.impl.DefaultChatHandler;
import com.lairon.plugins.xchat.service.ChatRegistryService;
import com.lairon.plugins.xchat.service.PlaceholderService;
import com.lairon.plugins.xchat.service.PlayerService;
import com.lairon.plugins.xchat.service.SendChatService;
import com.lairon.plugins.xchat.service.impl.ArrayChatRegistryService;
import com.lairon.plugins.xchat.service.impl.DefaultSendChatService;
import com.lairon.plugins.xchat.listener.ChatListener;
import com.lairon.plugins.xchat.service.BukkitPlayerService;
import com.lairon.plugins.xchat.service.PapiPlaceholderService;
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


    @Override
    public void onEnable() {
        playerService = new BukkitPlayerService();
        placeholderService = setupPlaceholderService();
        sendChatService = new DefaultSendChatService(playerService, placeholderService);
        chatRegistryService = new ArrayChatRegistryService();

        chatRegistryService.setDefaultChat(Chat
                .builder()
                .id("local")
                .format("&r\uE002 &7%vault_prefix%&7{player_name}%javascript_verified_status%%javascript_clan_tag%&7&e: &e{message}")
                .range(200)
                .symbol(' ')
                .build());
        chatRegistryService.registerChat(Chat
                .builder()
                .id("global")
                .format("&r⠀ &7%vault_prefix%&7{player}%javascript_verified_status%%javascript_clan_tag%&2:  {message}")
                .range(-1)
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
