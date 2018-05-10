/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Car;
import Models.DB_Gateway;
import Models.Parking;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zombietux
 */
public class Serv_Parking extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
        dispatch.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            DB_Gateway db_gateway;
            db_gateway = new DB_Gateway();
            ArrayList<Parking> parking_list = db_gateway.query_getAllParkingSpots();

            int[][] parking_grid = new int[10][10];
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    parking_grid[row][col] = 0;
                }
            }

            int user_id = (int) session.getAttribute("user_id");
            ArrayList<Car> user_cars = db_gateway.query_getUserCars(user_id);

            parking_list.forEach((parking_spot) -> {
                int id = parking_spot.getLocation();
                int row = Integer.parseInt(Integer.toString(id).substring(0, 1));
                int col = Integer.parseInt(String.valueOf(id).substring(String.valueOf(id).length() - 1));
                parking_grid[row][col] = (parking_spot.getParked_car() == 0) ? -1 : parking_spot.getParked_car();
            });

            request.setAttribute("parking_grid", parking_grid);
            request.setAttribute("parking_list", parking_list);
            request.setAttribute("user_cars", user_cars);

            request.setAttribute("container", "app");

            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Serv_Parking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int col = Integer.parseInt(request.getParameter("col"));
        int row = Integer.parseInt(request.getParameter("row"));

        request.setAttribute("row", row);
        request.setAttribute("col", col);

        request.setAttribute("container", "payment");
        
        processRequest(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
