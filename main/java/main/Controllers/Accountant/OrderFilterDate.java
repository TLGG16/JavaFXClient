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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import main.Models.Entities.FinalOrder;
import main.Models.TCP.Request;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.ACCOUNTANTVIEWFINALORDERS;

public class OrderFilterDate {
    public DatePicker DateFromField;
    public DatePicker DateToField;
    public TableView<FinalOrder> infTable;
    public TableColumn<FinalOrder, Integer> IDCollumn;
    public TableColumn<FinalOrder, Float> OrdersPriceCollumn;
    public TableColumn<FinalOrder, Float> DeliveryPriceCollumn;
    public TextArea AnswerArea;

    @FXML
    void initialize() {
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getId()));
        OrdersPriceCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getTotalPrice()));
        DeliveryPriceCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getDeliveryprice()));

    }


    public void Button_Pressed(ActionEvent actionEvent) {
        LocalDate date1 = DateFromField.getValue();
        LocalDate date2 = DateToField.getValue();

        Request requestModel = new Request();
        requestModel.setRequestType(ACCOUNTANTVIEWFINALORDERS);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<FinalOrder>>(){}.getType();
        List<FinalOrder> finalOrders = gson.fromJson(message, type);
        ObservableList<FinalOrder> observableList = FXCollections.observableArrayList();
        float totalprice = 0;
        float totaldeliveryprice = 0;
        for (FinalOrder finalorder: finalOrders
             ) {
            System.out.println(finalorder.getDate());
            LocalDate cmpdate;
            cmpdate = LocalDate.parse(finalorder.getDate());
            if ((date1.isBefore(cmpdate)==true) &
                    (date2.isAfter(cmpdate)==true)
            ){
                totaldeliveryprice+= finalorder.getDeliveryprice();
                totalprice+=finalorder.getTotalPrice();
                observableList.add(finalorder);
                System.out.println("+1");
            }
        }
        infTable.getItems().clear();
        if (!observableList.isEmpty()){
            infTable.setItems(observableList);
            infTable.refresh();

            AnswerArea.setText("Процент заработка от доставки за данный период составляет: " + (totaldeliveryprice/(totalprice + totaldeliveryprice))*100 + "%  ");
        }
        else {
            infTable.refresh();

            AnswerArea.setText("");
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
}
