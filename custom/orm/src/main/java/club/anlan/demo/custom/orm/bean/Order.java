package club.anlan.demo.custom.orm.bean;

import lombok.Data;

/**
 * Order类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/4 23:47
 */
@Data
public class Order {

    private long id;

    private String orderNo;

    private String address;

    private float price;
}
