package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import static project.OwnersCustomersScreen.Customers;


public class Login extends Application {

    static Scene loginScene; //JavaFX
    static Button loginButton;
    static Stage window;
    static Label name, password, welcome;
    static TextField input;
    static PasswordField pass;
    static GridPane grid;
    static final String file = "customers.txt";

    public void gridLayout() {
        grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20)); //padding for each edge
        grid.setVgap(8); 
        grid.setHgap(10);  
        grid.setStyle("-fx-background-color: #A0A0A0;");

    }
    
    //Username textfield & label
    public void userName() {
        name = new Label("Username:");
        GridPane.setConstraints(name, 0, 1);
        input = new TextField();
        GridPane.setConstraints(input, 1, 1); 
    }
    
    //Password textfield & label
    public void passWord() {
        password = new Label("Password:");
        GridPane.setConstraints(password, 0, 2);  
        pass = new PasswordField();
        GridPane.setConstraints(pass, 1, 2); 
    }
    
    public void createLoginButton(OwnerStartScreen screen, CustomerStartScreen cscreen){
        loginButton = new Button("Login"); 
        GridPane.setConstraints(loginButton, 1, 3);   
        // Action Button with Validity
        loginButton.setOnAction(e -> {
            if (input.getText().equals("admin") && pass.getText().equals("admin")) {
                try { 
                    screen.start(window);
                } catch (Exception ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                window.setScene(screen.ownerStartScene);
            } else {
                //Checks for if a customer logged in
                try {
                    FileReader reader = new FileReader(file);
                    BufferedReader buff = new BufferedReader(reader);

                    String line = null;
                    String user = null;
                    String password = null;
                    int count = 0;
                    while ((line = buff.readLine()) != null) {
                        switch (count) {
                            case 2:
                                if (input.getText().equals(user) && pass.getText().equals(password)) {
                                    try {
                                        screen.start(window);
                                    } catch (Exception ex) {
                                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    for (int i = 0; i < Customers.size(); i++) { // For loop finds the Customer Object in ArrayList
                                        if (Customers.get(i).getUser().equals(input.getText()) && Customers.get(i).getPass().equals(pass.getText())) {
                                            
                                            try {
                                                cscreen.start(window,Customers.get(i)); // Customer Object to access setters and getters
                                            } catch (Exception ex) {
                                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            
                                            break;
                                        }
                                    }
                                    window.setScene(cscreen.customerStartScene);
                                    
                                }   count = 0;
                                break;
                            case 1:
                                password = line;
                                count++;
                                break;
                            default:
                                count++;
                                user = line;
                                break;
                        }
                    }

                    buff.close();
                } catch (IOException er) {}
                input.setText("");
                pass.setText("");
            }
        });

    }

    public void start(Stage loginStage) throws Exception {
        window = loginStage;
        OwnerStartScreen ownerStart = new OwnerStartScreen();
        CustomerStartScreen customerStart = new CustomerStartScreen();
        loginStage.setTitle("BookStore App");

        gridLayout();

        welcome = new Label("Welcome to the BookStore App");
        GridPane.setConstraints(welcome, 0, 0);

        userName();
        passWord();
        createLoginButton(ownerStart, customerStart);

        grid.getChildren().addAll(welcome, name, input, password, pass, loginButton); 
        loginScene = new Scene(grid, 500, 250, Color.GRAY);    

        window.setScene(loginScene);      
        window.show();
    }

}
