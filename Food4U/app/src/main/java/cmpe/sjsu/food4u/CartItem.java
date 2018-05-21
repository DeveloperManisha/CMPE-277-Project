package cmpe.sjsu.food4u;

/**
 * Created by manas on 5/9/2018.
 */

public class CartItem {

    FoodItem item;
    Integer quantity;

    public CartItem(){

    }
    public CartItem(FoodItem item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public FoodItem getItem() {
        return item;
    }

    public void setItem(FoodItem item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
