package ex2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The InitialPollServlet is initial all the program need.
 * he get the file name and deliver it to poll class
 *
 * That servlet also take care if the input file is wrong
 * (line in file.txt small then 3 or not a validate file)
 *
 * do post request are not allow
 * do get request forward to the servlet create the question and answers
 * functional
 * 1. get and create the file poll path by stream
 * 2. create the poll and store them in the context
 *
 */

@WebServlet(name = "InitialPollServlet", urlPatterns = "/initialPollServlet", initParams =
@WebInitParam(name = "filename", value = "poll.txt"))
public class InitialPollServlet extends HttpServlet {

    /**
     * Initial the poll for all the the servlets
     * Store the poll into the context
     * it will happened one time
     *
     * @param servletConfig - init parameter
     * @throws ServletException - servlet general exception
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        Poll poll = null;
        /* get the file name path */
        String pollFileName = servletConfig.getInitParameter("filename");
        URL url = null;
        try {
            url = getServletContext().getResource(pollFileName);
        } catch (MalformedURLException e) { }

        /* create the poll */
        try {
            poll = new Poll(url);
            servletConfig.getServletContext().setAttribute("poll", poll);
        }
        /* if file cannot be read or num of line in the file wrong */
        catch (FileException e) { }

    }

    /**
     * in case we use method that are not allow that
     * function forward the client to the error page
     *
     * @param request - from the client
     * @param response - to the client
     * @throws ServletException -  servlet general exception
     */
    private void closeWebSite(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException {
        RequestDispatcher rd2 = request.getRequestDispatcher("error_input.html");
        try{
            rd2.forward(request, response);
        }
        catch (IOException e){}
    }

    /**
     * do post method are not allow from InitialPollServlet
     * will close the website
     *
     * @param request - from the client
     * @param response - to the client
     * @throws ServletException -  servlet general exception
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException {
        RequestDispatcher rd2 = request.getRequestDispatcher("wrong_method.html");
        try {
            rd2.forward(request, response);
        }catch (IOException e){}
    }

    /**
     * After the init done in the current servlet we forward the
     * power to the LoadQuizServlet and ManageVoteServlet for
     * handle the poll
     *
     * @param request - from the client
     * @param response - to the client
     * @throws ServletException -  servlet general exception
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException {

        try {
            PrintWriter out = response.getWriter();
            /* forward to the servlet that create the poll question */
            request.getRequestDispatcher("manageVoteServlet").forward(request, response);
        } catch (IOException e) {
        }
    }
}
