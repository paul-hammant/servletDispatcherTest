package servletDispatcherTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AServletThatOutputsTheRequestsThreadID extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("/b/AServletThatOutputsTheRequestsThreadID: thread = " + System.identityHashCode(Thread.currentThread())+ "\n");
        req.getSession(true);
        resp.getWriter().write("/b/AServletThatOutputsTheRequestsThreadID: JSESSIONID = " + req.getRequestedSessionId() + "\n");

    }
}
