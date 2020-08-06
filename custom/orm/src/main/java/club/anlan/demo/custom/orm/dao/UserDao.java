package club.anlan.demo.custom.orm.dao;

import club.anlan.demo.custom.orm.bean.User;
import club.anlan.demo.custom.orm.dao.base.Dao;

import java.util.List;

/**
 * 类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/6 23:20
 */
public class UserDao implements Dao<User> {
    public int save(User obj) {
        return 0;
    }

    public int update(User obj) {
        return 0;
    }

    public int delete(User obj) {
        return 0;
    }

    public List<User> findAll() {
            return null;
    }
}
