package com.lairon.plugins.xchat.loader;

import com.lairon.plugins.xchat.Chat;
import com.lairon.plugins.xchat.service.ChatRegistryService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractChatLoader implements ChatLoader {

    private final ChatRegistryService chatRegistryService;

    @Override
    public void reloadChats() {
        chatRegistryService.clear();
        reload().forEach(chatRegistryService::registerChat);
    }

    protected abstract List<Chat> reload();

}
