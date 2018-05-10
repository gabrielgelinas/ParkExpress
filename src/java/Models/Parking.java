/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author gggab
 */
public class Parking {
    private final int id;
    private final int location;
    private int parked_car;

    public Parking(int id, int location, int parked_car) {
        this.id = id;
        this.location = location;
        this.parked_car = parked_car;
    }

    public int getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }
    
    public int getParked_car() {
        return parked_car;
    }

    public void setParked_car(int parked_car) {
        this.parked_car = parked_car;
    }
    
    
    
}
