/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project;

public class Customer {
    String username, password;
    int points;
    
    public Customer(String u, String p, int pts){
        username = u;
        password = p;
        this.points = pts;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String u) {
        username = u;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String p) {
        password = p;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int pts) {
        this.points = pts;
    }
    
    
}
