package fr.epita.quiz.services.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static ConnectionUtil instance;

    private ConnectionUtil() {

    }

    public static ConnectionUtil getInstance() {
        if (instance == null) {
            instance = new ConnectionUtil();
        }
        return instance;
    }

    //单例模式返回数据库连接
    public static Connection getConnection() throws SQLException {
        ConfigurationService conf = ConfigurationService.getInstance();
        String username = conf.getConfigurationValue(ConfigEntry.DB_USERNAME, "");
        String password = conf.getConfigurationValue(ConfigEntry.DB_PASSWORD, "");
        String url = conf.getConfigurationValue(ConfigEntry.DB_URL, "");
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
