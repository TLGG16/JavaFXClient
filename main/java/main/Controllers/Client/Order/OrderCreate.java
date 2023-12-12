package main.Controllers.Client.Order;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Product;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;
import main.Utility.SessionData;
import main.Utility.SessionDataHolder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUPRODUCTS;
import static main.Enums.RequestType.CLIENTORDER;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

public class OrderCreate {
    public TableView<Product> infTable;
    public TableColumn<Product, Integer> IDCollumn;
    public TableColumn<Product, String> NameCollumn;
    public TableColumn<Product, String> CategoryCollumn;
    public TableColumn<Product, String> DescriptionCollumn;
    public TableColumn<Product, Float> PriceCollumn;
    public TableColumn<Product, Integer> AmountCollumn;
    public TableView<Product> UserTable;
    public TableColumn<Product, Integer> IDCollumnUser;
    public TableColumn<Product, String > NameCollumnUser;
    public TableColumn<Product, Float> PriceCollumnUser;
    public TextField IDField;
    public AnchorPane Pane;

    @FXML
    void initialize(){
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getId()));
        IDCollumnUser.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getId()));
        CategoryCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getCategory()));
        NameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getName()));
        NameCollumnUser.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getName()));
        DescriptionCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getDescription()));
        PriceCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPrice()));
        PriceCollumnUser.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPrice()));
        AmountCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getAmount()));

        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUPRODUCTS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();
        List<Product> products = gson.fromJson(message, type);
        ObservableList<Product> productlist = FXCollections.observableArrayList();
        if (productlist!=null){
            productlist.addAll(products);
            infTable.setItems(productlist);
            infTable.refresh();
        }
        else {
            System.out.println("productlist is null");
        }
    }

    public void AddProduct_Pressed(ActionEvent actionEvent) {
        ObservableList<Product> observableList = infTable.getItems();
        int i =0 ;

        for (Product prod:
              observableList) {
            if (prod.getId() == Integer.parseInt(IDField.getText())){
                Product product = new Product();
                product = (Product) infTable.getItems().get(i);

                SessionDataHolder holder = SessionDataHolder.getInstance();
                SessionData sessionData = holder.getSessionData();
                sessionData.addProduct(product);
                holder.setSessionData(sessionData);

                ObservableList<Product> productuserlist = FXCollections.observableArrayList();
                if (productuserlist!=null){
                    productuserlist.addAll(holder.getSessionData().getProductList());
                    UserTable.setItems(productuserlist);
                    UserTable.refresh();
                }

            }
            i++;
        }



    }


    public void MakeOrder_Pressed(ActionEvent actionEvent) {

        //Отправка данных
        Request requestModel = new Request();
        Type type = new TypeToken<SessionData>(){}.getType();
        SessionDataHolder holder = SessionDataHolder.getInstance();
        requestModel.setRequestMessage(new Gson().toJson(holder.getSessionData(), type));
        requestModel.setRequestType(CLIENTORDER);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Response response;
        Gson gson= new Gson();
        response = gson.fromJson(message, Response.class);
        if(response.qetResponseStatus() == OK){
            System.out.println("ОК");

            try {
                Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ClientMenu.fxml")));
                Scene scene = new Scene(blah, 800 , 600);
                Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

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
        else if(response.qetResponseStatus() == ERROR) {
            System.out.println("Ошибка");
        }
        else {
            System.out.println("Чтото непредвиденное");
        }
    }

    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ClientMenu.fxml")));
            Scene scene = new Scene(blah, 800 , 600);

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
}
