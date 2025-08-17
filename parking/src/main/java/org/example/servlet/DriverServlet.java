package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.app.AppContext;
import org.example.service.DriverService;

import java.io.IOException;

@WebServlet("/drivers")
public class DriverServlet extends HttpServlet {
    private DriverService driverService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String driverName = req.getParameter("driver_name");
        String occupation = req.getParameter("occupation");
        String idtype = req.getParameter("idtype");
        String identificationNumber = req.getParameter("identification_number");
        
        driverService.addDriver(driverName, occupation, idtype, identificationNumber);
        System.out.println(driverService.getDrivers().size());
        resp.sendRedirect("./driver.jsp");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        driverService = AppContext.getInstance().getApplicationContext().getBean("driverService", DriverService.class);
    }
}
