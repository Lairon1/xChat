package com.lairon.plugins.xchat.data;

import com.lairon.plugins.xchat.entity.Player;
import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface DataProvider {
    Optional<Player> loadPlayer(@NonNull UUID uuid);
    void savePlayer(@NonNull Player player);
    void removeIgnore(@NonNull Player player, @NonNull Player ignoredPlayer);

}
