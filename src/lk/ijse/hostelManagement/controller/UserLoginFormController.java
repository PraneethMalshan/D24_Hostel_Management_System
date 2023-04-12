package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserLoginFormController {
    @FXML
    private AnchorPane LoginPane;

    @FXML
    private JFXPasswordField txtPasswordLogin;

    @FXML
    private JFXTextField txtUserNameLogin;

    @FXML
    void btnLogin(ActionEvent event) throws IOException {

        Stage stage = (Stage) LoginPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelManagement/view/DashBoardForm.fxml"))));
        stage.centerOnScreen();

    }

}
