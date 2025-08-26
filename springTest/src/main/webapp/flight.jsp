<%@ page import="org.example.model.Flight" %>
<%@ page import="org.example.app.AppContext" %>
<%@ page import="org.example.service.FlightService" %><%--
  Created by IntelliJ IDEA.
  User: kracr
  Date: 8/21/2025
  Time: 2:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Flight Page</title>
</head>
<body>
    <h1>Flight Page</h1>

    <h2>Current Flights:</h2>
    <ul>
        <%
            out.println("<ol>");
            for(Flight flight: AppContext.
                    getInstance().
                    getApplicationContext().
                    getBean("flightService", FlightService.class).getFlights()){
                out.println("<li>"+flight.getId()+"</li>");
            }
            out.println("</ol>");
        %>
    </ul>

    <h2>Add a new flight!</h2>
    <form method="POST" action="flights">
        <input name = "id" placeholder="Enter the flight ID"/>
        <br>
        <input name = "origin" placeholder="Enter the flight's origin"/>
        <br>
        <input name = "destination" placeholder="Enter the flight's destination"/>
        <br>
        <input name = "date" placeholder="Enter the date of the flight"/>
        <button>Submit Flight</button>
    </form>
</body>
</html>
