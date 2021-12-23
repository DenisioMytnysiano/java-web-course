package com.denisio.app.controller.servlets;

import com.denisio.app.model.entity.Client;
import com.denisio.app.model.service.ClientService;
import com.denisio.app.utils.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "ClientServlet", urlPatterns = {"/clients/block", "/clients/unblock", "/clients/register"})
public class ClientServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(ClientServlet.class);
    private final ClientService clientService = new ClientService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String email = req.getParameter("email");
        HttpSession session = req.getSession();
        switch (uri) {
            case "/clients/block":
                clientService.blockClient(email);
                session.setAttribute("message", String.format("Account %s is blocked", email));
                resp.sendRedirect("/");
                break;
            case "/clients/unblock":
                clientService.unblockClient(email);
                session.setAttribute("message", String.format("Account %s is unblocked", email));
                resp.sendRedirect("/");
                break;
            case "/clients/register":
                String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                Client client = objectMapper.readValue(requestBody, Client.class);
                client.setPassword(PasswordEncoder.hashPassword(client.getPassword()));
                clientService.registerClient(client);
                session.setAttribute("message", String.format("Account %s is registered", email));
                resp.sendRedirect("/");
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}
