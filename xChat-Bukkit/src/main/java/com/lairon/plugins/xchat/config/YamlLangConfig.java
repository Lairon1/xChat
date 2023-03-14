package com.lairon.plugins.xchat.config;

import com.lairon.lairconfig.StorageClass;
import com.lairon.lairconfig.annotations.ConfigPath;
import lombok.Getter;

@Getter
public class YamlLangConfig extends StorageClass implements LangConfig {

    @ConfigPath("ChatNotFound")
    private String chatNotFound = "&7[&6xChat&7] We could not find a suitable chat for you";
    @ConfigPath("YourMessageIsEmpty")
    private String yourMessageIsEmpty = "&7[&6xChat&7] Your message is empty";

    @Override
    public String getPath() {
        return "Lang.";
    }
}
