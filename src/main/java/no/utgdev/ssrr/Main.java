package no.utgdev.ssrr;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

import static no.utgdev.ssrr.Utils.filepattern;

public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final RequestDispatcher dispatcher = getServletContext().getNamedDispatcher("default");
        boolean isFile = filepattern.matcher(req.getRequestURI()).find();

        final String resource = isFile ? req.getRequestURI() : "/index.html";
        final HttpServletRequest wrapper = getHttpServletRequest(req, resource);

        dispatcher.forward(wrapper, resp);
    }

    private HttpServletRequest getHttpServletRequest(final HttpServletRequest req, final String url) {
        return new HttpServletRequestWrapper(req) {
            @Override
            public String getServletPath() {
                return "";
            }

            @Override
            public String getPathInfo() {
                return url;
            }
        };
    }
}
