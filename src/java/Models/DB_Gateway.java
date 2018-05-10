/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gggab
 */
public class DB_Gateway {
    
    private Connection c;
    private ResultSet r;
    private PreparedStatement query_getAllParkingSpots, query_ValidateAuth, query_getUserCars, query_addReservation, query_removeReservation;
    private String url, db_user, db_pwd;

    ArrayList<Parking> parkinglist = new ArrayList<Parking>();

    public DB_Gateway() throws ClassNotFoundException {
        this.url = "jdbc:mysql://localhost:3306/parkexpress";
        this.db_user = "root";
        this.db_pwd = "";

        Class.forName("com.mysql.jdbc.Driver");

        try {
            c = DriverManager.getConnection(this.url, this.db_user, this.db_pwd);

            query_ValidateAuth = c.prepareStatement(
                    "SELECT U.id, U.name, U.role "
                    + "FROM user U "
                    + "WHERE U.username=? and "
                    + "U.pwd=?;");

            query_getAllParkingSpots = c.prepareStatement(
                    "SELECT * FROM parking_space;");

            query_getUserCars = c.prepareStatement(
                    "SELECT id, model, registration FROM car "
                            + "WHERE owner=?;");

//            query_addReservation = c.prepareStatement(
//                    "INSERT INTO parkingcar ("
//                    + "parking_id, "
//                    + "user_id, "
//                    + "day, "
//                    + "hour)"
//                    + "VALUES (1,?,?,?);");
//
//            query_removeReservation = c.prepareStatement(
//                    "DELETE FROM parkingcar "
//                    + "WHERE user_id=? "
//                    + "AND day=? "
//                    + "AND hour=?;");

        } catch (SQLException ex) {
            Logger.getLogger(DB_Gateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Car> query_getUserCars(int user) {
        ArrayList<Car> user_cars = new ArrayList<>();

        try {
            this.query_getUserCars.setString(1, String.valueOf(user));
            r = this.query_getUserCars.executeQuery();

            clearQueryParams(this.query_getUserCars);
            while (r.next()) {
                user_cars.add(new Car(r.getInt("id"), r.getString("model"), r.getString("registration")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB_Gateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user_cars;
    }

    public ArrayList<Parking> query_getAllParkingSpots() {
        parkinglist = new ArrayList<>();

        try {
            r = this.query_getAllParkingSpots.executeQuery();

            clearQueryParams(this.query_getAllParkingSpots);
            while (r.next()) {
                parkinglist.add(new Parking(r.getInt("id"), r.getInt("location"), r.getInt("parked_car")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB_Gateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parkinglist;
    }

    public User ValidateAuth(String user, String pwd) {
        try {
            this.query_ValidateAuth.setString(1, user);
            this.query_ValidateAuth.setString(2, pwd);
            r = this.query_ValidateAuth.executeQuery();

            clearQueryParams(this.query_ValidateAuth);
            while (r.next()) {
                return new User(r.getInt("id"), r.getString("name"), user, r.getString("role"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB_Gateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void addReservation(int user_id, int day, int hour) {
        try {
            this.query_addReservation.setInt(1, user_id);
            this.query_addReservation.setInt(2, day);
            this.query_addReservation.setInt(3, hour);
            String query = this.query_addReservation.toString();
            this.query_addReservation.execute();
            this.query_addReservation.clearParameters();
            query = this.query_addReservation.toString();
            
        } catch (SQLException ex) {
            Logger.getLogger(DB_Gateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeReservation(int user_id, int day, int hour) {
        try {
            this.query_removeReservation.setInt(1, user_id);
            this.query_removeReservation.setInt(2, day);
            this.query_removeReservation.setInt(3, hour);
            this.query_removeReservation.execute();
            clearQueryParams(this.query_removeReservation);
        } catch (SQLException ex) {
            Logger.getLogger(DB_Gateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearQueryParams(PreparedStatement query) throws SQLException {
        query.clearParameters();
    }

}
    