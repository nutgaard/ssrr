package no.utgdev.ssrr.filter;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;

public class ReactFilter extends ContentTransformationFilter {
    private static final String frontendpath = "./../../../../../frontend/";
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    static {
        try {
            engine.eval(read("nashorn-polyfill.js"));
            engine.eval(read("jvm-npm.js"));
            engine.eval(read("react.js"));
            engine.eval(read("react-dom-server.js"));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    private static TriFunction<HttpServletRequest, FilterConfig, String, String> transformation = (req, filterConfig, content) -> {
        if (content == null) {
            return "";
        }

        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document html = docBuilder.parse(toInputStream(content));
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression renderDiv = xpath.compile(".//div[@id='application']");

            Node node = (Node) renderDiv.evaluate(html, XPathConstants.NODE);

            return content;
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
            return content;
        }
    };

    private static InputStream toInputStream(String content) {
        try {
            return new ByteArrayInputStream(content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ByteArrayInputStream(new byte[0]);
        }
    }

    public ReactFilter() {
        super(transformation);
    }

    public static Reader read(String path) {
        InputStream in = ReactFilter.class.getResourceAsStream(path);
        return new InputStreamReader(in);
    }
}
