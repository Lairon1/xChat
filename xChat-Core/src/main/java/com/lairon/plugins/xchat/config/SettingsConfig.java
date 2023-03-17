package com.lairon.plugins.xchat.config;

import com.lairon.plugins.xchat.filter.ChatFilter;

import java.util.List;
import java.util.Map;

public interface SettingsConfig {

    String getDefaultChatID();
    /**Filters*/
    /**Caps*/
    boolean isCapsFilterEnable();
    double getCapsFilterRatio();
    String getCapsFilterMessage();

    /**Spam*/
    boolean isSpamFilterEnable();
    int getSpamFilterMaxEqualMessages();
    long getSpamFilterTimeWhenDelete();
    double getSpamFilterMaxSimilar();
    String getSpamFilterMessage();

    /**Regular*/
    boolean isRegularFilterEnable();
    Map<String, String> getRegularFilterRegulars();

    /**Swear*/
    boolean isSwearFilterEnable();
    List<String> getSwearFilterSwears();
    String getSwearFilterMessage();

    List<ChatFilter> getChatFilters();

    void reload() throws Exception;
}
