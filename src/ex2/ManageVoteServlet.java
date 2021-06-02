package ex2;


import org.jetbrains.annotations.NotNull;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * The ManageVoteServlet is load the answers and
 * present for each question the number of vote
 * it will store the information in html and send
 * it to the client
 *
 * That servlet also increase number of vote, each
 * client can vote only once and validation of client
 * to vote will be checked by session
 *
 * do post request add vote to specific answer that specific
 * do get request show the poll vot result
 *
 *
 * functional
 * 1. create the poll as answers and send to the client
 * 2. increase the vote number if needed (will valid if need by use session)
 */
@WebServlet(name = "ManageVoteServlet", urlPatterns = "/manageVoteServlet")
public class ManageVoteServlet extends HttpServlet {
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

    /**
     *  get request that show the answers
     *  if user already vote we dont need
     *  to increase the votes so we only
     *  show the poll
     *
     * @param request - from the client
     * @param response - to the client
     * @throws ServletException -  servlet general exception
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        createPollAnswers(request, response);
    }

    /**
     *  post request get the number of answer the client choose
     *  valid if its the first vote of the user and increase the
     *  answer vote if require
     *
     * @param request - from the client
     * @param response - to the client
     * @throws ServletException -  servlet general exception
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
            handlePoll(request, response);
    }

    /**
     * check if the submit button are not null
     * then continue to the poll
     * if its null show the client msg to choose
     * one of the poll question
     *
     * @param request - from the client
     * @param response - to the client
     * @throws ServletException -  servlet general exception
     */
    public void handlePoll(@NotNull HttpServletRequest request, HttpServletResponse response)throws ServletException {
        /* if client dosent choose answer */
        if (request.getParameter("rdbtn") == null) {
            RequestDispatcher rd2 = request.getRequestDispatcher("resubmit.html");
            request.removeAttribute("rdbtn");
            try {
                rd2.forward(request, response);
            }catch (IOException e) {}
        }

        Integer vote = Integer.parseInt(request.getParameter("rdbtn"));

        /*check if the client already vote by using is session */
        if (sessionChacker(request))
            poll.increaseNumOfVote(vote);
        else {
            RequestDispatcher rd2 = request.getRequestDispatcher("vote_validate.html");
            try {
                rd2.forward(request, response);
            }catch (IOException e ){}
        }
        /* in any case we still want to show the poll answers and the votes */
        createPollAnswers(request, response);
    }

    /**
     * create the poll answers and vote and send it to the client
     *
     * @param response - to the client
     * @param request - to the server
     * @throws ServletException - general servlet exception
     */
    private void createPollAnswers(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        PrintWriter out = null;
        try {
            out = response.getWriter();
        }
        catch (IOException e){}

        response.setContentType("text/html; charset=UTF-8");

        /* define the action when we click on submit */
        try {
            request.getRequestDispatcher("header.html").include(request, response);
        } catch (IOException e) { }

        /* define the action when we click on submit */
        out.println("<text>Result of the poll </text>" + "<b>"
                    + poll.getPollQuestion() + "</b>" + "<br>");
        out.println("<form method=\"get\" action=\"loadQuizServlet\">"
                    + " <div class=\"btn-group-vertical\">");
        out.println("</b>" + "<br>");

        /* poll answers and votes */
        for (int place = 0; place < poll.getNumOfAnswers(); place++)
            out.println(poll.getAnswer(place) + " " + poll.getNumOfVote(place) + "<br>");
        out.println("<br>");
        out.println("</div> <input type=\"submit\" value=\"click here for vote\"></form>");

        /* close the html tags*/
        try {
            request.getRequestDispatcher("footer.html").include(request, response);
        } catch (IOException e) { }
        out.close();
    }

    /**
     * check if the user already vote by session.
     * True = new client add is vote
     * false = client already exist dont increase vote number
     *
     * @param request - for get the session
     * @return True if its new client False if the client already exist
     */
    private boolean sessionChacker(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
            session.setAttribute("voted", true);
            return true;
        }
        return false;
    }
}


