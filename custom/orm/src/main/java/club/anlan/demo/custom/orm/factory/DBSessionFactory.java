package club.anlan.demo.custom.orm.factory;

import club.anlan.demo.custom.orm.config.DBSource;
import club.anlan.demo.custom.orm.util.ORMAnnoHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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

        /**
         *  通过反射来查询
         * @param cls
         * @param <T>
         * @return
         * @throws SQLException
         * @throws IllegalAccessException
         * @throws InstantiationException
         */
        public <T> List<T> list(Class<T> cls) throws SQLException, IllegalAccessException, InstantiationException {
            String sql = "select %s from %s";
            StringBuilder columns = new StringBuilder();
            Field[] fs = cls.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                columns.append(ORMAnnoHelper.getColumnName(fs[i]));
                if (i != fs.length - 1) {
                    columns.append(",");
                }
            }
            sql = String.format(sql, columns.toString(), ORMAnnoHelper.getTableName(cls));
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<T> list = new ArrayList<T>();
            T obj = null;
            while (rs.next()) {
                obj = cls.newInstance();
                for (Field field : fs) {
                    Class<?> type = field.getType();
                    field.setAccessible(true);
                    if (type == String.class) {
                        field.set(obj, rs.getString(ORMAnnoHelper.getColumnName(field)));
                    } else if (type == int.class || type == Integer.class) {
                        field.set(obj, rs.getInt(ORMAnnoHelper.getColumnName(field)));
                    } else if (type == double.class || type == Double.class) {
                        field.set(obj, rs.getDouble(ORMAnnoHelper.getColumnName(field)));
                    } else if (type == Date.class) {
                        field.set(obj, rs.getDate(ORMAnnoHelper.getColumnName(field)));
                    }
                }
                list.add(obj);
            }
            stmt.close();
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
