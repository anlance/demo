package club.anlan.demo.custom.orm.bean;

import club.anlan.demo.custom.orm.annotation.Column;
import club.anlan.demo.custom.orm.annotation.Table;
import lombok.Data;

/**
 * User类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/4 23:40
 */
@Data
@Table("user")
public class User {

    @Column(value="user_id", isId=true)
    private long id;

    @Column("username")
    private String name;

    @Column("name")
    private String nickName;

    private String password;

    private String phone;

    private String email;
}
