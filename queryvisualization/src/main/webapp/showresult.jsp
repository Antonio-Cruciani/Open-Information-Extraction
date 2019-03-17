<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ page import ="java.io.*"%>;
<%@ page import ="java.io.File.*"%>;
<%@ page import="java.sql.*"%>
<%@ page import="org.eclipse.rdf4j.query.BindingSet"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.HashMap"%>


<html> 
<style>
body
{
  background-image:url('./zoomed_out1.png');
  background-attachment:fixed;
  
}</style> 
<head> 
<%@ page isELIgnored="false" %>
 <title>Applicazione End-User</title>  
</head>  
<body>  
<b><%= request.getParameter("sbj") %></b>

 <%-- JSTL foreach tag example to loop a HashMap in JSP --%>
        <table>
            <c:forEach var="entry" items="${a}">
                <tr><td><c:out value="${entry.value}"/></td> </tr>
            </c:forEach>
        </table>

</body>
   
        