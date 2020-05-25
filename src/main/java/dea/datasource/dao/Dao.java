package dea.datasource.dao;

import dea.datasource.util.DatabaseConnectionSingleton;
import java.sql.Connection;

public class Dao {

    protected Connection conn;

    public Dao() {
        conn = DatabaseConnectionSingleton.getInstance().getConnection();
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
