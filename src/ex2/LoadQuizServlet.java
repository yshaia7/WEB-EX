package ex2;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The LoadQuizServlet is load the question and answers
 * of the poll, it will use html with radio button and submit button.
 *
 * in that servlet the do post request are not allow
 * in case try use post the servlet forward the request to
 * initialPollServlet to shut down the website
 *
 * That servlet also take care if the input file is wrong
 * (line in file.txt small then 3 or not a validate file)
 *
 * method:
 * do post request are not allow
 * do get request create the question and and answers and send to the client
 *
 * functional
 * 1. create the poll as radio button + submit btn
 * 2. send the question that choosed in post request
 *
 */
@WebServlet(name = "LoadQuizServlet", urlPatterns = "/loadQuizServlet")
public class LoadQuizServlet extends HttpServlet {
    private Poll poll;

    /**
     * Initial the poll from the context
     * it will happened one time
     *
     * @param servletConfig - init parameter
     * @throws ServletException - servlet general exception
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        poll = (Poll) servletConfig.getServletContext().getAttribute("poll");
    }

    /*
     * do post method are not allow from LoadQuizServlet
     * will forward to InitialPollServlet for close the website
     *
     * @param request - from the client
     * @param response - to the client
     * @throws ServletException -  servlet general exception
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException {
        try {
            request.getRequestDispatcher("InitialPollServlet").forward(request, response);
        } catch (IOException e) { }
    }

    /**
     * do get will create the question answers of the poll
     * and send it to the client
     *
     * @param request - from the client
     * @param response - to the client
     * @throws ServletException -  servlet general exception
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException {
        createPollQuestion(request, response);
    }

    /**
     * That method create the html question and the
     * answers to vote and send it to the client
     *
     * @param response - for create the question html page and send to client
     * @param request - to the serve
     * @throws ServletException - general servlet exception
     */
    private void createPollQuestion(HttpServletRequest request
                ,HttpServletResponse response) throws ServletException {
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) { }

        /* include for the head of the html */
        try {
            request.getRequestDispatcher("header.html").include(request, response);
        } catch (IOException e) { }

        /* define the action when we click on submit */
        out.println("<form method=\"post\" action=\"manageVoteServlet\"" +
                    " onsubmit=\"return validateForm()\">"
                    + " <div class=\"btn-group-vertical\">");
        out.println("<b>" + poll.getPollQuestion() + "</b>" + "<br>" + "<br>");

        /* create the option of vote as radio button */
        for (int i = 0; i < poll.getNumOfAnswers(); i++) {
            out.println("<input type=\"radio\" name=\"rdbtn\" + value = " + i +
                        " class=\"btn btn-primary\">" + poll.getAnswer(i) + "</radio><br>");
        }
        out.println("<br>");
        out.println("</div> <input type=\"submit\"> </form>");

        /* close the html tags*/
        try {
            request.getRequestDispatcher("footer.html").include(request, response);
        } catch (IOException e) { }
        out.close();
    }
}
