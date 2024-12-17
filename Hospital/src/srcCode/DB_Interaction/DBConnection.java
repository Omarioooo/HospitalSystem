package srcCode.DB_Interaction;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private boolean flgCon;
    private Connection connection;

    public DBConnection() {
    }

    // Read the server data using I/O Streams
    public SQLServerDataSource myDriver() {
        SQLServerDataSource sqlServerDataSource = new SQLServerDataSource();
        Properties properties = new Properties();
        FileInputStream Info;
        try (FileInputStream info = new FileInputStream("D:\\SQL_SERVER.txt")) {
            properties.load(info);
            sqlServerDataSource.setURL(properties.getProperty("URL"));
            sqlServerDataSource.setUser(properties.getProperty("USER"));
            sqlServerDataSource.setPassword(properties.getProperty("PASSWORD"));
        } catch (IOException io) {
            io.printStackTrace();
        }

        return sqlServerDataSource;
    }

    // A method to connect the server
    public void Connect() {
        try {
            connection = myDriver().getConnection();
            flgCon = true;
        } catch (SQLServerException sqlEx) {
            sqlEx.printStackTrace();
            flgCon = false;
            throw new RuntimeException("Database connection failed", sqlEx);
        }
    }

    // A getter for the connection
    public Connection getConnection() {
        return this.connection;
    }

    // Stop the connection
    public void disConnect() {
        if (connection != null) {
            try {
                connection.close();
                flgCon = false;
                System.out.println("Disconnected...");
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
    }

    // A method to check the connection
    public boolean isConnect() {
        return flgCon && connection != null;
    }

}

