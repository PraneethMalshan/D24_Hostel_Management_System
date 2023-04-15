package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.RoomBO;
import lk.ijse.hostelManagement.dto.RoomDTO;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;
import lk.ijse.hostelManagement.view.TM.RoomTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageRoomFormController implements Initializable {

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
    private TableView<RoomTM> tblRoom;
    @FXML
    private JFXButton btnRoomAddNew;
    @FXML
    private JFXButton btnRoomDelete;
    @FXML
    private JFXButton btnRoomSave;
    @FXML
    private JFXButton btnBack;

    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);

    @FXML
    void btnRoomSaveOnAction(ActionEvent event) {
        String id = txtRoomId.getText();
        String type = txtRoomType.getText();
        int qty = Integer.parseInt(txtRoomQTY.getText());
        String key_money = txtRoomKeyMoney.getText();

        if (!id.matches("^RM-[0-9]{3,4}$")){
            NotificationController.Warring("Room ID", "Invalid Room ID.Check STU-000 type in your entered value.");
            txtRoomId.requestFocus();
            return;
        }else if (!type.matches("^([A-z]{1,9}|[A-z]{1,9}[ /|-]?[A-z]{1,9}[ /|-]?[A-z]{1,9})$")) {
            NotificationController.Warring("Room Type", "Invalid Room Type.");
            txtRoomType.requestFocus();
            return;
        }else if (!txtRoomKeyMoney.getText().matches("^([A-Z a-z0-9]{4,8})$")) {
            NotificationController.Warring("Room Rent", "Invalid Room Rent.");
            txtRoomKeyMoney.requestFocus();
            return;
        }else if (!txtRoomQTY.getText().matches("^[1-9][0-9]*$")) {
            NotificationController.Warring("Room Qty", "Invalid Room Qty");
            txtRoomQTY.requestFocus();
            return;
        }

        if (btnRoomSave.getText().equalsIgnoreCase("save")){
            /*Save Rooms*/

            try {
                if (exitRooms(id)){
                    NotificationController.WarringError("Save Rooms Warning", id, "Already exists ");
                }
                roomBO.saveRooms(new RoomDTO(id, type, key_money, qty));
                tblRoom.getItems().add(new RoomTM(id, type, key_money, qty));
                NotificationController.SuccessfulTableNotification("Save", "Rooms");

            } catch (SQLException e) {
                NotificationController.WarringError("Save Rooms Warning", id + e.getMessage(), "Failed to save the Rooms ");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            /*Update Rooms*/
            try {
                if (!exitRooms(id)){
                    NotificationController.WarringError("Update Rooms Warning", id, "There is no such Rooms associated with the ");
                }
                //Rooms update
                roomBO.updateRooms(new RoomDTO(id, type, key_money, qty));
                NotificationController.SuccessfulTableNotification("Update", "Rooms");

            } catch (SQLException e) {
                NotificationController.WarringError("Update Rooms Warning", id + e.getMessage(), "Failed to update the Rooms ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            RoomTM selectedRoom = tblRoom.getSelectionModel().getSelectedItem();
            selectedRoom.setRoom_type_id(id);
            selectedRoom.setType(type);
            selectedRoom.setKey_money(key_money);
            selectedRoom.setQty(qty);
            tblRoom.refresh();
        }
        btnRoomAddNew.fire();
    }

    @FXML
    void btnRoomDeleteOnAction(ActionEvent event) {

        String code = tblRoom.getSelectionModel().getSelectedItem().getRoom_type_id();

        try {
            if (!exitRooms(code)){
                NotificationController.WarringError("Delete Rooms Warning", code, "There is no such Rooms associated with the ");
            }
            roomBO.deleteRooms(code);
            tblRoom.getItems().remove(tblRoom.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Rooms");
            tblRoom.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            NotificationController.WarringError("Delete Rooms Warning", code, "Failed to delete the Rooms ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private boolean exitRooms(String id) throws SQLException, ClassNotFoundException {
        return roomBO.existRoomsID(id);
    }

    @FXML
    void navigateToHome(MouseEvent event) throws SQLException, IOException {
        UILoader.NavigateToHome(ManageRoomPane, "DashBoardForm");
    }

    @FXML
    void txtRoomSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtRoomSearch.getText().trim().isEmpty()){
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllRoom();
        }else {
            if (exitRooms(txtRoomSearch.getText())){
                tblRoom.getItems().clear();
                ArrayList<RoomDTO> arrayList = roomBO.searchAllRooms(txtRoomSearch.getText());
                if (arrayList != null){
                    for (RoomDTO roomDTO : arrayList){
                        tblRoom.getItems().add(new RoomTM(roomDTO.getRoom_type_id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
                    }
                }
            }else {
                tblRoom.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");

            }

        }
    }

    @FXML
    void btnRoomAddNewOnAction(ActionEvent event) {

        txtRoomId.setDisable(false);
        txtRoomType.setDisable(false);
        txtRoomKeyMoney.setDisable(false);
        txtRoomQTY.setDisable(false);

        txtRoomId.clear();
        txtRoomType.clear();
        txtRoomKeyMoney.clear();
        txtRoomQTY.clear();

        txtRoomId.requestFocus();
        btnRoomSave.setDisable(false);
        btnRoomSave.setText("save");
        tblRoom.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblRoom.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        tblRoom.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblRoom.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("key_money"));
        tblRoom.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));

        initUI();

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnRoomDelete.setDisable(newValue == null);
            btnRoomSave.setText(newValue != null ? "Update" : "Save");
            btnRoomSave.setDisable(newValue == null);

            if (newValue != null){
                //------------------------Text Filed Load----------------------//
                txtRoomId.setText(newValue.getRoom_type_id());
                txtRoomType.setText(newValue.getType());
                txtRoomKeyMoney.setText(newValue.getKey_money() + "");
                txtRoomQTY.setText(newValue.getQty() + "");

                txtRoomId.setDisable(false);
                txtRoomType.setDisable(false);
                txtRoomKeyMoney.setDisable(false);
                txtRoomQTY.setDisable(false);

            }
        });

        txtRoomQTY.setOnAction(event -> btnRoomSave.fire());
        loadAllRoom();

    }

    private void loadAllRoom() {
        tblRoom.getItems().clear();
        /*Get all Room*/

        try {
            ArrayList<RoomDTO> allRoom = roomBO.getAllRooms();
            for (RoomDTO roomDTO : allRoom){
                tblRoom.getItems().add(new RoomTM(roomDTO.getRoom_type_id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {

        txtRoomId.clear();
        txtRoomType.clear();
        txtRoomKeyMoney.clear();
        txtRoomQTY.clear();

        txtRoomId.setDisable(true);
        txtRoomType.setDisable(true);
        txtRoomKeyMoney.setDisable(true);
        txtRoomQTY.setDisable(true);

        btnRoomSave.setDisable(true);
        btnRoomDelete.setDisable(true);

    }


    @FXML
    void btnBackManageRoomToDashboardOnAction(ActionEvent event) {
    }

    @FXML
    void btnRoomSearch(ActionEvent event) {
    }



}
