package club.anlan.demo.custom.mvc.servlet;

import club.anlan.demo.custom.mvc.action.SupportAction;
import lombok.SneakyThrows;
import org.xml.sax.SAXException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 自定义核心控制器
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/9 17:08
 */
public class DispatcherServlet extends HttpServlet {

    HashMap<String, ActionXmlParser.Action> actions;

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("---init-config---");
        String configPath = config.getInitParameter("config");
        if (configPath.startsWith("classpath")) {
            configPath = configPath.replace("classpath", "");
            InputStream is = ClassLoader.getSystemResourceAsStream(configPath);
            try {
                actions = new ActionXmlParser().parse(is);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void init() throws ServletException {
        System.out.println("---init---");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("---service-http---" + req.getMethod());

        String path = req.getRequestURI();
        String basePath = req.getContextPath();
        path = path.replace(basePath, "");

        ActionXmlParser.Action action = actions.get(path);
        if (action == null) {
            req.getRequestDispatcher("404.xml").forward(req, resp);
            return;
        }

        try {

            Class<SupportAction> actionCls = (Class<SupportAction>) Class.forName(action.getName());
            SupportAction sAction = actionCls.newInstance();

            // 注入request and session
            Field reqField = actionCls.getDeclaredField("request");
            reqField.setAccessible(true);
            reqField.set(sAction, req);
            Field sessionField = actionCls.getDeclaredField("session");
            sessionField.setAccessible(true);
            sessionField.set(sAction, req.getSession());

            // 执行业务控制器的业务方法
            String resultName = sAction.execute();

            // 根据返回结果获取 result 中的 path
            ActionXmlParser.Result result = action.getResults().get(resultName);
            String resultPath = result.getPath();

            if (result.isRedirect()) {
                resp.sendRedirect(resultPath);
            } else {
                req.getRequestDispatcher(resultPath).forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
