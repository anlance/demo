package club.anlan.demo.custom.orm.config;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/5 23:30
 */
@Data
public class DBSource {

    private String driver;
    private String url;
    private String username;
    private String password;

    public DBSource(Properties properties) {
        this.driver = properties.getProperty("driver");
        this.url = properties.getProperty("url");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
    }

    public Connection openConnection() throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}
