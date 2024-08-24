package project;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OwnerStartScreen extends Login {

    static Scene ownerStartScene; // DO NOT CHANGE
    static Button backButton, customerButton, bookButton;

    public void createBackButton() {
        backButton = new Button("   Logout   ");
        backButton.setOnAction(e -> {
            input.setText("");
            pass.setText("");

            window.setScene(loginScene);
        });  
        
        // Action on Button Scene change
        GridPane.setConstraints(backButton, 20, 15); // Location of Button    

    }

    public void booksButton(OwnersBooksScreen screen) {
        bookButton = new Button("Books");
        bookButton.setOnAction(e -> window.setScene(screen.ownerBookScene));
        GridPane.setConstraints(bookButton, 20, 2);
    }

    public void customersButton(OwnersCustomersScreen screen) {
        customerButton = new Button("Customers");
        customerButton.setOnAction(e -> window.setScene(screen.ownerCustomerScene));
        GridPane.setConstraints(customerButton, 20, 8); // Location of Button    

    }

    public void start(Stage ownerStartStage) throws Exception {

        OwnersCustomersScreen ownerCustomer = new OwnersCustomersScreen();
        OwnersBooksScreen ownerBook = new OwnersBooksScreen(); 
        ownerCustomer.start(ownerStartStage);
        ownerBook.start(ownerStartStage);

        createBackButton();
        customersButton(ownerCustomer);
        booksButton(ownerBook);

        gridLayout(); //Sets up the Grid Layout
        grid.getChildren().addAll(customerButton, backButton, bookButton); // Add to Grid

        ownerStartScene = new Scene(grid, 500, 250); // Intializing Scene    

    }

}
