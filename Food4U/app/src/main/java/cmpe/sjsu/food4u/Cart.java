package cmpe.sjsu.food4u;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manas on 5/9/2018.
 */

public class Cart {
    public ArrayList<CartItem> cartItemList;
    public static Cart cart;
    private Cart()
    {
        cartItemList = new ArrayList<CartItem>();
    }
    public static Cart getInstance(){
        if(cart == null)
            cart = new Cart();
        return cart;
    }
    public void addToCart(CartItem item){

        cartItemList.add(item);
    }
    public Integer size(){
        return cartItemList.size();

    }
    public CartItem get(Integer index ){
         if(cartItemList.size()>0 && (cartItemList.size()>= index))
            return cartItemList.get(index);
         else
            return null;
    }
}
