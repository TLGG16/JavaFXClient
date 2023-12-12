package main.Controllers.Client.Review;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Product;
import main.Models.TCP.Request;
import main.Utility.Connect;
import main.Utility.SessionData;
import main.Utility.SessionDataHolder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUPRODUCTS;

public class ReviewChoose {
    public TextField IDField;
    public TableView<Product> infTable;
    public TableColumn<Product, Integer> IDCollumn;
    public TableColumn<Product, String> NameCollumn;
    public TableColumn<Product, String> CategoryCollumn;
    public TableColumn<Product, Float> PriceCollumn;
    public TableColumn<Product, Integer> AmountCollumn;
    public TableColumn<Product, String> SupplierNameCollumn;

    @FXML
    void initialize(){
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getId()));
        NameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getName()));
        CategoryCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getCategory()));
        PriceCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPrice()));
        AmountCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getAmount()));
        SupplierNameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getSupplier().getName()));



        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUPRODUCTS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();
        List<Product> suppliers = gson.fromJson(message, type);
        ObservableList<Product> productlist = FXCollections.observableArrayList();
        if (productlist!=null){
            productlist.addAll(suppliers);
            infTable.setItems(productlist);
            infTable.refresh();
        }
        else {
            System.out.println("productlist is null");
        }


    }

    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ClientMenu.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();



            SessionDataHolder holder = SessionDataHolder.getInstance();
            SessionData sessionData = holder.getSessionData();
            List<Product> products = new ArrayList<Product>();
            sessionData.setProductList(products);
            holder.setSessionData(sessionData);

            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void Choose_Pressed(ActionEvent actionEvent) {
        ObservableList<Product> observableList = infTable.getItems();
        int i =0 ;

        for (Product prod:
                observableList) {
            if (prod.getId() == Integer.parseInt(IDField.getText())){
                Product product = new Product();
                product = (Product) infTable.getItems().get(i);
                Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                SessionData sessionData = new SessionData();


                SessionDataHolder holder = SessionDataHolder.getInstance();
                sessionData = holder.getSessionData();
                sessionData.addProduct(product);
                holder.setSessionData(sessionData);

                try {
                    Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ClientReview.fxml")));
                    Scene scene = new Scene(blah);
                    appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    appStage.setScene(scene);
                    appStage.show();
                    break;
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            i++;

        }
    }
}
