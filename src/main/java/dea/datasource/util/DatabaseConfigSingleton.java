package dea.datasource.util;

import java.io.InputStream;
import java.util.*;

public class DatabaseConfigSingleton {
    private static DatabaseConfigSingleton instance = null;

    private DatabaseType databaseType;
    private String MySqlDriver;
    private String MySqlConnectionString;
    private String SQLiteDriver;
    private String SQLiteConnectionString;

    private Map<String, String> databaseProperties;

    private DatabaseConfigSingleton() {
        databaseProperties = readPropertiesFile("database.properties");

        MySqlDriver = getProperty("MySqlDriver");
        MySqlConnectionString = getProperty("MySqlConnectionString");
        SQLiteDriver = getProperty("SQLiteDriver");
        SQLiteConnectionString = getProperty("SQLiteConnectionString");

        switch(getProperty("databaseType")) {
            case "SQLITE":
                databaseType = DatabaseType.SQLITE;
                break;
            case "MONGODB":
                databaseType = DatabaseType.MONGODB;
                break;
            case "MYSQL":
            default:
                databaseType = DatabaseType.MYSQL;
                break;
        }
    }

    private String getProperty(String propertyName) {
        return databaseProperties.get(propertyName);
    }

    public static DatabaseConfigSingleton getInstance() {
        if (instance == null) {
            instance = new DatabaseConfigSingleton();
        }

        return instance;
    }

    private Map<String, String> readPropertiesFile(String file) {
        HashMap<String, String> propertyMap = new HashMap<>();

        try(InputStream input = DatabaseConfigSingleton.class.getClassLoader().getResourceAsStream(file)) {
            Properties properties = new Properties();

            properties.load(input);
            Enumeration keys = properties.propertyNames();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();

                propertyMap.put(key, properties.getProperty(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return propertyMap;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public String getMySqlDriver() {
        return MySqlDriver;
    }

    public String getMySqlConnectionString() {
        return MySqlConnectionString;
    }

    public String getSQLiteDriver() {
        return SQLiteDriver;
    }

    public String getSQLiteConnectionString() {
        return SQLiteConnectionString;
    }
}
