package com.lairon.plugins.xchat.config;

import com.lairon.plugins.xchat.config.LangConfig;

public class YamlLangConfig implements LangConfig {
    @Override
    public String chatNotFound() {
        return "Чат не найден";
    }

    @Override
    public String yourMessageIsEmpty() {
        return "Ваше сообщение пустое";
    }
}
