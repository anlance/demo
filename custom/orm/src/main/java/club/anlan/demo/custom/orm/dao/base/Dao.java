package club.anlan.demo.custom.orm.dao.base;

import java.util.List;

/**
 * dao 类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/6 23:18
 */
public interface Dao<T> {
    public int save(T obj);

    public int update(T obj);

    public int delete(T obj);

    public List<T> findAll();
}
