package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReserveFormController {

    @FXML
    private AnchorPane ReserveFormPane;

    @FXML
    private JFXTextField txtstatus;

    @FXML
    private Label lblResId;

    @FXML
    private JFXDatePicker datePikerReserveForm;

    @FXML
    private JFXComboBox<?> combStudentIdReserveForm;

    @FXML
    private JFXComboBox<?> combRoomIdReserveForm;

    @FXML
    void btnAddStudentReserveForm(ActionEvent event) {

    }

    @FXML
    void btnBackReserveFormToDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage) ReserveFormPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelManagement/view/DashBoardForm.fxml"))));
        stage.centerOnScreen();
    }

    @FXML
    void btnReserve(ActionEvent event) {

    }


}
