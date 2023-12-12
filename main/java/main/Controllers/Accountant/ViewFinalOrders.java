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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.FinalOrder;
import main.Models.Entities.Order;
import main.Models.Entities.Product;
import main.Models.TCP.Request;
import main.Utility.Connect;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.*;

public class ViewFinalOrders {
    public TableView<FinalOrder> infTable;
    public TableColumn<FinalOrder, Integer> IDCollumn;
    public TableColumn<FinalOrder, Float> OrdersPriceCollumn;
    public TextField IDField;
    public TableColumn<FinalOrder, Float> DeliveryPriceCollumn;

    @FXML
    void initialize(){
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getId()));
        OrdersPriceCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getTotalPrice()) );
        DeliveryPriceCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getDeliveryprice()));

        Request requestModel = new Request();
        requestModel.setRequestType(ACCOUNTANTVIEWFINALORDERS);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<FinalOrder>>(){}.getType();
        List<FinalOrder> finalOrders = gson.fromJson(message, type);
        ObservableList<FinalOrder> observableList = FXCollections.observableArrayList();
        if (observableList!=null){
            observableList.addAll(finalOrders);
            infTable.setItems(observableList);
            infTable.refresh();
        }
        else {
            System.out.println("observableList is null");
        }


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

    public void Save_Pressed(ActionEvent actionEvent) throws IOException {
        try {
            Integer.parseInt(IDField.getText());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Вы ввели не число");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }


        Request requestModel = new Request();
        requestModel.setRequestType(ACCOUNTANTVIEWORDERS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Order>>(){}.getType();
        List<Order> orders = gson.fromJson(message, type);
//        Order order = orders.get(Integer.parseInt(IDField.getText()));
        List<Order> finalOrders = new ArrayList<Order>();
        int flag=0;
        for (Order order : orders
        ) {
            if (Integer.parseInt(IDField.getText())==order.getFinalOrder().getId()){
                flag=1;
            }
        }
        if (flag==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Такого ID нет");
            alert.setContentText("");
            alert.showAndWait();
            return;

        }

        String toWrite = "ID конечного заказа: " + IDField.getText();
        for (Order order : orders
             ) {
            if (Integer.parseInt(IDField.getText())==order.getFinalOrder().getId()){
                toWrite += "\n\nID заказа: " +order.getId() +
                        "\nДата заказа: "  + order.getFinalOrder().getDate()
                        + "\nНазвание товара: " + order.getProduct().getName() +
                        "\nЦена товара: " + order.getProduct().getPrice()
                        + "\nЦена товара: "+order.getFinalOrder().getDeliveryprice()
                        + "\nИмя заказчика: "+order.getClient().getPerson().getName()
                        + "\nФамилия заказчика: "+order.getClient().getPerson().getSurname()
                ;
            }
        }


        File file = new File("D:\\!Учеба\\КП (код)\\Client2\\src\\main\\java\\main\\check.txt");
        FileWriter fileWriter = new FileWriter(file);


        fileWriter.write(toWrite);
        fileWriter.close();

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
