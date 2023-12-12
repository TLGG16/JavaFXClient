package main.Models.Entities;


public class FinalOrder {

    private int id;

    private float totalPrice;
    private float deliveryprice;

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public float getDeliveryprice() {
        return deliveryprice;
    }

    public void setDeliveryprice(float deliveryprice) {
        this.deliveryprice = deliveryprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
