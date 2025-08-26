package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.app.AppContext;
import org.example.service.FlightService;

import java.io.IOException;

@WebServlet("/flights")
public class FlightServlet extends HttpServlet {

    private FlightService flightService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        flightService = AppContext.getInstance().getApplicationContext().getBean("flightService", FlightService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String origin = req.getParameter("origin");
        String destination = req.getParameter("destination");
        String date = req.getParameter("date");

        flightService.addFlight(id, origin, destination, date);
        resp.sendRedirect("./");
    }
}
