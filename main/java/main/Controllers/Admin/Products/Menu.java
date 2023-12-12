package main.Controllers.Admin.Products;

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
import main.Models.Entities.Product;
import main.Models.Entities.Supplier;
import main.Models.TCP.Request;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUPRODUCTS;
import static main.Enums.RequestType.ADMINMENUSUPPLIERS;

public class Menu {
    public Button CreateProductbtn;
    public Button ShowProductbtn;
    public Button DeleteProductbtn;
    public Button UpdateProductbtn;
    public Button Backbtn;

    public void Createproduct_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminCreateProduct.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void ShowProducts_Pressed(ActionEvent actionEvent) {
        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUPRODUCTS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();
        ListView<Product> listView = new ListView<>();
        List<Product> products = gson.fromJson(message, type);
        for (int i =0; i<products.size(); i++){
            listView.getItems().add(i, products.get(i));

        }
        VBox vBox = new VBox(listView);
        Scene scene = new Scene(vBox, 800,600);
        Stage stage = new Stage();
        stage.setTitle("Все товары");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void DeleteProduct_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminDeleteProduct.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void UpdateProduct_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminUpdateProduct.fxml")));
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
