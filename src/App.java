import ExploreCity.Objednavky.Objednavka;
import ExploreCity.Osoby.Zakaznik;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class App extends Application{
    static int ID = velkostZoznamu();
    
    @Override
    public void start(Stage primaryStage) {
        Login();
    }

    public static int velkostZoznamu() {
        File file = new File("C:\\Users\\Kozel\\Desktop\\Fiit\\2semester part 2\\OOP\\Projekt\\src\\users.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                ID++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ID;
    }


    public static void Login() {
        // create login form
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        AnchorPane pane = new AnchorPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setPrefSize(300, 200);
        pane.setStyle("-fx-background-color: #FFFFFF;");
        Scene loginScene = new Scene(pane);
        loginStage.setScene(loginScene);
        loginStage.show();
    
        // create login form elements
        Text loginTitle = new Text("Login");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label loginLabel = new Label("Username:");
        TextField loginField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        HBox loginButtons = new HBox(10);
        loginButtons.getChildren().addAll(loginButton, registerButton);
        loginButtons.setAlignment(Pos.CENTER);
    
        // register button action
        registerButton.setOnAction(e -> {
            loginStage.close();
            Register();
        });
    
        // add login form elements to login form
        AnchorPane.setTopAnchor(loginTitle, 10.0);
        AnchorPane.setLeftAnchor(loginTitle, 10.0);
        AnchorPane.setTopAnchor(loginLabel, 50.0);
        AnchorPane.setLeftAnchor(loginLabel, 10.0);
        AnchorPane.setTopAnchor(loginField, 50.0);
        AnchorPane.setLeftAnchor(loginField, 80.0);
        AnchorPane.setTopAnchor(passwordLabel, 80.0);
        AnchorPane.setLeftAnchor(passwordLabel, 10.0);
        AnchorPane.setTopAnchor(passwordField, 80.0);
        AnchorPane.setLeftAnchor(passwordField, 80.0);
        AnchorPane.setTopAnchor(loginButtons, 120.0);
        AnchorPane.setLeftAnchor(loginButtons, 80.0);
        pane.getChildren().addAll(loginTitle, loginLabel, loginField, passwordLabel, passwordField, loginButtons);
    
        // login button action
        loginButton.setOnAction(e -> {
            String username = loginField.getText();
            String password = passwordField.getText();
            boolean success = false;
    
            // read usernames and passwords from file and compare with input
            try (Scanner scanner = new Scanner(new File("C:\\Users\\Kozel\\Desktop\\Fiit\\2semester part 2\\OOP\\Projekt\\src\\users.txt"))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(" ");
                    String fileUsername = parts[2];
                    String filePassword = parts[3];
                    
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        success = true;
                        break;
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
    
            if (success) {
                loginStage.close();
                //ziskaj ID
                try (Scanner scanner = new Scanner(new File("C:\\Users\\Kozel\\Desktop\\Fiit\\2semester part 2\\OOP\\Projekt\\src\\users.txt"))) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split(" ");
                        String fileUsername = parts[2];
                        String filePassword = parts[3];
                        int fileID = Integer.parseInt(parts[1]);
                        
                        if (fileUsername.equals(username) && filePassword.equals(password)) {
                            ID = fileID;
                            break;
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                ExploreCity(username, password, ID);
            } else {
                Alert alert = new Alert(AlertType.ERROR, "Invalid username or password.");
                alert.showAndWait();
            }
        });
    }
    

    public static void ExploreCity(String username, String password, int ID) { 
        Zakaznik zakaznik = new Zakaznik(username, password, ID);   
        Label menoLabel = new Label("Meno: " + zakaznik.getMeno());
        Label idLabel = new Label("ID zakaznika: " + zakaznik.getID());
        
        Label ordersLabel = new Label("Objednávky:");
        // create text area for showing orders
        TextArea ordersArea = new TextArea();
        ordersArea.setEditable(false);
        ordersArea.setPrefSize(300, 200);

        ComboBox<String> cityComboBox = new ComboBox<>();
        cityComboBox.getItems().addAll("Stare Mesto", "Nove Mesto", "Petrzalka");
        cityComboBox.setValue(" ");
        CheckBox kostolBox = new CheckBox("Kostol");
        CheckBox restauracieBox = new CheckBox("Restaurácie");
        CheckBox kinoBox = new CheckBox("Kino");
        CheckBox parkBox = new CheckBox("Park");
        CheckBox muzeumBox = new CheckBox("Múzeum");
        CheckBox hradBox = new CheckBox("Hrad");
        TextField ineField = new TextField();
        ineField.setPromptText("Niečo iné");
    
        // create button for submitting selection
        Button submitButton = new Button("Vybrať");
        Button showOrdersButton = new Button("Zobraziť objednávky");
        Button logoutButton = new Button("Odhlásiť");
        Button odstranUcet = new Button("Odstrániť účet");
    
        // add checkboxes and button to layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(menoLabel, idLabel, cityComboBox ,kostolBox, restauracieBox, 
                                    kinoBox, parkBox, muzeumBox, hradBox, ineField, 
                                    submitButton, logoutButton, odstranUcet);
    
        // create scene and show window
        Scene scene = new Scene(layout, 500, 500);
        Stage window = new Stage();
        window.setTitle("Explore City");
        window.setScene(scene);
        window.show();
    
        // submit button action
        submitButton.setOnAction(e -> {
            // get selected options from dropdown and checkboxes
            String selectedCity = cityComboBox.getValue();
            boolean kostolSelected = kostolBox.isSelected();
            boolean restauracieSelected = restauracieBox.isSelected();
            boolean kinoSelected = kinoBox.isSelected();
            boolean parkSelected = parkBox.isSelected();
            boolean muzeumSelected = muzeumBox.isSelected();
            boolean hradSelected = hradBox.isSelected();
            String ineOstatne = ineField.getText();

            Objednavka objednavka = new Objednavka(selectedCity, kostolSelected, restauracieSelected, kinoSelected, parkSelected, muzeumSelected, hradSelected, ineOstatne);
            zakaznik.pridajObjednavku(objednavka);
            //zapise objednavku do suboru objednavky.txt
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Kozel\\Desktop\\Fiit\\2semester part 2\\OOP\\Projekt\\src\\objednavky.txt", true));
                writer.write(zakaznik.getID() + " " + objednavka.getMesto() + " " + objednavka.getKostol() + " " + objednavka.getRestauracie() + " " + 
                            objednavka.getKino() + " " + objednavka.getPark() + " " + objednavka.getMuzeum() + " "
                             + objednavka.getHrad() + " " + objednavka.getNiecoOstatne());
                writer.newLine();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        logoutButton.setOnAction(e -> {
            window.close();
            Login();
        });
    
        odstranUcet.setOnAction(e -> {
            zakaznik.odstranUcet();
            window.close();
            Login();
        });
        
    showOrdersButton.setOnAction(e -> {
         // clear previous orders
        ordersArea.clear();

        // read orders from file and filter by user ID
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Kozel\\Desktop\\Fiit\\2semester part 2\\OOP\\Projekt\\src\\objednavky.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[0]);
                if (id == ID) {
                    String order = parts[1];
                    for (int i = 2; i < parts.length; i++) {
                        if (parts[i].equals("true")) {
                            order += ", " + parts[i - 1];
                        }
                    }
                    if (!parts[parts.length - 1].equals("false")) {
                        order += ", " + parts[parts.length - 1];
                    }
                    ordersArea.appendText(order + "\n");
                }
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // show orders in new window  
            Stage ordersWindow = new Stage();
            ordersWindow.setTitle("Moje objednávky");
            ordersWindow.setScene(new Scene(new VBox(ordersLabel, ordersArea), 400, 300));
            ordersWindow.show();
    });

        // add show orders button to layout
        layout.getChildren().add(showOrdersButton);
    }
    
    

    public static void Register() {
        // create the register form
        Stage registerStage = new Stage();
        registerStage.setTitle("Register");
        AnchorPane pane = new AnchorPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setPrefSize(300, 200);
        pane.setStyle("-fx-background-color: #FFFFFF;");
        Scene registerScene = new Scene(pane);
        registerStage.setScene(registerScene);
        registerStage.show();
    
        // create the register form elements
        Text registerTitle = new Text("Register");
        registerTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label loginLabel = new Label("Username:");
        TextField loginField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label passwordLabel2 = new Label("Confirm password:");
        PasswordField passwordField2 = new PasswordField();
        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");
        HBox registerButtons = new HBox(10);
        registerButtons.getChildren().addAll(registerButton, loginButton);
        registerButtons.setAlignment(Pos.CENTER);
    
        // set up the register button action
        registerButton.setOnAction(e -> {
            String username = loginField.getText();
            String password = passwordField.getText();
            String confirmPassword = passwordField2.getText();
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR, "Please fill out all fields.");
                alert.showAndWait();
            } else if (!password.equals(confirmPassword)) {
                Alert alert = new Alert(AlertType.ERROR, "Passwords do not match.");
                alert.showAndWait();
            } else {
                try {
                    // write the user data to a file
                    ID = ID + 1;
                    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Kozel\\Desktop\\Fiit\\2semester part 2\\OOP\\Projekt\\src\\users.txt", true));
                    writer.write("Zakaznik " + ID + " " + username + " " + password);
                    writer.newLine();
                    writer.close();
                    Alert alert = new Alert(AlertType.INFORMATION, "Registration successful.");
                    alert.showAndWait();
                    registerStage.close();
                    Login();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(AlertType.ERROR, "An error occurred.");
                    alert.showAndWait();
                }
            }
        });
    
        // set up the login button action
        loginButton.setOnAction(e -> {
            registerStage.close();
            Login();
        });

        //add register form elements to register form
        AnchorPane.setTopAnchor(registerTitle, 10.0);
        AnchorPane.setLeftAnchor(registerTitle, 10.0);
        AnchorPane.setTopAnchor(loginLabel, 50.0);
        AnchorPane.setLeftAnchor(loginLabel, 10.0);
        AnchorPane.setTopAnchor(loginField, 50.0);
        AnchorPane.setLeftAnchor(loginField, 80.0);
        AnchorPane.setTopAnchor(passwordLabel, 80.0);
        AnchorPane.setLeftAnchor(passwordLabel, 10.0);
        AnchorPane.setTopAnchor(passwordField, 80.0);
        AnchorPane.setLeftAnchor(passwordField, 80.0);
        AnchorPane.setTopAnchor(passwordLabel2, 110.0);
        AnchorPane.setLeftAnchor(passwordLabel2, 10.0);
        AnchorPane.setTopAnchor(passwordField2, 110.0);
        AnchorPane.setLeftAnchor(passwordField2, 80.0);
        AnchorPane.setTopAnchor(registerButtons, 140.0);
        AnchorPane.setLeftAnchor(registerButtons, 80.0);
        pane.getChildren().addAll(registerTitle, loginLabel, loginField, passwordLabel, passwordField, passwordLabel2, passwordField2, registerButtons);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

