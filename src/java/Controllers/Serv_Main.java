/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.DB_Gateway;
import Models.User;
import java.io.IOException;
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
 * @author gggab
 */
public class Serv_Main extends HttpServlet {

    String redirect_to;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");

        String redirect = (request.getParameter("redirect") == null) ? "" : request.getParameter("redirect");

        if (redirect.equals("logout")) {
            request.getSession().invalidate();
        }

        if (request.getAttribute("container") == null) {
            request.setAttribute("container", "app");
        }

        RequestDispatcher dispatch = request.getRequestDispatcher(redirect_to);
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

        //Set session to connected user
        HttpSession session = request.getSession();

        session.invalidate();

        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Serv_Main.class.getName()).log(Level.SEVERE, null, ex);
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

        try {

            redirect_to = "index.jsp";

            //Get user, pwd for authentification with db
            String user = request.getParameter("email");
            String pwd = request.getParameter("password");

            //Set session to connected user
            HttpSession session = request.getSession();

            // TableDataGateWay
            DB_Gateway db_gateway = new DB_Gateway();

            if (session.getAttribute("user") == null) {

                User user_auth = db_gateway.ValidateAuth(user, pwd);
                String role = (user_auth == null) ? null : user_auth.getRole();
                if (role != null) {

                    session.setAttribute("user_id", user_auth.getId());
                    session.setAttribute("user", user);
                    session.setAttribute("role", role);

                    request.setAttribute("container", "app");
                }
            }

            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Serv_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
