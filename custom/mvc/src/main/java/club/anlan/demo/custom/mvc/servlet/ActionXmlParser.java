package club.anlan.demo.custom.mvc.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 业务控制器配置中心xml解析器
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/9 18:36
 */
public class ActionXmlParser {


    private HashMap<String, Action> actions;

    public HashMap<String, Action> parse(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        actions = new HashMap<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        saxParser.parse(is, new DefaultHandler() {
            private Action action = null;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if ("action".equals(qName)) {
                    action = new Action();
                    action.setPath(attributes.getValue("path"));
                    action.setName(attributes.getValue("name"));
                    action.setResults(new HashMap<String, Result>());
                } else if ("result".equals(qName)) {
                    action.getResults().put(attributes.getValue("name"),
                            new Result(attributes.getValue("name"), attributes.getValue("path"),
                                    Boolean.parseBoolean(attributes.getValue("redirect"))));
                }
            }


            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if ("action".equals(qName)) {
                    actions.put(action.getPath(), action);
                    action = null;
                }
            }
        });

        return actions;
    }

    @Data
    public static class Action {
        private String name;
        private String path;

        private HashMap<String, Result> results;
    }

    @Data
    @AllArgsConstructor
    public static class Result {
        private String name;
        private String path;
        private boolean redirect;
    }

}
