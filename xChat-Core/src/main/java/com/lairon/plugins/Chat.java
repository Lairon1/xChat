package com.lairon.plugins;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Chat {

    @NonNull
    private final String id;
    @NonNull
    private String format;
    private int range;
    @Builder.Default
    private char symbol = ' ';

}
