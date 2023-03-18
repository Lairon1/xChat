package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.entity.CommandSender;
import lombok.NonNull;

import java.util.List;

public interface SocialSpyService {

    void sendToSocialSpy(@NonNull List<CommandSender> excluded, @NonNull String message);

}
