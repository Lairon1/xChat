package com.lairon.plugins.xchat.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class CommandSettings {

    private final String id;
    @Builder.Default
    private final boolean onlyPlayer = false;
    @Builder.Default
    private final List<String> alliances = new ArrayList<>();
    private String permission;

}
