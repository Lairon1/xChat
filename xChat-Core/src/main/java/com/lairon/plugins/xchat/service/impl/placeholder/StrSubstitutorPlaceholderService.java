package com.lairon.plugins.xchat.service.impl.placeholder;

import com.lairon.plugins.xchat.entity.CommandSender;
import com.lairon.plugins.xchat.entity.Player;
import lombok.NonNull;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.Map;

public class StrSubstitutorPlaceholderService extends AbstractPlaceholderService {

    @Override
    public String setPlaceholders(@NonNull CommandSender player, @NonNull String message, @NonNull Map<String, String> placeholders) {
        return new StrSubstitutor(placeholders, "{", "}").replace(message);
    }



}
