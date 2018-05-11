package cmpe.sjsu.food4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    ListView cartListView;
    Button placeOrder;
    TextView totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView=(ListView)findViewById(R.id.cartListView);
        CartAdapter cartAdapter = new CartAdapter(getApplicationContext(),Cart.getInstance().cartItemList);
        cartListView.setAdapter(cartAdapter);
        totalPrice = findViewById(R.id.totalPrice);
        //set price on view
        Double tPrice=0.0;
        for(int i=0;i< Cart.getInstance().cartItemList.size();i++)
            tPrice += Cart.getInstance().cartItemList.get(i).getItem().getPrice()*Cart.getInstance().cartItemList.get(i).getQuantity();

        totalPrice.setText(tPrice.toString());

        placeOrder = (Button)findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change this
                 String orderPlacementTime = Calendar.getInstance().getTime().toString();
                String orderPickupTime = Calendar.getInstance().getTime().toString();
                Double totalPrice=0.0;
                for(int i=0;i< Cart.getInstance().cartItemList.size();i++)
                    totalPrice += Cart.getInstance().cartItemList.get(i).getItem().getPrice()*Cart.getInstance().cartItemList.get(i).getQuantity();
                Order order = new Order(Cart.getInstance().cartItemList,totalPrice,LoginContext.currentUser.getEmail(),orderPlacementTime,orderPickupTime);
                Database.getInstance().setNodeOrderDetails( "orders",order);

                Toast.makeText(getApplicationContext(), "Order placed Successfully", Toast.LENGTH_LONG).show();
                //move to next activity
                Cart.getInstance().emptyCart();
                Intent intent = new Intent(getApplicationContext(),UserRestaurantActivity.class);
                startActivity(intent);

            }
        });
    }
}
