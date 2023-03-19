package com.lairon.plugins.xchat.data.impl.sql;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteDataProvider extends AbstractSqlDataProvider {

    private final String dataBaseFilePath;

    public SQLiteDataProvider(String dataBaseFilePath) {
        this.dataBaseFilePath = dataBaseFilePath;
        createTables();
    }


    @Override
    public Connection createConnection() {
        File file = new File(dataBaseFilePath);
        if (!file.exists()) {
            try {
                Files.createDirectories(file.getParentFile().toPath());
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
