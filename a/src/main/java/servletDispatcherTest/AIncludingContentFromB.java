package servletDispatcherTest;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AIncludingContentFromB extends A {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);

        HttpServletRequest servletRequest = (HttpServletRequest) request;

        ServletContext context = servletRequest.getSession().getServletContext().getContext("/b");

        RequestDispatcher requestDispatcher = context.getRequestDispatcher("/anything");

        requestDispatcher.include(request, response);


    }
}
