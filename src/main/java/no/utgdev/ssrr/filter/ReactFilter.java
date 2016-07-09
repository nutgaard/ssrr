package no.utgdev.ssrr.filter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.util.function.BiConsumer;

public class ReactFilter extends ContentTransformationFilter {
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    private static final Invocable invocable = (Invocable) engine;


    static {
        try {
            engine.eval(read("nashorn-polyfill.js"));
            engine.eval(read("bundle-server.js"));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    private static QuadFunction<HttpServletRequest, HttpServletResponse, FilterConfig, String, String> transformation = (req, resp, filterConfig, content) -> {
        if (content == null) {
            return "";
        }

        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document html = docBuilder.parse(toInputStream(content));
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression renderDiv = xpath.compile(".//div[@id='application']");

            Node node = (Node) renderDiv.evaluate(html, XPathConstants.NODE);

            String requestUrl = req.getRequestURI();
            if (req.getQueryString() != null) {
                requestUrl += '?' + req.getQueryString();
            }


            BiConsumer<Integer, String> reply = (status, reactContent) -> {
                try {
                    if (status == 404) {
                        resp.sendError(404);
                        return;
                    }

                    Element reactElement = docBuilder.parse(toInputStream(reactContent)).getDocumentElement();
                    node.appendChild(html.importNode(reactElement, true));
                    String newContent = convertToString(html).replaceAll("\\n", "").replaceAll("\\r", "");

                    resp.setContentLength(newContent.length());
                    resp.setStatus(status);
                    resp.getWriter().write(newContent);
                } catch (TransformerException | SAXException | IOException e) {
                    e.printStackTrace();
                }
            };


            invocable.invokeFunction("render", requestUrl, reply);
            return content;

        } catch (ParserConfigurationException | NoSuchMethodException | ScriptException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
            return content;
        }
    };

    private static String convertToString(Document doc) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StringWriter stringWriter = new StringWriter();

        transformer.transform(source, new StreamResult(stringWriter));

        return stringWriter.toString();
    }

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
