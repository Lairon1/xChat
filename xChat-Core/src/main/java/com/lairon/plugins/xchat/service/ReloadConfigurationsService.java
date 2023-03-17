package com.lairon.plugins.xchat.service;

import com.lairon.plugins.xchat.entity.CommandSender;
import lombok.NonNull;

public interface ReloadConfigurationsService {

    long reload(@NonNull CommandSender sender) throws Exception;

}
