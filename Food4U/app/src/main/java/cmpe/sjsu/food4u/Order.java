package cmpe.sjsu.food4u;

import java.util.ArrayList;

/**
 * Created by manas on 5/9/2018.
 */

public class Order {

    ArrayList<CartItem> cartItemsList;
    Double totalPrice;
    String useremail;
    String orderDate;
    String pickupDate;
    Double totalTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String orderId;
    String status;

    public Order(ArrayList<CartItem> cartItemsList, Double totalPrice, String useremail, String orderDate, String pickupDate, Double totalTime, String orderId, String status) {
        this.cartItemsList = cartItemsList;
        this.totalPrice = totalPrice;
        this.useremail = useremail;
        this.orderDate = orderDate;
        this.pickupDate = pickupDate;
        this.totalTime = totalTime;
        this.orderId = orderId;
        this.status = status;
    }

    public Order() {

    }


    public ArrayList<CartItem> getCartItemsList() {
        return cartItemsList;
    }

    public void setCartItemsList(ArrayList<CartItem> cartItemsList) {
        this.cartItemsList = cartItemsList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalPrice = totalTime;
    }

}
