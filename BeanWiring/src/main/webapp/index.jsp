<%@ page import="org.example.app.AppContext" %>
<%@ page import="org.example.service.StudentService" %>
<%@ page import="org.example.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: kracr
  Date: 8/14/2025
  Time: 2:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Wiring Project</h2>
    <%
        StudentService studentService = AppContext.getInstance().
                getApplicationContext().getBean("studentService", StudentService.class);

        for(Student student : studentService.getStudents()){
            out.println("<p>"+student.getName()+"</p>");
        }
    %>

    <form method="POST" action="students">
        <input type="text" placeholder="Enter your id" name="studentId"/>
        <input type="text" placeholder="Enter your name" name="studentName"/>
        <input type="text" placeholder="Enter your course" name="studentCourse"/>
        <button>Submit Student</button>
    </form>
</body>
</html>
