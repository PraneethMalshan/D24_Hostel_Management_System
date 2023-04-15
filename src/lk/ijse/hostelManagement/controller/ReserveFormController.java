package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.ReserveBO;
import lk.ijse.hostelManagement.dto.ReservationDTO;
import lk.ijse.hostelManagement.dto.RoomDTO;
import lk.ijse.hostelManagement.dto.StudentDTO;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReserveFormController implements Initializable {
    @FXML
    private Label llbResId;
    @FXML
    private AnchorPane ReserveFormPane;
    @FXML
    private JFXTextField txtQTY;
    @FXML
    private JFXButton btnBack;
    @FXML
    private JFXButton btnAddStudentReserveForm;
    @FXML
    private JFXButton btnReserve;
    @FXML
    private JFXComboBox<String> combStudentIdReserveForm;
    @FXML
    private JFXComboBox<String> combRoomIdReserveForm;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtStudentName;
    @FXML
    private JFXTextField txtContactNo;
    @FXML
    private JFXTextField txtGender;
    @FXML
    private JFXTextField txtDOB;
    @FXML
    private JFXTextField txtRoomType;
    @FXML
    private JFXTextField txtKeyMoney;
    @FXML
    private JFXTextField txtMonthlyKeyMoner;

    private String RegID;

    private final ReserveBO purchaseRoomBO = (ReserveBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVE);


    @FXML
    void btnAddStudentReserveFormOnAction(ActionEvent event) throws SQLException, IOException {
        UILoader.NavigateToHome(ReserveFormPane, "ManageStudentForm");
    }

    @FXML
    void navigateToHome(MouseEvent event) throws SQLException, IOException {
        UILoader.NavigateToHome(ReserveFormPane, "DashBoardForm");
    }


    @FXML
    void btnReserveOnAction(ActionEvent event) {

        combStudentIdReserveForm.setDisable(false);
        combRoomIdReserveForm.setDisable(false);
        txtRoomType.setDisable(false);
        txtQTY.setDisable(false);
        txtMonthlyKeyMoner.setDisable(false);
        txtStudentName.setDisable(false);
        txtQTY.setDisable(false);
        txtAddress.setDisable(false);
        txtDOB.setDisable(false);
        txtGender.setDisable(false);
        txtContactNo.setDisable(false);

        double roomFee = Double.parseDouble(txtMonthlyKeyMoner.getText());
        double advance = Double.parseDouble(txtKeyMoney.getText());
        String status = String.valueOf(roomFee - advance);

        boolean b = saveReserve(RegID, combStudentIdReserveForm.getValue(), combRoomIdReserveForm.getValue(), LocalDate.now(), txtMonthlyKeyMoner.getText(), advance, status);
        if (b){
            NotificationController.SuccessfulTableNotification("Room Reserve", "Room Reserved in student ");
        }else {
            System.out.println(b);
            NotificationController.UnSuccessfulTableNotification("Room Reserve", "Room Reserved in student ");
        }

        RegID = generateNewOrderId();
        llbResId.setText(RegID);
        combRoomIdReserveForm.getSelectionModel().clearSelection();
        combStudentIdReserveForm.getSelectionModel().clearSelection();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtQTY.clear();
        txtMonthlyKeyMoner.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtDOB.clear();
        txtGender.clear();
        txtContactNo.clear();

    }

    private boolean saveReserve(String resID, String stId, String roomId, LocalDate orderDate, String keyMoney, double advance, String status) {
        try {
            return purchaseRoomBO.PurchaseRoom(new ReservationDTO(resID, orderDate, stId, roomId, keyMoney, advance, status));

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
          return  false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RegID = generateNewOrderId();
        llbResId.setText(RegID);
        btnReserve.setDisable(true);
        loadAllRoomIds();
        loadAllStudentIds();

        //---------Student to Combo-------------//
        combStudentIdReserveForm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisableRegisterButton();

            if (newValue != null){
                try {
                    if (!exitStudent(newValue + "")){
                        NotificationController.WarringError("Search Student Warning", newValue, "There is no such student associated with the ");
                    }
                    /*Search student*/
                    StudentDTO studentDTO = purchaseRoomBO.searchStudent(newValue + "");
                    txtStudentName.setText(studentDTO.getName());
                    txtAddress.setText(studentDTO.getAddress());
                    txtDOB.setText(studentDTO.getDob() + "");
                    txtGender.setText(studentDTO.getGender());
                    txtContactNo.setText(studentDTO.getContact_no());

                } catch (SQLException | ClassNotFoundException e) {
                    NotificationController.WarringError("Search Student Warning", newValue, "Failed to find the Student ");
                }
            }else {
                txtStudentName.clear();
            }
        });

        //---------Room to Combo-------------//
        combRoomIdReserveForm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newRoomId) -> {
            btnReserve.setDisable(newRoomId == null);

            if (newRoomId != null){
                try {
                    exitRooms(newRoomId + "");
                    RoomDTO room = purchaseRoomBO.searchRoom(newRoomId + "");
                    txtRoomType.setText(room.getType());
                    txtQTY.setText(String.valueOf(room.getQty()));
                    txtMonthlyKeyMoner.setText(room.getKey_money());


                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }else {
                txtMonthlyKeyMoner.clear();
                txtQTY.clear();
                txtStudentName.clear();
                txtMonthlyKeyMoner.clear();
            }

        });

    }

    private boolean exitStudent(String id) throws SQLException, ClassNotFoundException {
        return purchaseRoomBO.checkStudentIsAvailable(id);
    }

    private boolean exitRooms(String id) throws SQLException, ClassNotFoundException {
        return purchaseRoomBO.checkRoomIsAvailable(id);
    }

    private void enableOrDisableRegisterButton() {
        btnReserve.setDisable(combRoomIdReserveForm.getSelectionModel().getSelectedItem() == null);

    }

    private String generateNewOrderId() {

        try {
            return purchaseRoomBO.generateNewReserveID();
        } catch (SQLException e) {
            NotificationController.Warring("Generate Order Id", "Failed to generate a new order id...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "REG-001";
    }

    private void loadAllStudentIds() {
        try {
            ArrayList<StudentDTO> all = purchaseRoomBO.getAllStudents();
            for (StudentDTO studentDTO : all){
                combStudentIdReserveForm.getItems().add(studentDTO.getStudent_id());
            }
        } catch (SQLException e) {
            NotificationController.Warring("Student Load", "Failed to load customer ids.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void loadAllRoomIds() {
        try {
            ArrayList<RoomDTO> all = purchaseRoomBO.getAllRoom();
            for (RoomDTO roomDTO : all){
                combRoomIdReserveForm.getItems().add(roomDTO.getRoom_type_id());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }





    @FXML
    void btnBackReserveFormToDashboardOnAction(ActionEvent event) {
    }


}
