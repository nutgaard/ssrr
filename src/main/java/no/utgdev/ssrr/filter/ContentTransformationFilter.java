package no.utgdev.ssrr.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContentTransformationFilter implements Filter {

    private FilterConfig filterConfig;
    private TriFunction<HttpServletRequest, FilterConfig, String, String> transformation;

    public ContentTransformationFilter(TriFunction<HttpServletRequest, FilterConfig, String, String> transformation) {
        this.transformation = transformation;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding("UTF-8");
        }
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        HtmlResponseWrapper capturingResponseWrapper = new HtmlResponseWrapper(httpResp);

        chain.doFilter(request, capturingResponseWrapper);

        String content = capturingResponseWrapper.getCaptureAsString();
        boolean isHtml = response.getContentType() != null && response.getContentType().contains("text/html");

        String output = content;
        if (isHtml) {
            output = transformation.apply(httpReq, filterConfig, content);
        }


        response.setContentLength(output.length());
        response.getWriter().write(output);
    }

    public void destroy() {

    }
}
