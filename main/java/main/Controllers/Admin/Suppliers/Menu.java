package main.Controllers.Admin.Suppliers;

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
import main.Models.Entities.Supplier;
import main.Models.TCP.Request;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;


import static main.Enums.RequestType.ADMINMENUSUPPLIERS;

public class Menu {
    public Button CreateSupplierbtn;
    public Button ShowSuppliersbtn;
    public Button DeleteSupplierbtn;
    public Button UpdateSupplierbtn;
    public Button Backbtn;

    public void CreateSupplier_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminCreateSupplier.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void ShowSuppliers_Pressed(ActionEvent actionEvent) {
        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUSUPPLIERS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Supplier>>(){}.getType();
        ListView<Supplier> listView = new ListView<>();
        List<Supplier> suppliers = gson.fromJson(message, type);
        for (int i =0; i<suppliers.size(); i++){
            listView.getItems().add(i, suppliers.get(i));

        }
        VBox vBox = new VBox(listView);
        Scene scene = new Scene(vBox, 800,600);
        Stage stage = new Stage();
        stage.setTitle("Все поставщики");
        stage.setScene(scene);
        stage.showAndWait();

    }

    public void DeletSupplier_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminDeleteSupplier.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void UpdateSupplier_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminUpdateSupplier.fxml")));
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
