package club.anlan.demo.custom.orm.xmlparser;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 对应 beans.xml 文件中的 property 标签的属性结构
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/5 0:07
 */
@Data
@AllArgsConstructor
public class PropsInfo {

    private String name;

    private String value;

    private String type;

}
