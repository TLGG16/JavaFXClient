package main.Controllers.Admin.Accounts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Client;
import main.Models.Entities.User;
import main.Models.TCP.Request;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.*;

public class Menu {
    public Button AdminCreatebtn;
    public Button ShowAdminsbtn;
    public Button DeleteAdminbtn;
    public Button CreateAccountantbtn;
    public Button ShowAccountantsbtn;
    public Button DeleteClientbtn;
    public Button ShowClientsbtn;
    public Button DeleteAccountantsbtn;
    public Button CreateClientbtn;
    public Button UpdateAdminbtn;
    public Button UpdateAccountantbtn;
    public Button UpdateClientbtn;

    public void AdminCreatebtn_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminCreateAdmin.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void ShowAdminsbtn_Pressed(ActionEvent actionEvent) {
        //Отправка данных
        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUACCOUNTSADMINS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<User>>(){}.getType();
        ListView<User> listView = new ListView<>();
        List<User> users = gson.fromJson(message, type);
        int j =0;
        for (int i =0; i<users.size(); i++){
            if (users.get(i).getRole().toLowerCase().equals("admin")) {
                listView.getItems().add(j, users.get(i));
                j++;
            }
        }
        VBox vBox = new VBox(listView);
        Scene scene = new Scene(vBox, 800,600);
        Stage stage = new Stage();
        stage.setTitle("Все администраторы");
        stage.setScene(scene);
        stage.showAndWait();
    }


    public void DeleteAdminbtn_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminDeleteAdmin.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void CreateAccountantbtn_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminCreateAccountant.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void ShowAccountantsbtn_Pressed(ActionEvent actionEvent) {
        //Отправка данных
        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUACCOUNTSACCOUNTANTS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<User>>(){}.getType();
        ListView<User> listView = new ListView<>();
        List<User> users = gson.fromJson(message, type);
        int j =0;
        for (int i =0; i<users.size(); i++){
            if (users.get(i).getRole().toLowerCase().equals("accountant")) {
                listView.getItems().add(j, users.get(i));
                j++;
            }
        }
        VBox vBox = new VBox(listView);
        Scene scene = new Scene(vBox, 800,600);
        Stage stage = new Stage();
        stage.setTitle("Все бухгалтеры");
        stage.setScene(scene);
        stage.showAndWait();
    }


    public void DeleteAccountantsbtn_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminDeleteAccountant.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void CreateClientbtn_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminCreateClient.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void ShowClientsbtn_Pressed(ActionEvent actionEvent) {
        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUACCOUNTSCLIENTS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Client>>(){}.getType();
        ListView<Client> listView = new ListView<>();
        List<Client> clients = gson.fromJson(message, type);
        for (int i =0; i<clients.size(); i++){
            listView.getItems().add(i, clients.get(i));

        }
        VBox vBox = new VBox(listView);
        Scene scene = new Scene(vBox, 800,600);
        Stage stage = new Stage();
        stage.setTitle("Все пользователи");
        stage.setScene(scene);
        stage.showAndWait();
    }


    public void DeleteClientbtn_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminDeleteClient.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void UpdateAdmin_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminUpdateAdmin.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void UpdateAccountant_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminUpdateAccountant.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void UpdateClient_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminUpdateClient.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminMenu.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
