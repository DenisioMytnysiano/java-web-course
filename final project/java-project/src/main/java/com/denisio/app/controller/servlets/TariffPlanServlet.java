package com.denisio.app.controller.servlets;

import com.denisio.app.model.entity.Client;
import com.denisio.app.model.entity.TariffPlan;
import com.denisio.app.model.service.ClientTariffPlanService;
import com.denisio.app.model.service.TariffPlanService;
import com.denisio.app.utils.FilterConfiguration;
import com.denisio.app.utils.OrderBy;
import com.denisio.app.utils.SortingDirection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "TariffPlanServlet", urlPatterns = {"/tariff-plans", "/tariff-plans/download", "/tariff-plans/purchase", "/tariff-plans/add", "/tariff-plans/update"})
public class TariffPlanServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(ClientServlet.class);
    private final ClientTariffPlanService clientTariffPlanService = new ClientTariffPlanService();
    private final TariffPlanService tariffPlanService = new TariffPlanService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/tariff-plans":
                List<TariffPlan> tariffPlans = getSortedTariffPlans(request, response);
                response.getWriter().write(objectMapper.writeValueAsString(tariffPlans));
                request.setAttribute("publications", tariffPlans);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("layouts/catalog.jsp");
                requestDispatcher.forward(request, response);
                break;

            case "/tariff-plans/purchase":
                Client user = (Client) request.getSession().getAttribute("user");
                if (user != null) {
                    Long tariffPlanId = Long.parseLong(request.getParameter("id"));
                    clientTariffPlanService.buyTariffPlan(user, tariffPlanId);
                    response.sendRedirect("/tariff-plans");
                } else {
                    response.sendRedirect("/login");
                }
                break;

            case "/tariff-plans/download":
                Long id = Long.parseLong(request.getParameter("id"));
                Optional<TariffPlan> plan = tariffPlanService.getTariffPlanById(id);
                if (!plan.isPresent()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition", String.format("attachment; filename=tariff-plan-%s.txt", id));
                    response.getOutputStream().write(plan.get().toString().getBytes(StandardCharsets.UTF_8));
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/tariff-plans":
                Long id = Long.parseLong(req.getParameter("id"));
                tariffPlanService.deleteTariffPlanById(id);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/tariff-plans/add":
                String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                TariffPlan tariffPlan = objectMapper.readValue(requestBody, TariffPlan.class);
                tariffPlanService.addTariffPlan(tariffPlan);
                break;
            case "/tariff-plans/update":
                Long id = Long.parseLong(req.getParameter("id"));
                requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                tariffPlan = objectMapper.readValue(requestBody, TariffPlan.class);
                tariffPlanService.updateTariffPlan(id, tariffPlan);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    private List<TariffPlan> getSortedTariffPlans(HttpServletRequest request, HttpServletResponse response) {
        String orderByStr = request.getParameter("sortBy");
        String directionStr = request.getParameter("direction");

        OrderBy orderBy = OrderBy.safeValueOf(orderByStr);
        SortingDirection direction = SortingDirection.safeValueOf(directionStr);

        FilterConfiguration configuration = new FilterConfiguration.FilterConfigurationBuilder()
                .withOrderBy(orderBy)
                .withSortingDirection(direction)
                .build();

        return tariffPlanService.getTariffPlans(configuration);
    }
}
