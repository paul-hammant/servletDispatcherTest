package servletDispatcherTest;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AFilterThatOutputsTheRequestsThreadID implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType("text/plain");
        response.getWriter().write("/a/AFilterThatOutputsTheRequestsThreadID: thread = " + System.identityHashCode(Thread.currentThread()) + "\n");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        httpServletRequest.getSession(true);
        response.getWriter().write("/a/AFilterThatOutputsTheRequestsThreadID: JSESSIONID = " + httpServletRequest.getRequestedSessionId() + "\n");
    }

    public void destroy() {
    }
}
