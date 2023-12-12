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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Product;
import main.Models.Entities.Review;
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

import static main.Enums.RequestType.*;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;


public class ReviewCreate {
    public ChoiceBox<Integer> RateBox;
    public TextArea ReviewArea;
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

        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(5);
        ObservableList<Integer> boxList = FXCollections.observableArrayList(integerList);
        RateBox.setItems(boxList);


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

        SessionDataHolder holder = SessionDataHolder.getInstance();


        if (productlist!=null){
            productlist.addAll(holder.getSessionData().getProductList());
            infTable.setItems(productlist);
            infTable.refresh();
        }
        else {
            System.out.println("productlist is null");
        }


    }

    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ClientReviewChoose.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            //очистка массива продуктов
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


    public void Review_Pressed(ActionEvent actionEvent) {
        SessionDataHolder holder = SessionDataHolder.getInstance();
        Review review = new Review();
        review.setClient(holder.getSessionData().getClient());
        review.setProduct(holder.getSessionData().getProductList().get(0));
        review.setComment(ReviewArea.getText());
        review.setRate(RateBox.getValue());


        //Отправка данных
        Request requestModel = new Request();
        Type type = new TypeToken<Review>(){}.getType();
        requestModel.setRequestMessage(new Gson().toJson(review, type));
        requestModel.setRequestType(CLIENTREVIEW);
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
}
