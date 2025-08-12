package com.example.servlets;

import com.example.AppContext;
import com.example.services.ShopService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/shop")
public class ShopListServlet extends HttpServlet{

    //To get the instance that was made by the IoC container
    private ShopService shopService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.getWriter().println("<h1> This is the shop list servlet </h1>");
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Get the info of the form from the request
        String sale = req.getParameter("saleInput");
        shopService.addItem(sale);
        System.out.println(shopService.getShopList().size());
        //Basically refreshing the page forcibly so it gets updated with the changes
        resp.sendRedirect("./");
	}

    @Override
    public void init(ServletConfig config) throws ServletException {
        //Has to be downcasted since the applicationContext.getBean() returns an object
        shopService = (ShopService) AppContext.getInstance().getApplicationContext().getBean("shopService");
    }
}