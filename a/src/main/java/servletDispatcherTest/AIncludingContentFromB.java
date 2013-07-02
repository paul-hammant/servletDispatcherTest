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

        if (bURL == null) {
            response.getWriter().write("** you need to specify a bURL query string parameter **");

        } else {


            HttpServletRequest servletRequest = (HttpServletRequest) request;
            ServletContext context = servletRequest.getSession().getServletContext().getContext("/b");
            RequestDispatcher requestDispatcher = context.getRequestDispatcher("/" + bURL);

            requestDispatcher.include(request, response);

            //MyHttpServletResponse newResponse = new MyHttpServletResponse();
            //requestDispatcher.include(request, newResponse);
            //response.getWriter().write(">>" + newResponse.baos.toString() + "<<");

        }


    }

    private static class MyHttpServletResponse implements HttpServletResponse {

        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private final ServletOutputStream saos = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                baos.write(b);
            }
        };
        private final PrintWriter writer = new PrintWriter(baos);

        public void addCookie(Cookie cookie) {

        }

        public boolean containsHeader(String name) {
            return false;
        }

        public String encodeURL(String url) {
            return null;
        }

        public String encodeRedirectURL(String url) {
            return null;
        }

        public String encodeUrl(String url) {
            return null;
        }

        public String encodeRedirectUrl(String url) {
            return null;
        }

        public void sendError(int sc, String msg) throws IOException {

        }

        public void sendError(int sc) throws IOException {

        }

        public void sendRedirect(String location) throws IOException {

        }

        public void setDateHeader(String name, long date) {

        }

        public void addDateHeader(String name, long date) {

        }

        public void setHeader(String name, String value) {

        }

        public void addHeader(String name, String value) {

        }

        public void setIntHeader(String name, int value) {

        }

        public void addIntHeader(String name, int value) {

        }

        public void setStatus(int sc) {

        }

        public void setStatus(int sc, String sm) {

        }

        public String getCharacterEncoding() {
            return null;
        }

        public String getContentType() {
            return null;
        }

        public ServletOutputStream getOutputStream() throws IOException {
            return saos;
        }

        public PrintWriter getWriter() throws IOException {
            return writer;
        }

        public void setCharacterEncoding(String charset) {

        }

        public void setContentLength(int len) {

        }

        public void setContentType(String type) {

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

        }

        public Locale getLocale() {
            return null;
        }
    }
}
