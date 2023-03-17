package com.lairon.plugins.xchat.config;

import com.lairon.lairconfig.LairConfig;
import com.lairon.lairconfig.StorageClass;
import com.lairon.lairconfig.annotations.ConfigPath;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

@Getter
@RequiredArgsConstructor
public class YamlLangConfig extends StorageClass implements LangConfig {

    private final LairConfig config;

    /**Misc*/
    @ConfigPath("ChatNotFound")
    private String chatNotFound = "&7[&6xChat&7] We could not find a suitable chat for you";
    @ConfigPath("YourMessageIsEmpty")
    private String yourMessageIsEmpty = "&7[&6xChat&7] Your message is empty";
    @ConfigPath("DontHavePermission")
    private String dontHavePermission = "&7[&6xChat&7] You don't have enough permission to do this.";
    @ConfigPath("DefaultCommand")
    private String defaultCommand = "&7[&6xChat&7] Use - &6/xchat <subcommand>&7.";
    @ConfigPath("CommandNotFound")
    private String commandNotFound = "&7[&6xChat&7] Sub command {command} not found.";
    @ConfigPath("OnlyPlayer")
    private String onlyPlayer = "&7[&6xChat&7] This command can only be used by the player";

    /**Reload*/
    @ConfigPath("Reload.Error")
    private String reloadError = "&7[&6xChat&7] An error occurred while reloading the configuration files(&c{error}&7). See console.";
    @ConfigPath("Reload.Successfully")
    private String reloadSuccessfully = "&7[&6xChat&7] Configuration files successfully reloaded for &6{time}&7ms";


    @Override
    public String getPath() {
        return "Lang.";
    }

    @Override
    public void reload() throws IOException, InvalidConfigurationException, IllegalAccessException {
        config.reload();
    }
}
