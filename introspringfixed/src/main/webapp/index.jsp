<%@ page import="org.example.AppContext" %>
<%@ page import="org.example.services.ShopService" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hola Mundo JSP</title>
</head>
<body>
<h1>Hola Mundo desde JSP</h1>
<%-- Esto es un scriptlet de JSP --%>
<%
    ShopService service = (ShopService) AppContext.getInstance().getApplicationContext().getBean("shopService");
    ArrayList<String> list = service.getShopList();

    out.print("<ol>");
    for(String item : list){
        out.println("<li>" + item + "</li>");
    }
    out.print("</ol>");

    // Aquí puedes escribir código Java
    String mensaje = "¡Bienvenido a mi primera página JSP!";
    out.println("<h2>" + mensaje + "</h2>");
%>

<%-- Esto es una expresión de JSP --%>
<p>La fecha y hora actuales son: <%= new java.util.Date() %></p>

<form method="POST" action="shop">
    <input type="text" placeholder="Enter what you want" name="inputSale">
    <button>Register</button>
</form>


</body>
</html>