package com.lairon.plugins.xchat.permission;

public class Permissions {

    public static final String PREFIX = "xchat";
    public static final Chat CHAT = new Chat();
    public static final Bypass BYPASS = new Bypass();
    public static final Command COMMAND = new Command();

    public static class Chat {

        public final String COLORED = PREFIX + ".chat.colored";
        public final String SEND = PREFIX + ".chat.send";
        public final String LOOK = PREFIX + ".chat.look";
        public final String SOCIAL_SPY = PREFIX + ".chat.socialspy";

    }

    public static class Bypass{

        public final String CAPS_BYPASS = PREFIX + ".bypass.caps";
        public final String SPAM_BYPASS = PREFIX + ".bypass.spam";
        public final String SWEAR_BYPASS = PREFIX + ".bypass.swear";
        public final String REGULAR_BYPASS = PREFIX + ".bypass.regular";

    }

    public static class Command{

        public final String DEFAULT_COMMAND = PREFIX + ".command";
        public final String RELOAD = DEFAULT_COMMAND + ".reload";
        public final String PRIVATE_MESSAGE = DEFAULT_COMMAND + ".privatemessage";

    }

}
