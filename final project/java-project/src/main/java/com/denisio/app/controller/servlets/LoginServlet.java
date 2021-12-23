package com.denisio.app.controller.servlets;

import com.denisio.app.model.entity.Client;
import com.denisio.app.model.service.ClientService;
import com.denisio.app.utils.PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(LoginServlet.class);

    private final ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("pwd");
        password = PasswordEncoder.hashPassword(password);

        Optional<Client> optionalUser = clientService.getClient(email, password);

        if (optionalUser.isPresent() && !optionalUser.get().isBlocked()) {
            HttpSession session = req.getSession();
            session.setAttribute("user", optionalUser.get());
            session.setMaxInactiveInterval(30 * 60);

            Cookie userName = new Cookie("email", email);
            userName.setMaxAge(30 * 60);
            resp.addCookie(userName);
            resp.sendRedirect("/");

        } else {
            RequestDispatcher rd = req.getRequestDispatcher("layouts/login.jsp");
            if (optionalUser.isPresent()) {
                req.setAttribute("errMsg", "Sorry, this account is blocked. Contact us to find details.");
                logger.info("Failed login for client with password {}, account is blocked", email);
            }
            else {
                req.setAttribute("errMsg", "Email or password is wrong");
                logger.info("Failed login for client with password {}, email or password is wrong", email);
            }
            rd.include(req, resp);
        }

    }
}
