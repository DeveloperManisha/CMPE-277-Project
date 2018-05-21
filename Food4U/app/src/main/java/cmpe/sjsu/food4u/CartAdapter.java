package cmpe.sjsu.food4u;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manas on 5/9/2018.
 */

public class CartAdapter extends ArrayAdapter<CartItem> {

    private Context mContext;
    private List<CartItem> cartList = new ArrayList<>();
    private  CartItem currentItem;
    private  TextView  itemName;
    private  TextView  itemPriceVal;
    private EditText quantity;
    View listItem;
    public CartAdapter( Context context, ArrayList<CartItem> list) {
        super(context, 0 , list);
        mContext = context;
        cartList = list;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.cart_list_view,parent,false);

        currentItem = cartList.get(position);

        itemName = (TextView)listItem.findViewById(R.id.itemName);
        itemName.setText(currentItem.getItem().getName());

        itemPriceVal = (TextView)listItem.findViewById(R.id.itemPrice);
        Double price =  currentItem.getItem().getPrice()*currentItem.getQuantity();
        itemPriceVal.setText(price.toString());


        quantity = (EditText) listItem.findViewById(R.id.itemQuantity);
        quantity.setText(currentItem.getQuantity().toString());
        return listItem;
    }
}