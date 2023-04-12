package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageStudentFormController {

    @FXML
    private AnchorPane manageStudentPane;

    @FXML
    private JFXTextField txtStudentId;

    @FXML
    private JFXTextField txtStudentName;

    @FXML
    private JFXTextField txtStudentAddress;

    @FXML
    private JFXTextField txtStudentContactNo;

    @FXML
    private JFXDatePicker datePikerStudentDOB;

    @FXML
    private JFXComboBox<?> combStudentGender;

    @FXML
    private JFXTextField txtStudentSearch;

    @FXML
    private TableView<?> tblStudent;

    @FXML
    void btnStudentAdd(ActionEvent event) {

    }

    @FXML
    void btnStudentDelete(ActionEvent event) {

    }

    @FXML
    void btnStudentSave(ActionEvent event) {

    }

    @FXML
    void btnStudentSearch(ActionEvent event) {

    }



    @FXML
    void btnBackToDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage) manageStudentPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelManagement/view/DashBoardForm.fxml"))));
        stage.centerOnScreen();
    }


}
