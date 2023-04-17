package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hostelManagement.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class DashBoardFormController {
    @FXML
    public JFXButton btnManageUser;
    public ImageView imgStudent;
    public ImageView imgKeyMoney;
    public ImageView imgRooms;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView btnBack;
    @FXML
    private AnchorPane dashboardPane;

    public void userOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(dashboardPane, "ManageUserForm");
    }


    public void navigate(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() instanceof ImageView){
            ImageView icon = (ImageView) mouseEvent.getSource();

            Parent root = null;

            switch (icon.getId()){
                case "imgStudent":
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/lk/ijse/hostelManagement/view/ReserveForm.fxml")));
                    break;

                case "imgRooms":
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/lk/ijse/hostelManagement/view/ManageRoomForm.fxml")));
                    break;

                case "imgKeyMoney":
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/lk/ijse/hostelManagement/view/FindRemainKeyMoneyForm.fxml")));
                    break;
            }

            if (root != null){
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.dashboardPane.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();
            }
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() instanceof ImageView){
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()){
                case "imgStudent":
                    lblMenu.setText("Manage Registration");
                    lblDescription.setText("Click to Make Registration and Add, Update, Delete Student");
                    break;

                case "imgRooms":
                    lblMenu.setText("Manage Rooms");
                    lblDescription.setText("Click to Add, Update, Delete Rooms");
                    break;

                case "imgKeyMoney":
                    lblMenu.setText("Find Key Money");
                    lblDescription.setText("Click to find remain key money student");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.ORANGE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);

        }

    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() instanceof ImageView){
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToY(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");

        }

    }
    public void navigateToLoginPage(MouseEvent mouseEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(dashboardPane, "UserLoginForm");
    }

    public void BackOnAction(MouseEvent mouseEvent) {
    }

}
