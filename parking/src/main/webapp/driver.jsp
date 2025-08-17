<%@ page import="org.example.service.DriverService" %>
<%@ page import="org.example.app.AppContext" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="org.example.model.Driver" %><%--
  Created by IntelliJ IDEA.
  User: kracr
  Date: 8/17/2025
  Time: 4:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Drivers</title>
</head>
<body>
    <h1>Drivers Page</h1>
    <h2>Add a new driver:</h2>

    <%
        DriverService driverService = (DriverService) AppContext.getInstance().getApplicationContext().getBean("driverService");
        String[] idtypes = driverService.getIDTypes();
    %>

    <form method="POST" action="drivers">
        <input type="text" placeholder= "Name" name="driver_name">
        <br>
        <input type="text" placeholder= "Occupation" name="occupation">
        <br>
        <select name="id_type">
            <% for (String idtype : idtypes) { %>
            <option value="<%= idtype %>"><%= idtype %></option>
            <% } %>
        </select>
        <br>
        <input type="text" placeholder= "Identification Number" name="identification_number">
        <br>
        <button>Register Driver</button>
    </form>

    <%
        Collection<Driver> drivers = driverService.getDrivers();

        out.print("<ol>");
        for(Driver driver : drivers){
            out.println("<li>" + driver.getName() + "</li>");
        }
        out.print("</ol>");

    %>
</body>
</html>
