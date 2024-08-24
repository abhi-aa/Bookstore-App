/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author abhig
 */
public class CustomerCostScreen extends CustomerStartScreen {
    static Scene customerCostScene; 
    static Button logout;
    static Label TotalCost;
    static Label Points_Status;
    VBox VboxFinal = new VBox();
    
    static double Bookprice; 
    static int Points;
    static String status;
    static Set state = new Set();
    static String stringStatus;
    
    //Sets up total cost, points/status and the logout button
    public void setUp(){
        TotalCost = new Label("Total cost of books is: $" + Bookprice);
        stringStatus = state.getState().toString();
        Points_Status = new Label("Number of points in account is: " + Points + ". Your current status is: " + stringStatus);
        
        createFinalButton();
        VboxFinal.getChildren().addAll(TotalCost, Points_Status, logout);
    }
    
    //Logout button
     public void createFinalButton() {
        VboxFinal = new VBox();
        logout = new Button("Logout");
        logout.setOnAction(e -> {
            input.setText("");
            pass.setText("");

            window.setScene(loginScene);
        });
    }
    
    
    
    public void start(Stage customerCostStage) throws Exception {
        setUp();
        gridLayout(); //Sets up the Grid Layout  

        
       grid.getChildren().add(VboxFinal);
       customerCostScene = new Scene(grid, 500, 500); // Intializing Scene    


    }
    
    
    
}
