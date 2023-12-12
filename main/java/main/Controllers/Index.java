package main.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Index {
    public Button Adminbtn;
    public Button Accountantbtn;
    public Button Clientbtn;

    public void Admin_pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminLogin.fxml")));
            Scene scene = new Scene(blah, 800, 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void Accountant_pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AccountantLogin.fxml")));
            Scene scene = new Scene(blah, 800, 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void Client_pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ClientLogin.fxml")));
            Scene scene = new Scene(blah, 800, 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
