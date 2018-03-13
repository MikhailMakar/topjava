package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.AuthorizedUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");

        String userId = request.getParameter("user");
        Integer id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            id = null;
        }
        if (id != null) {
            AuthorizedUser.setId(id);
            response.sendRedirect("meals");
        } else {
            request.getRequestDispatcher("/users.jsp").forward(request, response);
        }
    }

}
