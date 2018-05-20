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
        quantity.addTextChangedListener(new TextWatcher() {
            boolean _ignore = false;
            @Override
            public void afterTextChanged(Editable s) {
                if (_ignore)
                    return;
                _ignore = true; // prevent infinite loop
                // Change your text here.
              itemPriceVal.setText(s.toString());
                _ignore = false; // release, so the TextWatcher start to listen again.

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
               // currentItem.setQuantity(Integer.parseInt(currentItem.toString()));
               // Double price =  currentItem.getItem().getPrice()*currentItem.getQuantity();
                //itemPrice.setText(price.toString());
            //    Double price =  s.toString();
  //              itemPriceVal.setText(s.toString());
            }
        });

        return listItem;
    }
}