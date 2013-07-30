package servletDispatcherTest;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AnotherFilterThatOutputsTheRequestsThreadID implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        response.getWriter().write("/b/AnotherFilterThatOutputsTheRequestsThreadID: thread = " + System.identityHashCode(Thread.currentThread()) + "\n");
        HttpServletRequest req = (HttpServletRequest) request;
        req.getSession(true);
        response.getWriter().write("/a/AnotherFilterThatOutputsTheRequestsThreadID: JSESSIONID = " + req.getRequestedSessionId() + "\n");


    }

    public void destroy() {

    }
}
