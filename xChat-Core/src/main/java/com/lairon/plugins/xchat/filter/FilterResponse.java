package com.lairon.plugins.xchat.filter;

import lombok.NonNull;

public record FilterResponse(boolean result, String message) {

    private static final FilterResponse empty = new FilterResponse(false, "");

    public static FilterResponse empty() {
        return empty;
    }

    public static FilterResponse message(@NonNull String message){
        return new FilterResponse(true, message);
    }

}
