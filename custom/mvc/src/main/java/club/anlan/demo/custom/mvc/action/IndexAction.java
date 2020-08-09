package club.anlan.demo.custom.mvc.action;

/**
 * 类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/9 18:29
 */
public class IndexAction extends SupportAction {

    @Override
    public String execute() {

        if(session.getAttribute("username")==null){
            return FAIL;
        }
        return super.execute();
    }
}
