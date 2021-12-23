package com.denisio.app.controller.servlets;

import com.denisio.app.model.entity.Client;
import com.denisio.app.model.entity.ClientTariffPlan;
import com.denisio.app.model.service.ClientService;
import com.denisio.app.model.service.ClientTariffPlanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(AccountServlet.class);
    final private ClientTariffPlanService tariffPlanService = new ClientTariffPlanService();
    final private ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client user = (Client) req.getSession().getAttribute("user");

        if (user != null) {
            req.setAttribute("user", user);
            List<ClientTariffPlan> tariffPlans = tariffPlanService.getTariffPlansByUser(user);
            req.setAttribute("subscriptions", tariffPlans);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/account.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String amountStr = req.getParameter("amount");
        Client user = (Client) req.getSession().getAttribute("user");
        if (user != null) {
            HttpSession session = req.getSession();
            try {
                BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));
                clientService.replenishAccount(user, amount);
                session.setAttribute("message", "Account has been replenished successfully!");
                res.sendRedirect("/account");
            } catch (NumberFormatException e) {
                logger.error(e);
                session.setAttribute("error", "Invalid amount to replenish!");
                res.sendRedirect("/account");
            }
        } else {
            res.sendRedirect("/login");
        }
    }
}
