<%-- 
    Document   : navbar
    Created on : 26-Apr-2018, 12:22:15 PM
    Author     : gggab
--%>
<form action="Serv_Main" method="post">
    <input type="hidden" name="login">
    <input type="hidden" name="redirect" value="index">
    <input id="image" type="image" alt="Login" class="navbar-brand"
           src="./images/home.png" >
</form>
<!--<a class="navbar-brand" href="./index.jsp">
    <img src="./images/home.png" height="40px" width="40px">
</a>-->
<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
</button>

<div class="collapse navbar-collapse" id="navbarSupportedContent">
    <!-- Left Side Of Navbar -->
    <form class="navbar-nav  mr-auto" action="Serv_Main" method="post">
        <ul class="navbar-nav mr-auto">
        </ul>
    </form>


    <ul class="navbar-nav">
        <li><a class="nav-link pr-3" href="#">
                Current Role : 
                <span class="font-weight-bold">
                    <% String role = (String) session.getAttribute("role");%>
                    <%= role%></span>
            </a></li>
        <li>
            <a class="nav-link" href="#">
            </a>
        </li>
    </ul>


    <div class="navbar-nav float-right">
        <form action="Serv_Main" method="post">
            <input class="nav-link" type="submit" name="login" value="Login" />
            <input type="hidden" name="redirect" value="login">
        </form>

        <form action="Serv_Main" method="post">
            <input class="nav-link" type="submit" name="logout" value="Disconnect" />
            <input type="hidden" name="redirect" value="logout">
        </form>
    </div>
</div>
