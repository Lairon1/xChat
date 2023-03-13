package com.lairon.plugins.xchat.service.impl.placeholder;

import com.lairon.plugins.xchat.AbstractPlayer;
import lombok.NonNull;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.Map;

public class StrSubstitutorPlaceholderService extends AbstractPlaceholderService {

    @Override
    public String setPlaceholders(@NonNull AbstractPlayer player, @NonNull String message, @NonNull Map<String, String> placeholders) {
        StrSubstitutor sub = new StrSubstitutor(placeholders, "{", "}");
        sub.setVariableResolver(StrLookup.mapLookup(placeholders));
        return sub.replace(message);
    }


}
