package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.app.AppContext;
import org.example.service.PassengerService;

import java.io.IOException;

@WebServlet("/passengers")
public class PassengerServlet extends HttpServlet {
    private PassengerService passengerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        passengerService = AppContext.getInstance().getApplicationContext().getBean("passengerService", PassengerService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flightId = req.getParameter("flightId");
        String passengerId = req.getParameter("passengerId");
        String name = req.getParameter("name");
        String passportId = req.getParameter("passportId");


        passengerService.addPassenger(flightId, passengerId, name, passportId);
        resp.sendRedirect("./");
    }
}
