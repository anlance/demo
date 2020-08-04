package club.anlan.demo.custom.orm.xmlparser;

import lombok.Data;

import java.util.List;

/**
 * 对应 beans.xml 文件中的 bean 标签的属性结构
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/5 0:05
 */
@Data
public class BeanInfo {

    private String id;
    /**
     * 对应 class 属性
     */

    private String clsName;

    private String scope;

    private List<PropsInfo> props;
}
