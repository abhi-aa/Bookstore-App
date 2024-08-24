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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

    public class OwnersBooksScreen extends OwnerStartScreen {
        static Scene ownerBookScene; 
        static Button backToOwner;
        static Button add = new Button("Add");
        static Button delete = new Button("Delete");
        static TableView<Book> opentable;
        static TextField nameIn, priceIn;
        static final String file = "books.txt";
        static ObservableList<Book> Books; 

        HBox hbox = new HBox();
        HBox hbox2 = new HBox();
        VBox vbox = new VBox();
        
    public void createBackButton(){//creates backbutton and saves data  
        backToOwner = new Button ("Back");
        backToOwner.setOnAction(e -> { //Will write to the file if you move back
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
            
            window.setScene(ownerStartScene);
            });
        GridPane.setConstraints(backToOwner, 5, 8); // Location of Button    
        
    }
    
    public ObservableList<Book> getBooks(){ //Generates list of books from file into the table
        Books =  FXCollections.observableArrayList();
        try{
            FileReader reader = new FileReader(file);
            BufferedReader buff = new BufferedReader(reader);
            
            String line = null;
            String name = null;
            int count = 0;
            while ((line = buff.readLine())!= null){
                if (count == 1){
                    count = 0;
                    Books.add(new Book(name,Double.parseDouble(line)));
                }
                else{
                    count++;
                }
                name = line;
            }
            
            buff.close();
        } catch (IOException e){}
        
        return Books;
    } 
    
    public void createBookTable(){ //Formats the design of the table
        //Name Column
        TableColumn<Book, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        //Price Column
        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Builds Table
        opentable = new TableView();
        opentable.setItems(getBooks());
        opentable.getColumns().addAll(nameColumn, priceColumn);
    }
    
    public void createBookOptions(){ //Deals with add/delete functions, generates the entire book GUI
        nameIn = new TextField(); //GUIs
        priceIn = new TextField();
        
        nameIn.setPromptText("Name");
        nameIn.setMinWidth(50);
        
        priceIn.setPromptText("Price");
        priceIn.setMinWidth(50);
        
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setSpacing(10);
        
        hbox2.setPadding(new Insets(10,10,10,10));
        hbox2.setSpacing(20);   //UPDATED
        
        add.setOnAction(e -> { //Add function command
            Book newbook = new Book(" ", 0.0);
            String test = priceIn.getText();
            
            newbook.setName(nameIn.getText());
            
            if ((newbook.getName().equals("")) || (test.equals(""))){ //Empty check
                
            }
            else{
                newbook.setPrice(Double.parseDouble(priceIn.getText()));
                opentable.getItems().add(newbook);
            }
            
            nameIn.clear();
            priceIn.clear();
            window.setOnCloseRequest(er -> close()); //Forces exit button to save if things are added
        });
        
        delete.setOnAction(e -> { //Delete function command
            ObservableList<Book> selected, items;
            items = opentable.getItems();
            selected = opentable.getSelectionModel().getSelectedItems();
            
            selected.forEach(items::remove);
            window.setOnCloseRequest(er -> close()); //Forces exit button to save if things are deleted
        });
        
        //Builds the entire interface
        hbox.getChildren().addAll(nameIn, priceIn, add);
        hbox2.getChildren().addAll(delete, backToOwner);
        vbox.getChildren().addAll(opentable, hbox, hbox2);
    }
    
    protected void close() {//Exit button close trigger
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
            } catch (IOException er){
                //do nothing
            }
        window.close();
    }
    
    public void start(Stage ownerBookStage) throws Exception {
        gridLayout(); 
        createBackButton(); 
        createBookTable();
        createBookOptions();
        grid.getChildren().add(vbox);
        ownerBookScene = new Scene(grid, 500, 500);
    }
}
