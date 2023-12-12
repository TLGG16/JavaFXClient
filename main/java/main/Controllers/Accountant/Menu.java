package main.Controllers.Accountant;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Menu {


    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("index.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void Report_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AccountantReport.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void OrderView_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AccountantViewOrders.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void FinalOrderView_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AccountantViewFinalOrders.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void DelPrice_Pressed(ActionEvent actionEvent){
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AccountantDeliveryPrice.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void OrderFilterDate_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AccountantOrderFilterDate.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
