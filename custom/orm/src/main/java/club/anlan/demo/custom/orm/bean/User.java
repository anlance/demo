package club.anlan.demo.custom.orm.bean;

import lombok.Data;

/**
 * User类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/4 23:40
 */
@Data
public class User {

    private long id;

    private String name;

    private String password;

    private String phone;

    private String email;
}
