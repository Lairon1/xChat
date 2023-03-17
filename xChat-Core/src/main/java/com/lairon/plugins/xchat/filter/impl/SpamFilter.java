package com.lairon.plugins.xchat.filter.impl;

import com.lairon.plugins.xchat.entity.Player;
import com.lairon.plugins.xchat.filter.ChatFilter;
import com.lairon.plugins.xchat.filter.FilterResponse;
import com.lairon.plugins.xchat.permission.Permissions;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class SpamFilter implements ChatFilter {

    private final int maxEqualMessages;
    private final long timeWhenDelete;
    private final double maxSimilar;
    private final String message;

    private HashMap<String, List<ChatLog>> playerLogs = new HashMap<>();

    @Override
    public FilterResponse filter(@NonNull Player player, @NonNull String message) {
        if(!playerLogs.containsKey(player.getUuid().toString())){
            ChatLog chatLog = new ChatLog(player.getUuid(), message);
            List<ChatLog> logs = new ArrayList<>();
            logs.add(chatLog);
            playerLogs.put(player.getUuid().toString(), logs);
            return FilterResponse.empty();
        }
        List<ChatLog> logs = playerLogs.get(player.getUuid().toString());
        List<ChatLog> deleteLogs = new ArrayList<>();

        int similarCounter = 0;

        for (ChatLog log : logs) {
            if(System.currentTimeMillis() - log.time >= timeWhenDelete){
                deleteLogs.add(log);
                continue;
            }

            if(compareStrings(log.getMessage(), message) >= maxSimilar){
                if(++similarCounter >= maxEqualMessages){
                    return FilterResponse.message(this.message);
                }
            }
        }
        logs.removeAll(deleteLogs);
        logs.add(new ChatLog(player.getUuid(), message));
        return FilterResponse.empty();
    }

    public static double compareStrings(String msg1, String msg2) {
        msg1 = msg1.toLowerCase().replaceAll("\\s", "");
        msg2 = msg2.toLowerCase().replaceAll("\\s", "");

        int maxLength = Math.max(msg1.length(), msg2.length());

        int sameChars = 0;
        for (int i = 0; i < msg1.length(); i++) {
            if (msg2.indexOf(msg1.charAt(i)) >= 0) {
                sameChars++;
            }
        }

        double similarity = (double) sameChars / (double) maxLength;

        return similarity;
    }
    @Override
    public String bypassPermission() {
        return Permissions.BYPASS.SPAM_BYPASS;
    }

    @Data
    private static class ChatLog{

        private final UUID uuid;
        private final String message;
        private final long time;

        public ChatLog(UUID uuid, String message) {
            this.uuid = uuid;
            this.message = message;
            this.time = System.currentTimeMillis();
        }
    }

}
