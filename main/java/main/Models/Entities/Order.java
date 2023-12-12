package main.Models.Entities;



import java.io.Serializable;
import java.util.Date;


public class Order implements Serializable {

    private int id;


    private Client client;

    private Product product;

    private FinalOrder finalOrder;





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FinalOrder getFinalOrder() {
        return finalOrder;
    }

    public void setFinalOrder(FinalOrder finalOrder) {
        this.finalOrder = finalOrder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", product=" + product +
                '}';
    }
}
