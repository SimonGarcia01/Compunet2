<%@ page import="org.example.model.Flight" %>
<%@ page import="org.example.app.AppContext" %>
<%@ page import="org.example.service.FlightService" %>
<%@ page import="org.example.service.PassengerService" %>
<%@ page import="org.example.model.Passenger" %><%--
  Created by IntelliJ IDEA.
  User: kracr
  Date: 8/21/2025
  Time: 2:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Passenger Page</title>
</head>
<body>
    <h1>Passenger Page</h1>
    <%
        FlightService flightService = AppContext.
                getInstance().
                getApplicationContext().
                getBean("flightService", FlightService.class);
        PassengerService passengerService = AppContext.
                getInstance().
                getApplicationContext().
                getBean("passengerService", PassengerService.class);
    %>

    <h2>All passengers and Flights registered:</h2>
    <%
        for(Flight flight: passengerService.getFlights()){
            out.println("<h3>"+flight.getId()+"</h3>");
            out.println("<br>");
            out.println("<ol>");
            for(Passenger passenger : passengerService.getPassengers()){
                if(passenger.getFlightId().equals(flight.getId())){
                    out.println("<li>"+passenger.getName()+"</li>");
                }
            }
            out.println("</ol>");
        }
    %>
    <h2>Register a new passenger</h2>
    <form method="POST" action="passengers">
        <input name="passengerId" placeHolder="Enter your ID"/>
        <br>
        <input name="name" placeHolder="Enter your name"/>
        <br>
        <input name="passportId" placeHolder="Enter your passport ID"/>
        <select name="flightId">
            <%
                for (Flight flight : passengerService.getFlights()) {
                    out.println("<option value=\"" + flight.getId() + "\">" + flight.getId() + "</option>");
                }
            %>
        </select>

        <button>Submit new Passenger</button>
    </form>
</body>
</html>
