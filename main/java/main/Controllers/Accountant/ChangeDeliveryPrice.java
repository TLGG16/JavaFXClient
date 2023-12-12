package main.Controllers.Accountant;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

import static main.Enums.RequestType.CLIENTORDER;
import static main.Enums.RequestType.DELIVERYPRICE;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

public class ChangeDeliveryPrice {
    public TextField PriceField;

    public void Delete_Pressed(ActionEvent actionEvent) {
        //Отправка данных
        Request requestModel = new Request();
        Type type = new TypeToken<Integer>(){}.getType();
        requestModel.setRequestMessage(PriceField.getText());
        requestModel.setRequestType(DELIVERYPRICE);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Response response;
        Gson gson= new Gson();
        response = gson.fromJson(message, Response.class);
        if(response.qetResponseStatus() == OK){
            System.out.println("ОК");

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
        else if(response.qetResponseStatus() == ERROR) {
            System.out.println("Ошибка");
        }
        else {
            System.out.println("Чтото непредвиденное");
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
