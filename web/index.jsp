<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    HttpSession session_active = request.getSession();
    String redirect = "login";
    if (session_active.getAttributeNames().hasMoreElements()) {
        redirect = (String) request.getAttribute("container");
    }

    redirect += ".jsp";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://www.paypalobjects.com/api/checkout.js"></script>
        <title>Certificatif WEB (JSP)</title>

        <!-- Styles -->
        <link href="style/app.css" rel="stylesheet" type="text/css">
        <link href="style/custom.css" rel="stylesheet" type="text/css">
        <link href="style/bootstrap.min.css" rel="stylesheet" type="text/css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-light navbar-laravel">
            <div class="container">
                <%@include file="./navbar.jsp" %>
            </div>
        </nav>

        <main>
            <div class="container">
                Redirect: <%= redirect%>     <br>
                <jsp:include page="<%= redirect%>" flush="true" />
            </div>
        </main>

        <div class="custom-footer">
            <footer class="py-4 bg-dark footer">
                <%@include file="./footer.jsp" %>
            </footer>
        </div>
            
    </body>
</html>
