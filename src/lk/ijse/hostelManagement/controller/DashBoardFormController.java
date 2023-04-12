package lk.ijse.hostelManagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    void btnFindRemainKeyMoneyStudent(MouseEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelManagement/view/ReserveForm.fxml"))));
        stage.centerOnScreen();
    }

    @FXML
    void btnMakeRegistrationStudent(MouseEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelManagement/view/ManageStudentForm.fxml"))));
        stage.centerOnScreen();
    }

    @FXML
    void btnManageRooms(MouseEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelManagement/view/ManageRoomForm.fxml"))));
        stage.centerOnScreen();
    }

    @FXML
    void btnNavigateToLoginPage(MouseEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelManagement/view/UserLoginForm.fxml"))));
        stage.centerOnScreen();
    }
}
