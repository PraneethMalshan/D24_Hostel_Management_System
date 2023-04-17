package lk.ijse.hostelManagement.controller;

import antlr.actions.cpp.ActionLexer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import lk.ijse.hostelManagement.bo.custom.StudentBO;
import lk.ijse.hostelManagement.dto.StudentDTO;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;
import lk.ijse.hostelManagement.view.TM.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageStudentFormController implements Initializable {

    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);


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
    private JFXComboBox combStudentGender;
    @FXML
    private JFXTextField txtStudentSearch;
    @FXML
    private TableView<StudentTM> tblStudent;
    @FXML
    private JFXButton btnStudentAdd;
    @FXML
    private JFXButton btnStudentDelete;
    @FXML
    private JFXButton btnStudentSave;
    @FXML
    private JFXButton btnBack1;


    @FXML
    void btnStudentAddNewOnAction(ActionEvent event) {

        txtStudentId.setDisable(false);
        txtStudentName.setDisable(false);
        txtStudentContactNo.setDisable(false);
        txtStudentAddress.setDisable(false);
        datePikerStudentDOB.setDisable(false);
        combStudentGender.setDisable(false);

        txtStudentId.clear();
        txtStudentName.clear();
        txtStudentContactNo.clear();
        txtStudentAddress.clear();
        datePikerStudentDOB.getEditor().clear();
        combStudentGender.getSelectionModel().clearSelection();

        txtStudentId.requestFocus();
        btnStudentSave.setDisable(false);
        btnStudentSave.setText("Save");
        tblStudent.getSelectionModel().clearSelection();

    }

    @FXML
    void btnStudentSaveOnAction(ActionEvent event) {

        String id = txtStudentId.getText();
        String name = txtStudentName.getText();
        String cNO = txtStudentContactNo.getText();
        String address = txtStudentAddress.getText();
        LocalDate dob = datePikerStudentDOB.getValue();
        String gender = (String) combStudentGender.getValue();

        if (!id.matches("^(STU-[0-9]{3,4})$")) {
            NotificationController.Warring("Student Id", "Invalid Student Id.Check STU-000 type in your entered value.");
            txtStudentId.requestFocus();
            return;
        }else if (!name.matches("^([A-Z a-z]{5,40})$")) {
            NotificationController.Warring("Student Name", "Student Name.");
            txtStudentName.requestFocus();
            return;
        }else if (!cNO.matches("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$")) {
            NotificationController.Warring("Contact Number", "Invalid Student Contact Number.");
            txtStudentContactNo.requestFocus();
            return;
        }else if (!address.matches("^([A-Za-z]{4,10})$")) {
            NotificationController.Warring("Address", "Invalid Student Address.");
            txtStudentAddress.requestFocus();
            return;
        }else if (!gender.matches("^([A-Z a-z]{4,20})$")) {
            NotificationController.Warring("Gender", "Invalid Student Gender.");
            combStudentGender.requestFocus();
            return;
        }

        if (btnStudentSave.getText().equalsIgnoreCase("save")){
            /*Save Student*/
            try {
                if (exitStudent(id)){
                    NotificationController.WarringError("Save Student Warning", id, "Already exists ");
                }
                studentBO.saveStudent(new StudentDTO(id, name, address, cNO, dob, gender));
                tblStudent.getItems().add(new StudentTM(id, name, address, cNO, dob, gender));
                NotificationController.SuccessfulTableNotification("Save", "Student");

            } catch (SQLException e) {
                NotificationController.WarringError("Save Student Warning", id + e.getMessage(), "Failed to save the Student ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            /*Update Student*/
            try {
                if (!exitStudent(id)){
                    NotificationController.WarringError("Update Student Warning", id, "There is no such Student associated with the ");
                }
                //Rooms update
                studentBO.updateStudent(new StudentDTO(id, name, address, cNO, dob, gender ));
                NotificationController.SuccessfulTableNotification("Update", "Student");
            } catch (SQLException e) {
                NotificationController.WarringError("Update Student Warning", id + e.getMessage(), "Failed to update the Student ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();
            selectedItem.setStudent_id(id);
            selectedItem.setName(name);
            selectedItem.setAddress(address);
            selectedItem.setContact_no(cNO);
            selectedItem.setDob(dob);
            selectedItem.setGender(gender);
            tblStudent.refresh();
        }
        btnStudentAdd.fire();
    }

    @FXML
    void btnStudentDeleteOnAction(ActionEvent event) {

        String code = tblStudent.getSelectionModel().getSelectedItem().getStudent_id();
        try {
            if (!exitStudent(code)){
                NotificationController.WarringError("Delete Student Warning", code, "There is no such Student associated with the ");
            }
            studentBO.deleteStudent(code);
            tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Student");
            tblStudent.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            NotificationController.WarringError("Delete Student Warning", code, "Failed to delete the Student ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void txtStudentSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (txtStudentSearch.getText().trim().isEmpty()){
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllStudent();
        } else {
            if (exitStudent(txtStudentSearch.getText())){
                tblStudent.getItems().clear();
                ArrayList<StudentDTO> arrayList = studentBO.searchAllStudent(txtStudentSearch.getText());
                if (arrayList != null){
                    for (StudentDTO studentDTO : arrayList){
                        tblStudent.getItems().add(new StudentTM(studentDTO.getStudent_id(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact_no(), studentDTO.getDob(), studentDTO.getGender()));
                    }
                }
            } else {
                tblStudent.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    private void loadAllStudent() {

        tblStudent.getItems().clear();
        /*Get all Student*/
        try {
            ArrayList<StudentDTO> allStudent = studentBO.getAllStudent();
            for (StudentDTO studentDTO : allStudent){
                tblStudent.getItems().add(new StudentTM(studentDTO.getStudent_id(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact_no(), studentDTO.getDob(), studentDTO.getGender()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void navigateToHome(MouseEvent event) throws SQLException, IOException {
        UILoader.NavigateToHome(manageStudentPane, "ReserveForm");
    }


    private void initUI() {

        txtStudentId.clear();
        txtStudentName.clear();
        txtStudentContactNo.clear();
        txtStudentAddress.clear();
        datePikerStudentDOB.getEditor().clear();
        combStudentGender.getSelectionModel().clearSelection();

        txtStudentId.setDisable(true);
        txtStudentName.setDisable(true);
        txtStudentContactNo.setDisable(true);
        txtStudentAddress.setDisable(true);
        datePikerStudentDOB.setDisable(true);
        combStudentGender.setDisable(true);

        btnStudentSave.setDisable(true);
        btnStudentDelete.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        combStudentGender.getItems().addAll("Male", "Female", "Other");

        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("student_id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        initUI();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnStudentDelete.setDisable(newValue == null);
            btnStudentSave.setText(newValue != null ? "Update" : "Save");
            btnStudentSave.setDisable(newValue == null);

            if (newValue != null){
                //------------------------Text Filed Load----------------------//
                txtStudentId.setText(newValue.getStudent_id());
                txtStudentName.setText(newValue.getName());
                txtStudentContactNo.setText(newValue.getContact_no());
                txtStudentAddress.setText(newValue.getAddress());
                datePikerStudentDOB.setValue(newValue.getDob());
                combStudentGender.setValue(newValue.getGender() + "");


                txtStudentId.setDisable(false);
                txtStudentName.setDisable(false);
                txtStudentContactNo.setDisable(false);
                txtStudentAddress.setDisable(false);
                datePikerStudentDOB.setDisable(false);
                combStudentGender.setDisable(false);
            }
        });
        txtStudentAddress.setOnAction(event -> btnStudentSave.fire());
        loadAllStudent();
    }

    private boolean exitStudent(String id) throws SQLException, ClassNotFoundException {
        return studentBO.existStudentID(id);
    }


    @FXML
    void BackToDashboardOnAction(ActionEvent event) {
    }


    @FXML
    void btnStudentSearch(ActionEvent event) {
    }




































   /* private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);

    public JFXButton btnStudentUpdate;
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
    private JFXComboBox combStudentGender;
    @FXML
    private JFXTextField txtStudentSearch;
    @FXML
    private TableView<StudentTM> tblStudent;
    @FXML
    private JFXButton btnStudentAdd;
    @FXML
    private JFXButton btnStudentDelete;
    @FXML
    private JFXButton btnStudentSave;
    @FXML
    private JFXButton btnBack1;

    @FXML
    void btnStudentAddNewOnAction(ActionEvent event) {

        txtStudentId.setDisable(false);
        txtStudentName.setDisable(false);
        txtStudentContactNo.setDisable(false);
        txtStudentAddress.setDisable(false);
        datePikerStudentDOB.setDisable(false);
        combStudentGender.setDisable(false);

        txtStudentId.clear();
        txtStudentName.clear();
        txtStudentContactNo.clear();
        txtStudentAddress.clear();
        datePikerStudentDOB.getEditor().clear();
        combStudentGender.getSelectionModel().clearSelection();

        txtStudentId.requestFocus();
        btnStudentSave.setDisable(false);
        btnStudentSave.setText("Save");
        tblStudent.getSelectionModel().clearSelection();

    }


    @FXML
    void btnStudentSaveOnAction(ActionEvent event) {

        String id = txtStudentId.getText();
        String name =  txtStudentName.getText();
        String cNO = txtStudentContactNo.getText();
        String address =  txtStudentAddress.getText();
        LocalDate dob = datePikerStudentDOB.getValue();
        String gender = (String)  combStudentGender.getValue();

        if (!id.matches("^(ST-[0-9]{3,4})$")) {
            NotificationController.Warring("Student Id", "Invalid Student Id.Check ST-000 type in your entered value.");
            txtStudentId.requestFocus();
            return;
        }else if (!name.matches("^([A-Z a-z]{5,40})$")) {
            NotificationController.Warring("Student Name", "Student Name.");
            txtStudentName.requestFocus();
            return;
        }else if (!cNO.matches("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$")) {
            NotificationController.Warring("Contact Number", "Invalid Student Contact Number.");
            txtStudentContactNo.requestFocus();
            return;
        }else if (!address.matches("^([A-Za-z]{4,10})$")) {
            NotificationController.Warring("Address", "Invalid Student Address.");
            txtStudentAddress.requestFocus();
            return;
        }else if (!gender.matches("^([A-Z a-z]{4,20})$")) {
            NotificationController.Warring("Gender", "Invalid Student Gender.");
            combStudentGender.requestFocus();
            return;
        }

        if (btnStudentSave.getText().equalsIgnoreCase("save")){
            *//*Save Student*//*

            try {
                if (exitStudent(id)) {
                    NotificationController.WarringError("Save Student Warning", id, "Already exists ");
                }
                studentBO.saveStudent(new StudentDTO(id, name,address, cNO, dob, gender));
                tblStudent.getItems().add(new StudentTM(id, name,address, cNO, dob, gender));
                NotificationController.SuccessfulTableNotification("Save", "Student");

            } catch (SQLException e) {
                NotificationController.WarringError("Save Student Warning", id + e.getMessage(), "Failed to save the Student ");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            *//*Update Rooms*//*
            try {
                if (!exitStudent(id)) {
                    NotificationController.WarringError("Update Student Warning", id, "There is no such Student associated with the ");
                }
                //Rooms update
                studentBO.updateStudent(new StudentDTO(id, name, address, cNO, dob, gender));
                NotificationController.SuccessfulTableNotification("Update", "Student");

            } catch (SQLException e) {
                NotificationController.WarringError("Update Student Warning", id + e.getMessage(), "Failed to update the Student ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();
            selectedItem.setStudent_id(id);
            selectedItem.setName(name);
            selectedItem.setAddress(address);
            selectedItem.setContact_no(cNO);
            selectedItem.setDob(dob);
            selectedItem.setGender(gender);
            tblStudent.refresh();
        }
        btnStudentAdd.fire();
    }

    @FXML
    void btnStudentDeleteOnAction(ActionEvent event) {

        String code = tblStudent.getSelectionModel().getSelectedItem().getStudent_id();;
        try {
            if (!exitStudent(code)){
                NotificationController.WarringError("Delete Student Warning", code, "There is no such Student associated with the ");
            }
            studentBO.deleteStudent(code);
            tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Student");
            tblStudent.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            NotificationController.WarringError("Delete Student Warning", code, "Failed to delete the Student ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllStudent(){
        tblStudent.getItems().clear();
        *//*Get all Student*//*
        try {
            ArrayList<StudentDTO> allStudent = studentBO.getAllStudent();
            for (StudentDTO studentDTO : allStudent){
                tblStudent.getItems().add(new StudentTM(studentDTO.getStudent_id(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact_no(), studentDTO.getDob(), studentDTO.getGender()));
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtStudentSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (txtStudentSearch.getText().trim().isEmpty()){
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllStudent();
        }else {
            if (exitStudent(txtStudentSearch.getText())) {
                tblStudent.getItems().clear();
                ArrayList<StudentDTO> arrayList = studentBO.searchAllStudent(txtStudentSearch.getText());
                if (arrayList != null){
                    for (StudentDTO studentDTO : arrayList){
                        tblStudent.getItems().add(new StudentTM(studentDTO.getStudent_id(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact_no(), studentDTO.getDob(), studentDTO.getGender()));
                    }
                }
            }else {
                tblStudent.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    @FXML
    void navigateToHome(MouseEvent event) throws SQLException, IOException {
        UILoader.NavigateToHome(manageStudentPane, "Reserve Form");
    }

    private void initUI() {
        txtStudentId.clear();
        txtStudentName.clear();
        txtStudentContactNo.clear();
        txtStudentAddress.clear();
        datePikerStudentDOB.getEditor().clear();
        combStudentGender.getSelectionModel().clearSelection();

        txtStudentId.setDisable(true);
        txtStudentName.setDisable(true);
        txtStudentContactNo.setDisable(true);
        txtStudentAddress.setDisable(true);
        datePikerStudentDOB.setDisable(true);
        combStudentGender.setDisable(true);

        btnStudentSave.setDisable(true);
       btnStudentDelete.setDisable(true);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        combStudentGender.getItems().addAll("Male", "Female", "Other");

        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("student_id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        initUI();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnStudentDelete.setDisable(newValue == null);
            btnStudentDelete.setText(newValue != null ? "Update" : "Save");
            btnStudentSave.setDisable(newValue == null);

            if (newValue != null){
                //------------------------Text Filed Load----------------------//
                txtStudentId.setText(newValue.getStudent_id());
                txtStudentName.setText(newValue.getName());
                txtStudentContactNo.setText(newValue.getContact_no());
                txtStudentAddress.setText(newValue.getAddress());
                datePikerStudentDOB.setValue(newValue.getDob());
                combStudentGender.setValue(newValue.getGender());

                txtStudentId.setDisable(false);
                txtStudentName.setDisable(false);
                txtStudentContactNo.setDisable(false);
                txtStudentAddress.setDisable(false);
                datePikerStudentDOB.setDisable(false);
                combStudentGender.setDisable(false);
            }
        });

        txtStudentAddress.setOnAction(event -> btnStudentSave.fire());
        loadAllStudent();
    }

    private boolean exitStudent(String id) throws SQLException, ClassNotFoundException {
        return studentBO.existStudentID(id);
    }




    @FXML
    void BackToDashboardOnAction(ActionEvent event) {
    }
    @FXML
    void btnStudentSearch(ActionEvent event) {
    }


    public void btnStudentUpdateOnAction(ActionEvent event) {
    }*/



}
