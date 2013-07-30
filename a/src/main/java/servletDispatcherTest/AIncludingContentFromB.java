package servletDispatcherTest;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

public class AIncludingContentFromB
        extends AFilterThatOutputsTheRequestsThreadID {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);

        String bURL = request.getParameter("bURL");
        boolean extractPayload = "true".equals(request.getParameter("extractPayload"));

        if (bURL == null) {
            response.getWriter().write("** you need to specify a bURL query string parameter **");
        } else {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            ServletContext context = servletRequest.getSession().getServletContext().getContext("/b");
            RequestDispatcher requestDispatcher = context.getRequestDispatcher("/" + bURL);

            if (!extractPayload) {
                requestDispatcher.include(request, response);
            } else {
                MyHttpServletResponse newResponse = new MyHttpServletResponse((HttpServletResponse) response);
                requestDispatcher.include(request, newResponse);
                response.getWriter().write("Extracted from temp response, and rewritten: [\n"
                        + newResponse.baos.toString() + "]");
            }

        }
    }

    private static class MyHttpServletResponse implements HttpServletResponse {

        private HttpServletResponse original;
        private String contentType;
        private int contentLen;
        private Locale locale;

        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private final ServletOutputStream saos = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                baos.write(b);
                flush();
            }
        };

        private final PrintWriter writer = new PrintWriter(baos) {
            @Override
            public void write(String s) {
                super.write(s);
                flush();
            }
        };

        private MyHttpServletResponse(HttpServletResponse original) {
            this.original = original;
        }

        public void addCookie(Cookie cookie) {

        }

        public boolean containsHeader(String name) {
            return original.containsHeader(name);
        }

        public String encodeURL(String url) {
            return original.encodeURL(url);
        }

        public String encodeRedirectURL(String url) {
            return original.encodeRedirectURL(url);
        }

        public String encodeUrl(String url) {
            return original.encodeUrl(url);
        }

        public String encodeRedirectUrl(String url) {
            return encodeRedirectUrl(url);
        }

        public void sendError(int sc, String msg) throws IOException {
            original.sendError(sc, msg);

        }

        public void sendError(int sc) throws IOException {
            original.sendError(sc);

        }

        public void sendRedirect(String location) throws IOException {
            original.sendRedirect(location);
        }

        public void setDateHeader(String name, long date) {
            original.setDateHeader(name, date);
        }

        public void addDateHeader(String name, long date) {
            original.addDateHeader(name, date);

        }

        public void setHeader(String name, String value) {
            original.setHeader(name, value);
        }

        public void addHeader(String name, String value) {
            original.addHeader(name, value);
        }

        public void setIntHeader(String name, int value) {
            original.setIntHeader(name, value);
        }

        public void addIntHeader(String name, int value) {
            original.addIntHeader(name, value);
        }

        public void setStatus(int sc) {
            original.setStatus(sc);
        }

        public void setStatus(int sc, String sm) {
            original.setStatus(sc);
        }

        public String getCharacterEncoding() {
            return original.getCharacterEncoding();
        }

        public String getContentType() {
            return original.getContentType();
        }

        public ServletOutputStream getOutputStream() throws IOException {
            return saos;
        }

        public PrintWriter getWriter() throws IOException {
            return writer;
        }

        public void setCharacterEncoding(String charset) {
            original.setCharacterEncoding(charset);
        }

        public void setContentLength(int len) {
            contentLen = len;
        }

        public void setContentType(String type) {
            contentType = type;
        }

        public void setBufferSize(int size) {
        }

        public int getBufferSize() {
            return 0;
        }

        public void flushBuffer() throws IOException {

        }

        public void resetBuffer() {

        }

        public boolean isCommitted() {
            return false;
        }

        public void reset() {

        }

        public void setLocale(Locale loc) {
            locale = loc;
        }

        public Locale getLocale() {
            return locale;
        }
    }
}
