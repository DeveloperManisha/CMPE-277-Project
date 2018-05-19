package cmpe.sjsu.food4u;

import java.util.List;

public class Request {

    private String email;
    private String total;
    private String status;
    private List<Order> foods;

    public Request(){
        }

    public Request(String email,String total, String status, List<Order>foods){
        this.email=email;
        this.total=total;
        this.status="0"; //default 0, 0 placed, 1 shipping, 2 shipped
        this.foods=foods;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
