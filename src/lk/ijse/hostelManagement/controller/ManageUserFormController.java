package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class ManageUserFormController {
    @FXML
    private AnchorPane ManageUserPane;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private TableView<?> tblUser;

    @FXML
    private JFXTextField txtUserAddress;

    @FXML
    private JFXComboBox<?> combGender;

    @FXML
    void btnBackManageUserToDashboard(ActionEvent event) {

    }

    @FXML
    void btnUserAdd(ActionEvent event) {

    }

    @FXML
    void btnUserDelete(ActionEvent event) {

    }

    @FXML
    void btnUserSave(ActionEvent event) {

    }

}
