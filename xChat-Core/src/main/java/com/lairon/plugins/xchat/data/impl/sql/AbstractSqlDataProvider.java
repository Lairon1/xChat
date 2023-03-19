package com.lairon.plugins.xchat.data.impl.sql;

import com.lairon.plugins.xchat.data.DataProvider;
import com.lairon.plugins.xchat.entity.Player;
import lombok.NonNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractSqlDataProvider implements DataProvider {

    private Connection connection;

    @Override
    public Optional<Player> loadPlayer(@NonNull UUID uuid) {
        Connection connection = getConnection();
        try (
                PreparedStatement playerPS = connection.prepareStatement("SELECT * FROM xc_players WHERE `uuid` = ?");
                PreparedStatement ignoredPlayersPS = connection.prepareStatement("SELECT * FROM xc_ignored_players WHERE `player` = ?");
        ) {
            playerPS.setString(1, uuid.toString());
            ignoredPlayersPS.setString(1, uuid.toString());

            try (
                    ResultSet playerRS = playerPS.executeQuery();
                    ResultSet ignoredRS = ignoredPlayersPS.executeQuery();
            ) {
                if (playerRS.next()) {
                    Player player = new Player(uuid, playerRS.getString("name"));
                    player.setSocialSpyEnabled(playerRS.getBoolean("social_spy_enabled"));

                    List<UUID> ignoredList = new ArrayList<>();
                    while (ignoredRS.next()) {
                        ignoredList.add(UUID.fromString(ignoredRS.getString("ignored")));
                    }
                    player.setIgnoredPlayers(ignoredList);
                    return Optional.of(player);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void savePlayer(@NonNull Player player) {

        Connection connection = getConnection();
        try(
                PreparedStatement playerStatement = connection.prepareStatement("""
                        INSERT INTO xc_players(uuid, name, social_spy_enabled)
                        VALUES (?, ?, ?)
                        ON DUPLICATE KEY UPDATE
                        social_spy_enabled = ?;
                        """);
                PreparedStatement ignoreStatement
                        = connection.prepareStatement("INSERT IGNORE INTO xc_ignored_players(player, ignored) VALUES(?, ?);")

        ){
            playerStatement.setString(1, player.getUuid().toString());
            playerStatement.setString(2, player.getName());
            playerStatement.setBoolean(3, player.isSocialSpyEnabled());
            playerStatement.setBoolean(4, player.isSocialSpyEnabled());

            for (UUID ignoredPlayer : player.getIgnoredPlayers()) {
                ignoreStatement.setString(1, player.getUuid().toString());
                ignoreStatement.setString(2, ignoredPlayer.toString());
                ignoreStatement.addBatch();
            }

            playerStatement.execute();
            ignoreStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeIgnore(@NonNull Player player, @NonNull Player ignoredPlayer) {

        Connection connection = getConnection();
        try(
                PreparedStatement statement = connection.prepareStatement("DELETE FROM xc_ignored_players WHERE player = ? AND ignored = ?;")
        ){
            statement.setString(1, player.getUuid().toString());
            statement.setString(2, ignoredPlayer.getUuid().toString());
            statement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createTables() {
        Connection connection = getConnection();

        try (
                Statement statement = connection.createStatement();
        ) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS xc_players
                    (
                        `uuid`               varchar(36)                  NOT NULL,
                        `name`               varchar(200)          NOT NULL,
                        `social_spy_enabled` BIT DEFAULT FALSE NOT NULL,
                        CONSTRAINT xc_player_uuid_pk
                            PRIMARY KEY (`uuid`),
                        UNIQUE(`uuid`)
                    );
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS xc_ignored_players
                    (
                        `player`  varchar(36) NOT NULL,
                        `ignored` varchar(36) NOT NULL,
                        CONSTRAINT xc_ignored_players_ignored_fk
                            FOREIGN KEY (`ignored`) REFERENCES xc_players (`uuid`),
                        CONSTRAINT xc_ignored_players_player_fk
                            FOREIGN KEY (`player`) REFERENCES xc_players (`uuid`),
                        UNIQUE(`player`, `ignored`)
                    );
                                        
                    """);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()) connection = createConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    abstract Connection createConnection();
}
