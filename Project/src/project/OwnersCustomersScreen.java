package project;

//JavaFX imports
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static project.Login.grid;
import static project.OwnersCustomersScreen.backToOwner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OwnersCustomersScreen extends OwnerStartScreen {
    
    static Scene ownerCustomerScene;
    static Button backToOwner;
    static Button add = new Button("Add");
    static Button delete = new Button("Delete");
    static TextField userIn, passIn;
    static TableView<Customer> table;
    static ObservableList<Customer> Customers; 

    static final String file = "customers.txt";
    
    HBox hbox = new HBox(); 
    HBox hbox2 = new HBox();
    VBox vbox = new VBox();
    
    public void createBackButton() {
        backToOwner = new Button ("Back");
        backToOwner.setOnAction(e -> {// Will write to the file if you move back
            try{
                FileWriter write = new FileWriter(file);
                BufferedWriter buff = new BufferedWriter(write);
                for (int x = 0; x < table.getItems().size(); x++){
                    buff.write(table.getItems().get(x).getUser());
                    buff.newLine();
                    buff.write(table.getItems().get(x).getPass());
                    buff.newLine();
                    buff.write(Integer.toString(table.getItems().get(x).getPoints()));
                    buff.newLine();
                }
                buff.close();
            } catch (IOException er){}
            window.setScene(ownerStartScene);
            });
        GridPane.setConstraints(backToOwner, 5, 8); // Location of Button
    }
    
    public ObservableList<Customer> getCustomers(){ //Generates list of customers from file to be inserted into the table
        Customers = FXCollections.observableArrayList();
        try{
            FileReader reader = new FileReader(file);
            BufferedReader buff = new BufferedReader(reader);
            
            String line = null;
            String user = null;
            String pass = null;
            int count = 0;
            while ((line = buff.readLine())!= null){
                if (count == 2){
                    count = 0;
                    Customers.add(new Customer(user,pass,Integer.parseInt(line)));
                }
                else if (count == 1){
                    pass = line;
                    count++;
                }
                else{
                    count++;
                    user = line;
                }
            }
            
            buff.close();
        } catch (IOException e){}
        
        return Customers;
    }
    
    public void createCustomerTable(){ //Formats the design of the table
        //Username Column
        TableColumn<Customer, String> userColumn = new TableColumn<>("Username");
        userColumn.setMinWidth(170);
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        
        //Password Column
        TableColumn<Customer, String> passColumn = new TableColumn<>("Password");
        passColumn.setMinWidth(170);
        passColumn.setCellValueFactory(new PropertyValueFactory<>("pass"));
        
        //Points Column
        TableColumn<Customer, Integer> pointColumn = new TableColumn<>("Points");
        pointColumn.setMinWidth(50);
        pointColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        
        //Builds Table
        table = new TableView(); 
        table.setItems(getCustomers()); 
        table.getColumns().addAll(userColumn, passColumn, pointColumn);
    }
    
    public void createCustomerOptions(){// add/delete functions
        userIn = new TextField();
        passIn = new TextField();
        
        userIn.setPromptText("Username");
        userIn.setMinWidth(50);
        
        passIn.setPromptText("Password");
        passIn.setMinWidth(50);
        
        hbox.setPadding(new Insets(20,40,20,20));
        hbox.setSpacing(10);    
        
        hbox2.setPadding(new Insets(20,40,20,20));
        hbox2.setSpacing(20);    
        
        add.setOnAction(e -> {//Add function command
            Customer newCustomer = new Customer(" ", " ", 0);
            newCustomer.setUser(userIn.getText());
            newCustomer.setPass(passIn.getText());
            newCustomer.setPoints(0);
            
            if ((userIn.getText().equals("")) || (passIn.getText().equals(""))){ //checks if empty
                
            }
            else{
                table.getItems().add(newCustomer);
            }
            
            userIn.clear();
            passIn.clear();
            window.setOnCloseRequest(er -> close()); //Sets exit button to save if things are added
        });
        
        delete.setOnAction(e -> { //Delete function command
            ObservableList<Customer> selected, items;
            items = table.getItems(); //Takes all items from table
            selected = table.getSelectionModel().getSelectedItems(); //Takes the one which are selected
            
            selected.forEach(items::remove); //Removes all selected items
            window.setOnCloseRequest(er -> close()); //Sets exit button to save if things are deleted
        });
        
        //Builds the entire interface
        hbox.getChildren().addAll(userIn, passIn, add);
        hbox2.getChildren().addAll(delete, backToOwner);
        vbox.getChildren().addAll(table, hbox, hbox2);
    }
    
    protected void close() { //Saves if exit button is pressed
        try{
            FileWriter write = new FileWriter(file);
            BufferedWriter buff = new BufferedWriter(write);
            for (int x = 0; x < table.getItems().size(); x++){
                buff.write(table.getItems().get(x).getUser());
                buff.newLine();
                buff.write(table.getItems().get(x).getPass());
                buff.newLine();
                buff.write(Integer.toString(table.getItems().get(x).getPoints()));
                buff.newLine();
            }
            buff.close();
            } catch (IOException er){}  
        window.close();
    }
    public void start(Stage ownerCustomerStage) throws Exception {
        gridLayout();  
        createBackButton();
        createCustomerTable();
        createCustomerOptions();
        grid.getChildren().add(vbox);
        ownerCustomerScene = new Scene(grid, 550, 600);
 
    }

}
