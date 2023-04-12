package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageRoomFormController {

    @FXML
    private AnchorPane ManageRoomPane;

    @FXML
    private JFXTextField txtRoomId;

    @FXML
    private JFXTextField txtRoomType;

    @FXML
    private JFXTextField txtRoomKeyMoney;

    @FXML
    private JFXTextField txtRoomQTY;

    @FXML
    private JFXTextField txtRoomSearch;

    @FXML
    private TableView<?> tblRoom;

    @FXML
    void btnBackManageRoomToDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage) ManageRoomPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelManagement/view/DashBoardForm.fxml"))));
        stage.centerOnScreen();
    }

    @FXML
    void btnRoomAdd(ActionEvent event) {

    }

    @FXML
    void btnRoomDelete(ActionEvent event) {

    }

    @FXML
    void btnRoomSave(ActionEvent event) {

    }

    @FXML
    void btnRoomSearch(ActionEvent event) {

    }

}
