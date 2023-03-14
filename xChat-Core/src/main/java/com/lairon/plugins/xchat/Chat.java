package com.lairon.plugins.xchat;

import com.lairon.plugins.xchat.filter.ChatFilter;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

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
    @Builder.Default
    private List<ChatFilter> filters = new ArrayList<>();


    public static final int GLOBAL_RANGE = -1;
    public static final int WORLD_RANGE = -2;

}
