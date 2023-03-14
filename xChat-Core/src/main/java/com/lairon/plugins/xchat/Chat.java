package com.lairon.plugins.xchat;

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

    public static final int GLOBAL_RANGE = -1;
    public static final int WORLD_RANGE = -2;

}
