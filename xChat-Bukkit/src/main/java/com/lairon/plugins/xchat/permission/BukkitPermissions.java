package com.lairon.plugins.xchat.permission;

public class BukkitPermissions {

    public static final String PREFIX = "xchat";
    public static Chat CHAT = new Chat();


    public static class Chat {

        public final String CHAT_COLORED = PREFIX + ".chat.colored";
        public final String CHAT_SEND = PREFIX + ".chat.send";
        public final String CHAT_LOOK = PREFIX + ".chat.look";

    }


}
