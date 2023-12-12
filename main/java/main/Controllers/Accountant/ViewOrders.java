package main.Controllers.Accountant;


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
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Order;
import main.Models.Entities.Product;
import main.Models.TCP.Request;
import main.Utility.Connect;
import main.Utility.SessionData;
import main.Utility.SessionDataHolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static main.Enums.RequestType.ACCOUNTANTVIEWORDERS;
import static main.Enums.RequestType.ADMINMENUPRODUCTS;

//TODO ViewOrders
//TODO график и сохранение данных в файл
public class ViewOrders {
    public TableView<Order> infTable;
    public TableColumn<Order, Integer> IDCollumn;
    public TableColumn<Order, String> NameCollumn;
    public TableColumn<Order, Float> PriceCollumn;
    public TableColumn<Order, Integer> SoldAmountCollumn;
    public TableColumn<Order, String> SupplierNameCollumn;
    public PieChart ProductChart;


    @FXML
    void initialize(){
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getProduct().getId()));
        NameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getProduct().getName()));
        SupplierNameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getProduct().getName()));
        PriceCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getProduct().getPrice()));
        SoldAmountCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getProduct().getAmount()));


        Request requestModel = new Request();
        requestModel.setRequestType(ACCOUNTANTVIEWORDERS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Order>>(){}.getType();
        List<Order> orders = gson.fromJson(message, type);
        List<Order> sortedOrders = new ArrayList<Order>();
        for (Order order: orders
             ) {
            int i=1;
            boolean flag = false;

            for (Order sortedOrder : sortedOrders
                 ) {
                if(order.getProduct().getName().equals(sortedOrder.getProduct().getName()) &&
                        order.getProduct().getCategory().equals(sortedOrder.getProduct().getCategory()) &&
                        order.getProduct().getPrice() == sortedOrder.getProduct().getPrice() &&
                        order.getProduct().getSupplier().getName().equals(sortedOrder.getProduct().getSupplier().getName())

                ){
                    flag =true;
                    sortedOrder.getProduct().setAmount(sortedOrder.getProduct().getAmount()+1);
                    break;
                }
            }
            if (flag==false){
                order.getProduct().setAmount(1);
                sortedOrders.add(order);

            }

        }

        ObservableList<Order> observableList = FXCollections.observableArrayList();

        if (observableList!=null){
            observableList.addAll(sortedOrders);
            infTable.setItems(observableList);
            infTable.refresh();
        }
        else {
            System.out.println("observableList is null");
        }

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Order order: sortedOrders
             ) {
            pieData.add(new PieChart.Data(order.getProduct().getName(),order.getProduct().getAmount()));
        }
        ProductChart.setData(pieData);

    }

    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AccountantMenu.fxml")));
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
