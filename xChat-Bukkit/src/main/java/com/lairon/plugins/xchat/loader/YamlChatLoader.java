package com.lairon.plugins.xchat.loader;

import com.lairon.plugins.xchat.Chat;
import com.lairon.plugins.xchat.filter.impl.CapsFilter;
import com.lairon.plugins.xchat.filter.impl.FloodFilter;
import com.lairon.plugins.xchat.filter.impl.RegularFilter;
import com.lairon.plugins.xchat.filter.impl.SwearFilter;
import com.lairon.plugins.xchat.service.ChatRegistryService;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlChatLoader extends AbstractChatLoader {

    private final Plugin plugin;
    private final Logger logger;

    private File file;
    private YamlConfiguration config;

    public YamlChatLoader(@NonNull ChatRegistryService chatRegistryService,
                          @NonNull Plugin plugin
    ) {
        super(chatRegistryService);
        this.plugin = plugin;
        this.logger = plugin.getSLF4JLogger();
    }

    @Override
    protected List<Chat> reload() {
        reloadFiles();
        List<Chat> chats = new ArrayList<>();
        for (String chatID : config.getConfigurationSection("chats").getKeys(false)) {
            ConfigurationSection chatSection = config.getConfigurationSection("chats." + chatID);
            if (!checkChat(chatSection)) continue;
            Chat.ChatBuilder builder = Chat.builder().id(chatID)
                    .format(chatSection.getString("format"));
            if (chatSection.contains("symbol"))
                builder.symbol(chatSection.getString("symbol").charAt(0));
            if (chatSection.isInt("range")) {
                builder.range(chatSection.getInt("range"));
            } else {
                String range = chatSection.getString("range");
                switch (range.toUpperCase()) {
                    case "WORLD":
                        builder.range(Chat.WORLD_RANGE);
                        break;
                    case "GLOBAL":
                        builder.range(Chat.GLOBAL_RANGE);
                        break;
                    default:
                        logger.error("Range " + range + " not found. Use GLOBAL, WORLD or number. Chat: " + chatID);
                        continue;
                }
            }
            builder.filters(List.of(new CapsFilter(0.5, "&7Не капси пож"),
                    new FloodFilter(3, 10000, 0.7f, "&7Не флуди ок да"),
                    new SwearFilter(List.of("swear", "asd_asd"), "Не матюкайся"),
                    new RegularFilter(Map.of(
                            "(?iu)\\b((у|[нз]а|(хитро|не)?вз?[ыьъ]|с[ьъ]|(и|ра)[зс]ъ?|(о[тб]|под)[ьъ]?|(.\\B)+?[оаеи])?-?([её]б(?!о[рй])|и[пб][ае][тц]).*?|(н[иеа]|([дп]|верт)о|ра[зс]|з?а|с(ме)?|о(т|дно)?|апч)?-?ху([яйиеёю]|ли(?!ган)).*?|(в[зы]|(три|два|четыре)жды|(н|сук)а)?-?бл(я(?!(х|ш[кн]|мб)[ауеыио]).*?|[еэ][дт]ь?)|(ра[сз]|[зн]а|[со]|вы?|п(ере|р[оие]|од)|и[зс]ъ?|[ао]т)?п[иеё]зд.*?|(за)?п[ие]д[аое]?р(ну.*?|[оа]м|(ас)?(и(ли)?[нщктл]ь?)?|(о(ч[еи])?|ас)?к(ой)|юг)[ауеы]?|манд([ауеыи](л(и[сзщ])?[ауеиы])?|ой|[ао]вошь?(е?к[ауе])?|юк(ов|[ауи])?)|муд([яаио].*?|е?н([ьюия]|ей))|мля([тд]ь)?|лять|([нз]а|по)х|м[ао]л[ао]фь([яию]|[еёо]й))\\b\n", "Не матюкайся 1",
                            "(?iu)\\b(([уyu]|[нзnz3][аa]|(хитро|не)?[вvwb][зz3]?[ыьъi]|[сsc][ьъ']|(и|[рpr][аa4])[зсzs]ъ?|([оo0][тбtb6]|[пp][оo0][дd9])[ьъ']?|(.\\B)+?[оаеиeo])?-?([еёe][бb6](?!о[рй])|и[пб][ае][тц]).*?|([нn][иеаaie]|([дпdp]|[вv][еe3][рpr][тt])[оo0]|[рpr][аa][зсzc3]|[з3z]?[аa]|с(ме)?|[оo0]([тt]|дно)?|апч)?-?[хxh][уuy]([яйиеёюuie]|ли(?!ган)).*?|([вvw][зы3z]|(три|два|четыре)жды|(н|[сc][уuy][кk])[аa])?-?[бb6][лl]([яy](?!(х|ш[кн]|мб)[ауеыио]).*?|[еэe][дтdt][ь']?)|([рp][аa][сзc3z]|[знzn][аa]|[соsc]|[вv][ыi]?|[пp]([еe][рpr][еe]|[рrp][оиioеe]|[оo0][дd])|и[зс]ъ?|[аоao][тt])?[пpn][иеёieu][зz3][дd9].*?|([зz3][аa])?[пp][иеieu][дd][аоеaoe]?[рrp](ну.*?|[оаoa][мm]|([аa][сcs])?([иiu]([лl][иiu])?[нщктлtlsn]ь?)?|([оo](ч[еиei])?|[аa][сcs])?[кk]([оo]й)?|[юu][гg])[ауеыauyei]?|[мm][аa][нnh][дd]([ауеыayueiи]([лl]([иi][сзc3щ])?[ауеыauyei])?|[оo][йi]|[аоao][вvwb][оo](ш|sh)[ь']?([e]?[кk][ауеayue])?|юк(ов|[ауи])?)|[мm][уuy][дd6]([яyаиоaiuo0].*?|[еe]?[нhn]([ьюия'uiya]|ей))|мля([тд]ь)?|лять|([нз]а|по)х|м[ао]л[ао]фь([яию]|[её]й))\\b", "Не матюкайся 2"
                    ))));
            chats.add(builder.build());
        }
        return chats;
    }

    private boolean checkChat(@NonNull ConfigurationSection chat) {
        if (!chat.contains("format")) {
            logger.error("Format is a mandatory argument for chat. Chat: " + chat.getName());
            return false;
        }
        if (!chat.contains("range")) {
            logger.error("Range is a mandatory argument for chat. Chat: " + chat.getName());
            return false;
        }
        return true;
    }

    private void reloadFiles() {
        if (file == null)
            file = new File(plugin.getDataFolder() + File.separator + "chats.yml");
        if (!file.exists())
            plugin.saveResource("chats.yml", true);
        config = YamlConfiguration.loadConfiguration(file);
    }

}
