import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {

    @FXML private TextField loginUsernameField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Label loginMessage;

    @FXML private TextField registerUsernameField;
    @FXML private PasswordField registerPasswordField;
    @FXML private Label registerMessage;

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();

        if (FileManager.checkCredentials(username, password)) {
            switchScene("/game.fxml");
        } else {
            loginMessage.setText("Invalid username or password.");
        }
    }

    @FXML
    public void handleRegister(ActionEvent event) {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            registerMessage.setText("Please fill in both fields.");
        } else if (FileManager.userExists(username)) {
            registerMessage.setText("Username already exists.");
        } else {
            FileManager.saveUser(new User(username, password));
            registerMessage.setText("Registration successful. Go to login.");
        }
    }

    @FXML
    public void switchToRegister(ActionEvent event) {
        switchScene("/register.fxml");
    }

    @FXML
    public void switchToLogin(ActionEvent event) {
        switchScene("/login.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) (loginUsernameField != null ? loginUsernameField.getScene().getWindow() : registerUsernameField.getScene().getWindow());
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


