package lk.ijse.hostelManagement.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {

    public static void swatchNavigation(String link, ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Navigation.class.getResource("/lk/ijse/hostelManagement/view/" + link));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

}
