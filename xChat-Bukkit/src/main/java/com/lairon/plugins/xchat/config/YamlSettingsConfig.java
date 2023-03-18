package com.lairon.plugins.xchat.config;

import com.lairon.lairconfig.LairConfig;
import com.lairon.lairconfig.StorageClass;
import com.lairon.lairconfig.annotations.ConfigPath;
import com.lairon.plugins.xchat.filter.ChatFilter;
import com.lairon.plugins.xchat.filter.impl.CapsFilter;
import com.lairon.plugins.xchat.filter.impl.RegularFilter;
import com.lairon.plugins.xchat.filter.impl.SpamFilter;
import com.lairon.plugins.xchat.filter.impl.SwearFilter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class YamlSettingsConfig extends StorageClass implements SettingsConfig {

    private final LairConfig config;

    @ConfigPath("DefaultChat")
    private String defaultChatID = "local";

    @ConfigPath("Filter.Caps.Enable")
    private boolean capsFilterEnable = true;
    @ConfigPath("Filter.Caps.Ratio")
    private double capsFilterRatio = 0.5f;
    @ConfigPath("Filter.Caps.Message")
    private String capsFilterMessage = "&7[&6xChat&7] Please don't caps in the chat";

    @ConfigPath("Filter.Spam.Enable")
    private boolean spamFilterEnable = true;
    @ConfigPath("Filter.Spam.MaxEqualMessages")
    private int spamFilterMaxEqualMessages = 2;
    @ConfigPath("Filter.Spam.TimeWhenDelete")
    private long spamFilterTimeWhenDelete = 60000;
    @ConfigPath("Filter.Spam.MaxSimilar")
    private double spamFilterMaxSimilar = 0.7f;
    @ConfigPath("Filter.Spam.Message")
    private String spamFilterMessage = "&7[&6xChat&7] Please don't spam in the chat";

    @ConfigPath("Filter.Regular.Enable")
    boolean regularFilterEnable = true;
    @ConfigPath("Filter.Regular.Regulars")
    private Map<String, String> regularFilterRegulars = Map.of(
            "[^a-zA-Z0-9а-яА-Я\\s!@#$%^&*()_+\\[\\]\\{\\}\\\\|<>/?~`-]", "&7[&6xChat&7] Please do not use special characters in chat."
    );

    @ConfigPath("Filter.Swear.Enable")
    boolean swearFilterEnable = true;
    @ConfigPath("Filter.Swear.Swears")
    private List<String> swearFilterSwears = List.of("");
    @ConfigPath("Filter.Swear.Mesage")
    private String swearFilterMessage = "&7[&6xChat&7] Please don't swear in the chat";

    private List<ChatFilter> filters;

    @Override
    public List<ChatFilter> getChatFilters() {
        if(filters == null) reloadChatFilters();
        return filters;
    }

    private void reloadChatFilters(){
        filters = new ArrayList<>();
        if (isCapsFilterEnable()) {
            filters.add(new CapsFilter(getCapsFilterRatio(), getCapsFilterMessage()));
        }
        if(isSpamFilterEnable()){
            filters.add(new SpamFilter(
                    getSpamFilterMaxEqualMessages(),
                    getSpamFilterTimeWhenDelete(),
                    getSpamFilterMaxSimilar(),
                    getSpamFilterMessage()));
        }
        if (isRegularFilterEnable()) {
            filters.add(new RegularFilter(getRegularFilterRegulars()));
        }
        if (isSwearFilterEnable()) {
            filters.add(new SwearFilter(getSwearFilterSwears(), getSwearFilterMessage()));
        }
    }

    @Override
    public void reload() throws IOException, InvalidConfigurationException, IllegalAccessException {
        config.reload();
        reloadChatFilters();
    }

    @Override
    public String getPath() {
        return "Settings.";
    }
}
