package club.anlan.demo.custom.mvc.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 * 业务控制的父类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/9 18:27
 */
public class SupportAction {
    protected HttpServlet request;
    protected HttpSession session;

    protected final String SUCCESS = "success";
    protected final String FAIL = "fail";

    // 业务控制器执行的方法
    public String execute() {
        return SUCCESS;
    }
}
