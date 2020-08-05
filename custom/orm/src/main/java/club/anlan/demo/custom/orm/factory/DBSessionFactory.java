package club.anlan.demo.custom.orm.factory;

import club.anlan.demo.custom.orm.config.DBSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/5 23:35
 */
public class DBSessionFactory {

    private DBSource dbSource;

    private Properties props;

    public DBSessionFactory() {
        props = new Properties();
        try {
            props.load(ClassLoader.getSystemResourceAsStream("dbConfig.properties"));
            dbSource = new DBSource(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DBSession openSession() throws Exception {
        return new DBSession(dbSource.openConnection());
    }

    /**
     * 操作数据库
     */
    public static class DBSession {

        private Connection conn;

        public DBSession(Connection conn) {
            this.conn = conn;
        }

        public <T>List<T> list(Class<T> cls) throws IllegalAccessException, InstantiationException {
            List<T> list = new ArrayList<T>();
            list.add(cls.newInstance());
            list.add(cls.newInstance());

            return list;
        }


        public void close() {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    conn = null;
                }
            }
        }
    }

}
