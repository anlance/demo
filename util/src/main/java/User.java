import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author lan
 * @version 1.0
 * @date 2021/4/24 15:28
 */
@Data
public class User {

    private String id;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 密码
     */
    private String loginPass;

    /**
     * 密码加密盐值
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String userEmail;


    /**
     * 用户头像定位
     */
    private String userPic;

    /**
     * 描述
     */
    private String memo;


    /**
     * 最后一次登录时间
     */
    private LocalDateTime lastLoginDate;


    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 是否删除，1-被删除
     */
    private Integer deleteFlag;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public static User init(){
        User user = new User();
        System.out.println(user);
        return user;
    }
}
