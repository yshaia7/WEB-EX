package ex2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is filter class that guarantee that
 * no request will excepted if the poll are not
 * exist yet, or the file input are not validate
 *
 * for each web request we will first check if condition
 * that describe above are valid and only if they are we
 * will continue to the poll
 */
@WebFilter(
        filterName = "WebUrlFilter",
        urlPatterns = {"/initialPollServlet/*", "/loadQuizServlet/*", "/manageVoteServlet/*"})
public class WebUrlFilter implements javax.servlet.Filter {

    /**
     * If poll equal to null mean the poll are not create yet so we cant load
     * the poll quiz yet
     * if num of answer smaller then 2 its mean we dont have at list one
     * question and 2 answers in the pool
     *
     * @param request  - from client
     * @param response - to server
     * @param chain    - for filter action
     * @throws ServletException - servlet general exception
     */
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain) throws ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        RequestDispatcher rd;

        /* check if file is valid and legal*/
        if ((Poll) (request.getServletContext().getAttribute("poll")) == null ||
           ((Poll) request.getServletContext().getAttribute("poll")).getNumOfAnswers() < 2) {
            rd = request.getRequestDispatcher("error_input.html");
            try {
                rd.forward(request, response);
            } catch (IOException e) {
            }
        }

        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
        }
    }
}
