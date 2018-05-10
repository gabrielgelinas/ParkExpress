<%-- 
    Document   : app
    Created on : 5-May-2018, 4:31:10 PM
    Author     : gggab
--%>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://www.paypalobjects.com/api/checkout.js"></script>
</head>
<%@page import="Models.Car"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Models.Parking"%>
<form id="update_grid" action="Serv_Parking" method="get">
    <input type="submit" name="Update_Grid" value="Update Grid" />
</form>
<%
    ArrayList<Car> user_cars = (ArrayList<Car>) request.getAttribute("user_cars");
    int[][] parking_grid = (int[][]) request.getAttribute("parking_grid");
    String popup = "";
    if (request.getAttribute("popup") != null) {
        popup = (String) request.getAttribute("msg");
    }

%>
<div id="paypal-button"></div>

<div class="bg-dark2">
    <table class="table text-center p-2 bg-dark" cellspacing="0">
        <tbody>
            <%for (int row = 1; row < 10; row++) {%>
            <tr>
                <%for (int col = 0; col < 10; col++) {
                        int parked_car = parking_grid[row][col];
                %>
                <td class="col-md-2 h5">
                    <a href="#aboutModal" data-toggle="modal" data-target="#myModal<%=row%><%=col%>">
                        <%  if (parked_car > 0) {%>
                        <%
                            for (Car car : user_cars) {
                                int car_id = car.getId();
                                if (car_id == parked_car) {
                                    String model = car.getModel();
                                    String registration = car.getRegistration();%>
                        <div class="bg-info p-1 text-white font-weight-bold">
                            My Car
                            <br>
                            <span class="small text-white font-weight-bold"><%=model%></span>
                            <br>
                            <span class="small text-white font-weight-bold"><%=registration%></span>
                            <br>
                            <input type="submit" name="submit" value="Cancel" class="small">
                        </div>
                        <%}
                            }%>
                        <%} else if (parking_grid[row][col] == -1) {%>
                        <div class="bg-success text-white font-weight-bold p-1">
                            <%=row%>0<%=col%>
                            <br>
                            <br>
                            Available
                            <br>
                            <br>
                            <input type="submit" name="submit" value="Reserve" class="small">
                        </div> 
                        <%}%>
                    </a>
                </td>
        <div class="modal fade" id="myModal<%=row%><%=col%>" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <div style="text-align: center;">
                            <img src="https://protiumdesign.com/wp-content/uploads/2017/01/Golden-Dollar.jpg" class="card-img"></a>
                            <span class="media-heading h2">
                                Parking
                            </span>
                        </div>
                        <hr>
                        <p class="text-center h2 font-weight-bold">15.00 $</p>
                    </div>
                    <div class="modal-footer m-auto">
                        <div class="text-center">
                            Pick Parking

                            <form action="Serv_Parking" method="post">
                                <input type="hidden" name="row" value="<%=row%>">
                                <input type="hidden" name="col" value="<%=col%>">
                                <input type="submit" name="payment" value="Pay Parking">
                            </form>
                            <hr>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%}%>
        </tr>
        <%}%>
        </tbody>
    </table>
</div>

<script>
    
    
    <%= popup%>
</script>