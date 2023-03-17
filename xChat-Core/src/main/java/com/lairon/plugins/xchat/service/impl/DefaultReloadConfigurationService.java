package com.lairon.plugins.xchat.service.impl;

import com.lairon.plugins.xchat.config.LangConfig;
import com.lairon.plugins.xchat.config.SettingsConfig;
import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.loader.ChatLoader;
import com.lairon.plugins.xchat.service.ReloadConfigurationsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultReloadConfigurationService implements ReloadConfigurationsService {

    private final SettingsConfig settingsConfig;
    private final LangConfig langConfig;
    private final ChatLoader chatLoader;

    @Override
    public long reload(@NonNull CommandSender sender) throws Exception {
        long start = System.currentTimeMillis();
        settingsConfig.reload();
        langConfig.reload();
        chatLoader.reloadChats();
        return System.currentTimeMillis() - start;
    }

}
