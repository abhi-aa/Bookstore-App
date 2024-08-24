/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import static project.Login.grid;
import static project.OwnersBooksScreen.opentable;
/**
 *
 * @author abhig
 */
public class CustomerStartScreen extends OwnersBooksScreen {

    static Scene customerStartScene; //JavaFX
    static Button backButton2, buyButton, redeembuyButton;
    static Label customerIntroduction;
    
    static int intPoints;
    static double sumPrice;
    static final int maxcustomers = 500; //Defines maxcustomers to read in the file
    static String stringUser;
    static String stringStatus;
    static Set state = new Set();
    
    HBox hbox2Customer;//Design Formatters
    VBox vboxCustomer;

    public void createBookTable() { 
        TableColumn<Book, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(300);    
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Book, CheckBox> checkColumn = new TableColumn<>("Select");
        checkColumn.setMinWidth(50);
        checkColumn.setCellValueFactory(new PropertyValueFactory<>("select"));

        //Builds Table
        opentable.getColumns().clear();
        opentable.getColumns().addAll(nameColumn, priceColumn, checkColumn);
    }

    public void introduction(Customer introCustomers) { // Welcome Message
        stringUser = introCustomers.getUser(); //Grabs user information
        intPoints = introCustomers.getPoints();

        //Sets Status based on points
        state.set(introCustomers);
        stringStatus = state.getState().toString();
        
        //Output Label
        customerIntroduction = new Label("Welcome " + stringUser + ". You have " + intPoints + "pts. Your status is " + stringStatus);
    }

    public void createBackButton() {//Creates the back button
        backButton2 = new Button("Logout");
        backButton2.setOnAction(e -> {
            input.setText("");
            pass.setText("");

            window.setScene(loginScene);
        });  // Action on Button Scene change

    }
    
    public void save(Customer c, int p){//Saves the book and customer files if items are bought
        String file = "books.txt";//First, remove the book in the book text file
        try{ 
            FileWriter write = new FileWriter(file);
            BufferedWriter buff = new BufferedWriter(write);
            for (int x = 0; x < opentable.getItems().size(); x++){
                buff.write(opentable.getItems().get(x).getName());
                buff.newLine();
                buff.write(Double.toString(opentable.getItems().get(x).getPrice()));
                buff.newLine();
            }
            buff.close();
        } catch (IOException er){}
        
        file = "customers.txt"; 
        try{
            FileReader reader = new FileReader(file);//Reads for the array of all customers
            BufferedReader buff = new BufferedReader(reader);
            String[] user = new String[maxcustomers];
            String[] pass = new String[maxcustomers];
            int[] points = new int[maxcustomers];
            String line;
            int x = 0;
            int y = 0;
            while ((line = buff.readLine())!= null){
                if (x == 0){
                    user[y] = line;
                    x++;
                }
                else if (x == 1){
                    pass[y] = line;
                    x++;
                }
                else if (x == 2){
                    points[y] = Integer.parseInt(line);
                    y++;
                    x = 0;
                }
            }
            buff.close();
            
            for (x = 0; x < y; x++){ //Update the points for the correct customer
                if ((user[x].equals(c.getUser())) && (pass[x].equals(c.getPass()))){
                    points[x] = p;
                }
            }
            
            FileWriter write = new FileWriter(file); //writes the data back into the text file
            BufferedWriter buff2 = new BufferedWriter(write);
            for (x = 0; x < y; x++){
                buff2.write(user[x]);
                buff2.newLine();
                buff2.write(pass[x]);
                buff2.newLine();
                buff2.write(Integer.toString(points[x]));
                buff2.newLine();
            }
            buff2.close();
        } catch (IOException er){
        
        }
    }
    public void buyButton(Customer introCustomers, CustomerCostScreen costScreen) {
        sumPrice = 0;
        buyButton = new Button("Buy");
        buyButton.setOnAction(e -> {
            ObservableList<Book> dataListRemove = FXCollections.observableArrayList();
            sumPrice = 0;
            for (Book bean : Books) {// Removes book from BookStore once purchased
                if (bean.getSelect().isSelected()) {
                    dataListRemove.add(bean);
                    sumPrice = sumPrice + bean.getPrice(); //Keeps track of price of books purchased
                }
            }
            Books.removeAll(dataListRemove); //Removes all purchased books

            intPoints = intPoints + (int) (Math.floor(sumPrice) * 10); // Points added up
            introCustomers.setPoints(intPoints); //Updates points
            save(introCustomers, intPoints); //Update Customers File
            
            try { //Opens the CustomerCostScreen after passing in some information
                CustomerCostScreen.state.set(introCustomers);
                CustomerCostScreen.Points = intPoints;
                CustomerCostScreen.Bookprice = sumPrice;
                costScreen.start(window);
            } catch (Exception ex) {
                Logger.getLogger(CustomerStartScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            window.setScene(costScreen.customerCostScene);
        });
    }

    public void redeembuyButton(Customer introCustomers, CustomerCostScreen costScreen){
        sumPrice = 0;
        redeembuyButton = new Button("Redeem Points and Buy");
        redeembuyButton.setOnAction(e -> {
            ObservableList<Book> dataListRemove = FXCollections.observableArrayList();
            sumPrice = 0;
            for (Book bean : Books) {// Removes book from BookStore once purchased
                if (bean.getSelect().isSelected()) {
                    dataListRemove.add(bean);
                    sumPrice = sumPrice + bean.getPrice(); //Keeps track of price of books purchased
                }
            }
            Books.removeAll(dataListRemove); //removes all purchased books
            
            //Calculates value of points in money
            int pointsprice = intPoints / 100;
            intPoints = intPoints % 100; 
            if (pointsprice < sumPrice){//If value of points less than total cost, subtract difference and add points
                sumPrice = sumPrice - pointsprice;
                //Calculates points accumulated based on money you have to spend out of pocket
                intPoints = intPoints + (int) (Math.floor(sumPrice) * 10);
            }
            else{ //If value of points greater than total cost, no points are gained. Points value is recalculated
                pointsprice = pointsprice - (int)sumPrice;
                sumPrice = 0;
                //Calculates points based on how much point money you have
                intPoints = intPoints + ((int)pointsprice * 100);
            }
            
            introCustomers.setPoints(intPoints); //Updates points
            save(introCustomers, intPoints);    //Saves data
            
            try {
                //Opens the CustomerCostScreen after passing in some information
                CustomerCostScreen.state.set(introCustomers);
                CustomerCostScreen.Points = intPoints;
                CustomerCostScreen.Bookprice = sumPrice;
                costScreen.start(window);
            } catch (Exception ex) {
                Logger.getLogger(CustomerStartScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            window.setScene(costScreen.customerCostScene);
        });
    }
    public void createBookOptions() {
        hbox2Customer = new HBox();
        vboxCustomer = new VBox();
        hbox2Customer.getChildren().addAll(backButton2, buyButton, redeembuyButton);
        vboxCustomer.getChildren().addAll(customerIntroduction, opentable, hbox2Customer);
    }

    public void start(Stage customerStartStage, Customer introCustomers) throws Exception {
        CustomerCostScreen costScene = new CustomerCostScreen();
        createBackButton();
        buyButton(introCustomers,costScene);
        redeembuyButton(introCustomers,costScene);
        introduction(introCustomers);
        gridLayout();
        createBookTable();
        createBookOptions();
        grid.getChildren().add(vboxCustomer);

        customerStartScene = new Scene(grid, 500, 500);    

    }

}
