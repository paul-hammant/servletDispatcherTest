package servletDispatcherTest;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class A implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpsr = (HttpServletRequest) request;

        response.setContentType("text/plain");
        response.getWriter().write("A thread = " + System.identityHashCode(Thread.currentThread()) + "\n");

        ServletContext context = httpsr.getSession().getServletContext().getContext("/b");

        RequestDispatcher requestDispatcher = context.getRequestDispatcher("/anything");

        requestDispatcher.include(request, response);

    }

    public void destroy() {

    }
}
