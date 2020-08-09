package club.anlan.demo.custom.orm.factory;

import club.anlan.demo.custom.orm.config.DBSource;
import club.anlan.demo.custom.orm.util.ORMAnnoHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
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
         * 通过反射来查询
         *
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
                    } else if (type == long.class || type == Long.class) {
                        field.set(obj, rs.getLong(ORMAnnoHelper.getColumnName(field)));
                    }
                }
                list.add(obj);
            }
            stmt.close();
            return list;
        }

        /**
         * 插入
         *
         * @param obj
         * @return
         */
        public int save(Object obj) throws SQLException, IllegalAccessException {
            String sql = "insert into %s(%s) values(%s)";
            StringBuilder columns = new StringBuilder();
            StringBuilder params = new StringBuilder();

            Field[] fs = obj.getClass().getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                columns.append(ORMAnnoHelper.getColumnName(fs[i]));
                params.append("?");
                if (i != fs.length - 1) {
                    columns.append(",");
                    params.append(",");
                }
            }

            sql = String.format(sql, ORMAnnoHelper.getTableName(obj.getClass()), columns.toString(), params.toString());
            PreparedStatement ps = conn.prepareStatement(sql);

            System.out.println(sql);

            // 设置预处理语句的每个参数值
            int index = 1;
            for (Field field : fs) {
                Class<?> type = field.getType();
                field.setAccessible(true);
                if (type == String.class) {
                    ps.setString(index, String.valueOf(field.get(obj)));
                } else if (type == int.class || type == Integer.class) {
                    ps.setInt(index, field.getInt(obj));
                } else if (type == double.class || type == Double.class) {
                    ps.setDouble(index, field.getDouble(obj));
                } else if (type == Date.class) {
                    ps.setDate(index, (java.sql.Date) field.get(obj));
                } else if (type == long.class || type == Long.class) {
                    ps.setLong(index, field.getLong(obj));
                }
                index++;
            }

            // 执行预处理语句
            int rows = ps.executeUpdate();
            ps.close();
            return rows;
        }

        public int update(Object obj) throws SQLException, IllegalAccessException {
            String sql = "update %s set %s  where %s";
            StringBuilder params = new StringBuilder();
            StringBuilder where = new StringBuilder();

            Field[] fs = obj.getClass().getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                if (ORMAnnoHelper.isId(fs[i])) {
                    fs[i].setAccessible(true);
                    where.append(ORMAnnoHelper.getColumnName(fs[i])).append("=").append(fs[i].get(obj));
                    continue;
                }
                params.append(ORMAnnoHelper.getColumnName(fs[i])).append("=").append("?");
                if (i != fs.length - 1) {
                    params.append(",");
                }
            }

            sql = String.format(sql, ORMAnnoHelper.getTableName(obj.getClass()), params.toString(), where);
            PreparedStatement ps = conn.prepareStatement(sql);

            System.out.println(sql);

            int index = 1;
            for (Field field : fs) {
                Class<?> type = field.getType();
                field.setAccessible(true);
                if (ORMAnnoHelper.isId(field)) {
                    continue;
                }
                if (type == String.class) {
                    ps.setString(index, String.valueOf(field.get(obj)));
                } else if (type == int.class || type == Integer.class) {
                    ps.setInt(index, field.getInt(obj));
                } else if (type == double.class || type == Double.class) {
                    ps.setDouble(index, field.getDouble(obj));
                } else if (type == Date.class) {
                    ps.setDate(index, (java.sql.Date) field.get(obj));
                } else if (type == long.class || type == Long.class) {
                    ps.setLong(index, field.getLong(obj));
                }
                index++;
            }

            // 执行预处理语句
            int rows = ps.executeUpdate();
            ps.close();
            return rows;
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
