package com.lairon.plugins.xchat.permission;

public class Permissions {

    public static final String PREFIX = "xchat";
    public static Chat CHAT = new Chat();
    public static Bypass BYPASS = new Bypass();

    public static class Chat {

        public final String CHAT_COLORED = PREFIX + ".chat.colored";
        public final String CHAT_SEND = PREFIX + ".chat.send";
        public final String CHAT_LOOK = PREFIX + ".chat.look";

    }

    public static class Bypass{

        public final String CAPS_BYPASS = PREFIX + ".bypass.caps";
        public final String SPAM_BYPASS = PREFIX + ".bypass.spam";
        public final String SWEAR_BYPASS = PREFIX + ".bypass.swear";
        public final String REGULAR_BYPASS = PREFIX + ".bypass.regular";

    }

}
