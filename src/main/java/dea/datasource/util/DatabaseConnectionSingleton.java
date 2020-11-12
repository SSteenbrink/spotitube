package dea.datasource.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionSingleton {

    private static DatabaseConnectionSingleton instance = null;

    private Connection connection;

    public DatabaseConnectionSingleton() {
        try {
            DatabaseConfigSingleton databaseConfig = DatabaseConfigSingleton.getInstance();
            switch (databaseConfig.getDatabaseType()) {
                case MYSQL:
                    Class.forName(databaseConfig.getMySqlDriver());
                    this.connection = DriverManager.getConnection(databaseConfig.getMySqlConnectionString());
                    break;
                case SQLITE:
                    Class.forName(databaseConfig.getSQLiteDriver());
                    this.connection = DriverManager.getConnection(databaseConfig.getSQLiteConnectionString());
                    break;
                case MONGODB:
                    Class.forName(databaseConfig.getMongoDbDriver());
                    this.connection = DriverManager.getConnection(databaseConfig.getMongoDbConnectionString());
                    break;
                default:
                    break;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getClass().toString());
            e.printStackTrace();
        }
    }

    public static DatabaseConnectionSingleton getInstance() {
        if(instance == null) {
            instance = new DatabaseConnectionSingleton();
        }
        return instance;
    }


    public Connection getConnection() {
        return connection;
    }
}
