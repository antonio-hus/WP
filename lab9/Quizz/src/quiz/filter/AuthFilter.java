package quiz.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A servlet filter that enforces authentication for all resources under the "/protected/*" path.
 * It checks if a valid user session exists. If not, it redirects the user to the login page.
 */
@WebFilter("/protected/*")
public class AuthFilter implements Filter {

    /**
     * This method is called by the servlet container for each request that matches the filter's URL pattern.
     * It performs the authentication check and either allows the request to proceed or redirects the user.
     *
     * @param request  The ServletRequest object representing the client's request.
     * @param response The ServletResponse object representing the server's response.
     * @param chain    The FilterChain object that allows the request to proceed to the next filter or the target servlet.
     * @throws IOException      If an I/O error occurs during the processing of the filter.
     * @throws ServletException If a servlet-specific error occurs during the processing of the filter.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Cast the ServletRequest and ServletResponse objects to their HTTP counterparts
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Retrieve the current HTTP session, but do not create one if it doesn't exist (pass 'false')
        HttpSession session = req.getSession(false);

        // Check if the session is null (doesn't exist) or if the 'user' attribute is not present in the session
        if (session == null || session.getAttribute("user") == null) {

            // If no valid user session is found, redirect the user to the login page
            resp.sendRedirect(req.getContextPath() + "/login");
            return; // Stop the current request from proceeding further
        }

        // If a valid user session exists, allow the request to proceed to the next filter in the chain or the target servlet
        chain.doFilter(request, response);
    }
}