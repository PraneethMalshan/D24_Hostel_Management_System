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
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.UserBO;
import lk.ijse.hostelManagement.dto.LoginDTO;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserLoginFormController {

    public PasswordField txtUserPasswordLogin;
    @FXML
    private AnchorPane LoginPane;
    @FXML
    private JFXTextField txtUserNameLogin;
    @FXML
    private Label lblHide;

    int attempts = 0;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @FXML
    void btnLogin(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        UILoader.LoginOnAction(LoginPane, "DashBoardForm");
        NotificationController.LoginSuccessfulNotification("Admin");
        ArrayList<LoginDTO> loginDTOS = userBO.getAllUsers();
        attempts++;
        loginDTOS.forEach(e -> {
            if (attempts <= 3){
                if (e.getUserID().equals(txtUserNameLogin.getText()) && e.getPassword().equals(txtUserPasswordLogin.getText())){
                    try {
                        UILoader.LoginOnAction(LoginPane, "DashBoardForm");
                    } catch (IOException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }else {

                }
            }else {
                txtUserNameLogin.setEditable(false);
                txtUserPasswordLogin.setEditable(false);
                NotificationController.LoginUnSuccessfulNotification("Account is Temporarily Disabled or You Did not Sign in Correctly.");
            }
        });
    }

    @FXML
    void btnCancel(ActionEvent event) {
        txtUserNameLogin.clear();
        txtUserPasswordLogin.clear();

    }

    @FXML
    void hidePasswordOnMousePressedd(MouseEvent event) {
        Image img = new Image("/lk/ijse/hostelManagement/assests/Close_eye.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtUserPasswordLogin.setText(txtUserPasswordLogin.getPromptText());
        txtUserPasswordLogin.setPromptText("");
        txtUserPasswordLogin.setDisable(false);
    }

    @FXML
    void showPasswordOnMousePressed(MouseEvent event) {
        Image img = new Image("/lk/ijse/hostelManagement/assests/Open_eye.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtUserPasswordLogin.setPromptText(txtUserPasswordLogin.getText());
        txtUserPasswordLogin.setText("");
        txtUserPasswordLogin.setDisable(true);
        txtUserPasswordLogin.requestFocus();
    }

}
