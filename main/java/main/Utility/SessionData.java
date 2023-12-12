package main.Utility;

import main.Models.Entities.Client;
import main.Models.Entities.Product;
import main.Models.Entities.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SessionData implements Serializable {
    private User user;
    private Client client;

    //для работы с заказом
    //загрузка в сессию набора продуктов для заказа после создание
    //записей заказа с продуктом и созданием записи в финальном заказе
    private List<Product> productList;
    public SessionData(){
        user = null;
        client = null;
        productList = new ArrayList<>();
    }
    public void addProduct(Product product)
    {
        this.productList.add(product);
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public SessionData(Client client) {
        this.client = client;
    }

    public SessionData(User user) {
        this.user = user;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
