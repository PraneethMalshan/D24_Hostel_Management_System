package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Label lblHide;

    @FXML
    void btnLogin(ActionEvent event) {

    }

    @FXML
    void btnCancel(ActionEvent event) {

    }

    @FXML
    void hidePasswordOnMousePressedd(MouseEvent event) {
        Image img = new Image("/lk/ijse/hostelManagement/assests/Close_eye.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPasswordLogin.setText(txtPasswordLogin.getPromptText());
        txtPasswordLogin.setPromptText("");
        txtPasswordLogin.setDisable(false);
    }

    @FXML
    void showPasswordOnMousePressed(MouseEvent event) {
        Image img = new Image("/lk/ijse/hostelManagement/assests/Open_eye.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPasswordLogin.setPromptText(txtPasswordLogin.getText());
        txtPasswordLogin.setText("");
        txtPasswordLogin.setDisable(true);
        txtPasswordLogin.requestFocus();
    }

}
