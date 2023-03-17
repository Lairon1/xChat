package com.lairon.plugins.xchat.config;

public interface LangConfig {

    String getChatNotFound();
    String getYourMessageIsEmpty();
    String getDontHavePermission();
    String getReloadError();
    String getReloadSuccessfully();
    String getDefaultCommand();
    String getCommandNotFound();
    String getOnlyPlayer();

    void reload() throws Exception;
}
