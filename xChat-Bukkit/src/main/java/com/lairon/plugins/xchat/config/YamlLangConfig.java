package com.lairon.plugins.xchat.config;

import lombok.Getter;

@Getter
public class YamlLangConfig implements LangConfig {

    private String chatNotFound = "&7[&6xChat&7] We could not find a suitable chat for you";
    private String yourMessageIsEmpty = "&7[&6xChat&7] Your message is empty";

}
