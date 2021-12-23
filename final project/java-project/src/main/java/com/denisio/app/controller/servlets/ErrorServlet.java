package com.denisio.app.controller.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "errorHandler", urlPatterns = {"/error"})
public class ErrorServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {

        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");

        System.out.println(statusCode);

        ServletContext context = request.getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher("/layouts/error.jsp");
        request.setAttribute("statusCode", statusCode);
        rd.forward(request, response);
    }
}
