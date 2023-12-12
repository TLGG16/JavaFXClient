package main.Controllers.Admin.Products;


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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Product;
import main.Models.Entities.Supplier;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUPRODUCTS;
import static main.Enums.RequestType.ADMINMENUSUPPLIERS;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

//TODO UPDATE продукта
public class Update {

    public TextField IDField;
    public Button Backbtn;
    public TextField NameField;
    public TextField CategoryField;
    public TextField DescriptionField;
    public TextField PriceField;
    public TextField AmountField;
    public TableView<Product> infTable;
    public TableColumn<Product, Integer> IDCollumn;
    public TableColumn<Product, String> NameCollumn;
    public TableColumn<Product, String> CategoryCollumn;
    public TableColumn<Product, String> DescriptionCollumn;
    public TableColumn<Product, Float> PriceCollumn;
    public TableColumn<Product, Integer> AmountCollumn;

    @FXML
    void initialize(){
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getId()));
        CategoryCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getCategory()));
        NameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getName()));
        DescriptionCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getDescription()));
        PriceCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPrice()));
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
            System.out.println("supplierlist is null");
        }


    }

    public void Update_Pressed(ActionEvent actionEvent) {
        Product product = new Product();

        product.setId(Integer.parseInt(IDField.getText()));
        product.setName(NameField.getText());
        product.setCategory(CategoryField.getText());
        product.setDescription(DescriptionField.getText());
        product.setPrice(Integer.parseInt(PriceField.getText()));
        product.setAmount(Integer.parseInt(AmountField.getText()));

        //Отправка данных
        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(product));
        requestModel.setRequestType(ADMINMENUSUPPLIERS);
        requestModel.setRequestCRUD(RequestCRUD.UPDATE);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Response response;
        Gson gson= new Gson();
        response = gson.fromJson(message, Response.class);
        if(response.qetResponseStatus() == OK){
            System.out.println("ОК");

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
        else if(response.qetResponseStatus() == ERROR) {
            System.out.println("Ошибка");
        }
        else {
            System.out.println("Чтото непредвиденное");
        }

    }

    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminMenuSuppliers.fxml")));
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
