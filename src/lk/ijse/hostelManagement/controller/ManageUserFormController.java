package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.UserBO;
import lk.ijse.hostelManagement.dto.LoginDTO;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;
import lk.ijse.hostelManagement.view.TM.UserLoginTM;

import java.io.IOException;
import java.io.NotActiveException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageUserFormController implements Initializable {
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
    private TableView<UserLoginTM> tblUser;
    @FXML
    private JFXButton btnAddNewUser;
    @FXML
    private JFXButton btnUserDelete;
    @FXML
    private JFXButton btnUserSave;
    @FXML
    private JFXButton btnBack;
    @FXML
    private JFXTextField txtUserAddress;
    @FXML
    private JFXComboBox<String> combGender;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @FXML
    void btnAddNewUserOnAction(ActionEvent event) {
        txtUserId.setDisable(false);
        txtUserName.setDisable(false);
        txtContactNo.setDisable(false);
        txtUserAddress.setDisable(false);
        txtPassword.setDisable(false);
        combGender.setDisable(false);

        txtUserId.clear();
        txtUserName.clear();
        txtContactNo.clear();
        txtUserAddress.clear();
        txtPassword.clear();
        combGender.getSelectionModel().clearSelection();
        txtUserId.requestFocus();
        btnUserSave.setDisable(false);
        btnUserSave.setText("Save");
        tblUser.getSelectionModel().clearSelection();

    }

    @FXML
    void btnBackOnActionManageUserToDashboard(ActionEvent event) {

    }

    @FXML
    void btnUserDeleteOnAction(ActionEvent event) {

        String code = tblUser.getSelectionModel().getSelectedItem().getUserID();
        try {
            if (!exitUser(code)){
                NotificationController.WarringError("Delete User Warning", code, "There is no such User associated with the ");
            }
            userBO.deleteUser(code);
            tblUser.getItems().remove(tblUser.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "User");
            tblUser.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            NotificationController.WarringError("Delete User Warning", code, "Failed to delete the User ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void initUI() {
        txtUserId.clear();
        txtUserName.clear();
        txtContactNo.clear();
        txtUserAddress.clear();
        txtPassword.clear();
        combGender.getSelectionModel().clearSelection();

        txtUserId.setDisable(true);
        txtUserName.setDisable(true);
        txtContactNo.setDisable(true);
        txtUserAddress.setDisable(true);
        txtPassword.setDisable(true);
        combGender.setDisable(true);

        btnUserSave.setDisable(true);
        btnUserDelete.setDisable(true);
    }

    @FXML
    void btnUserSave(ActionEvent event) throws SQLException, ClassNotFoundException {

        String id = txtUserId.getText();
        String name = txtUserName.getText();
        String cNO = txtContactNo.getText();
        String address = txtUserAddress.getText();
        String pws = txtPassword.getText();
        String gender = combGender.getValue();

        if (!id.matches("^([A-z 0-9]{6,20})$")){
            NotificationController.Warring("User Id", "Invalid User Id.");
            txtUserId.requestFocus();
            return;
        }else if(!name.matches("^([A-Z a-z]{5,40})$")){
            NotificationController.Warring("Student Name", "User Name.");
            txtUserName.requestFocus();
            return;
        }else if (!cNO.matches("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$")) {
            NotificationController.Warring("Contact Number", "Invalid User Contact Number.");
            txtContactNo.requestFocus();
            return;
        }else if (!address.matches("^([A-Za-z]{4,10})$")) {
            NotificationController.Warring("Address", "Invalid User Address.");
            txtUserAddress.requestFocus();
            return;
        }else if (!pws.matches("^([A-z 0-9]{4,20})$")) {
            NotificationController.Warring("Password", "Invalid User Password.");
            txtPassword.requestFocus();
            return;
        }else if (!gender.matches("^([A-Z a-z]{4,20})$")) {
            NotificationController.Warring("Gender", "Invalid User Gender.");
            combGender.requestFocus();
            return;
        }

        if (btnUserSave.getText().equalsIgnoreCase("save")){
            /*Save User*/
            try {
                if (exitUser(id)){
                    NotificationController.WarringError("Save User Warning", id, "Already exists ");
                }
                userBO.saveUser(new LoginDTO(id, name, address, cNO, pws, gender));
                tblUser.getItems().add(new UserLoginTM(id, name, address, cNO, pws, gender));
                NotificationController.SuccessfulTableNotification("Save", "User");

            }catch (SQLException e){
                NotificationController.WarringError("Save User Warning", id + e.getMessage(), "Failed to save the User");
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }else {
            /*Update User*/
            try {
                if (!exitUser(id)){
                    NotificationController.WarringError("Update User Warning", id, "There is no such User associated with the ");
                }
                //User update

                userBO.updateUser(new LoginDTO(id, name, address, cNO,pws, gender));
                NotificationController.SuccessfulTableNotification("Update", "User");

            }catch (SQLException e){
                NotificationController.WarringError("Update User Warning", id + e.getMessage(), "Failed to update the User ");
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            UserLoginTM selectedItem = tblUser.getSelectionModel().getSelectedItem();
            selectedItem.setUserID(id);
            selectedItem.setName(name);
            selectedItem.setAddress(address);
            selectedItem.setContact_no(cNO);
            selectedItem.setGender(gender);
            tblUser.refresh();

        }
        btnAddNewUser.fire();

    }

    private boolean exitUser(String id) throws SQLException, ClassNotFoundException {
        return userBO.existUser(id);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        combGender.getItems().addAll("Male", "Female", "Other");

        tblUser.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("userID"));
        tblUser.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblUser.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblUser.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        tblUser.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Password"));
        tblUser.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        initUI();

        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnUserDelete.setDisable(newValue == null);
            btnUserSave.setText(newValue != null ? "Update" : "Save");
            btnUserSave.setDisable(newValue == null);

            if (newValue != null){
                txtUserId.setText(newValue.getUserID());
                txtUserName.setText(newValue.getName());
                txtContactNo.setText(newValue.getContact_no());
                txtUserAddress.setText(newValue.getAddress());
                txtPassword.setText(newValue.getPassword());
                combGender.setValue(newValue.getGender() + "");

                txtUserId.setDisable(false);
                txtUserName.setDisable(false);
                txtContactNo.setDisable(false);
                txtUserAddress.setDisable(false);
                txtPassword.setDisable(false);
                combGender.setDisable(false);

            }

        });

        txtUserAddress.setOnAction(event -> btnUserSave.fire());
        loadAllUsers();

    }

    private void loadAllUsers() {
        tblUser.getItems().clear();
        /*Get all Student*/

        try {
            ArrayList<LoginDTO> allUsers = userBO.getAllUsers();
            for (LoginDTO loginDTO : allUsers){
                tblUser.getItems().add(new UserLoginTM(loginDTO.getUserID(), loginDTO.getName(), loginDTO.getAddress(), loginDTO.getContact_no(), loginDTO.getPassword(), loginDTO.getGender()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void navigateToHome(MouseEvent event) throws SQLException, IOException {
        UILoader.NavigateToHome(ManageUserPane, "DashBoardForm");
    }
}
