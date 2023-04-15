package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.hostelManagement.bo.custom.ReserveBO;
import lk.ijse.hostelManagement.dto.ReservationDTO;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;
import lk.ijse.hostelManagement.view.TM.ReservationTM;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FindRemainKeyMoneyFormController implements Initializable {

    @FXML
    private AnchorPane FindRemainKeymoneyPane;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private TableView<ReservationTM> tblFindRemainKeymoney;
    @FXML
    private JFXButton btnBack;

    private final ReserveBO reserveBO = (ReserveBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVE);

    @FXML
    void BackOnActionFindRemainToDashboard(ActionEvent event) {
    }
    @FXML
    void btnSearchOnAction(ActionEvent event) {
    }

    @FXML
    void navigateToHome(MouseEvent event) throws SQLException, IOException {
        UILoader.NavigateToHome(FindRemainKeymoneyPane, "DashBoardForm");
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()){
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllReserve();
        }else {
            if (RegExit(txtSearch.getText())){
                tblFindRemainKeymoney.getItems().clear();
                ArrayList<ReservationDTO> arrayList = reserveBO.getAllReserveSearch(txtSearch.getText());
                if (arrayList != null){
                    for (ReservationDTO reservationDTO : arrayList){
                        tblFindRemainKeymoney.getItems().add(new ReservationTM(reservationDTO.getRes_id(), reservationDTO.getDate(), reservationDTO.getStudent_id(), reservationDTO.getRoom_type_id(), reservationDTO.getKey_money(), reservationDTO.getAdvance(), reservationDTO.getStatus()));
                    }
                }
                else {
                    tblFindRemainKeymoney.getItems().clear();
                    NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
                }
            }
        }
    }

    private boolean RegExit(String id) throws SQLException, ClassNotFoundException {
        return reserveBO.existReserveID(id);
    }

    private void loadAllReserve() {
        tblFindRemainKeymoney.getItems().clear();
        /*Get all Reserve*/
        try {
            ArrayList<ReservationDTO> allReserve = reserveBO.getAllReserve();
            for (ReservationDTO reservationDTO : allReserve){
                tblFindRemainKeymoney.getItems().add(new ReservationTM(reservationDTO.getRes_id(), reservationDTO.getDate(), reservationDTO.getStudent_id(), reservationDTO.getRoom_type_id(), reservationDTO.getKey_money(), reservationDTO.getAdvance(), reservationDTO.getStatus()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblFindRemainKeymoney.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("res_id"));
        tblFindRemainKeymoney.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("student_id"));
        tblFindRemainKeymoney.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        tblFindRemainKeymoney.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblFindRemainKeymoney.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("key_money"));
        tblFindRemainKeymoney.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("advance"));
        tblFindRemainKeymoney.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("status"));
        loadAllReserve();


    }
    }

