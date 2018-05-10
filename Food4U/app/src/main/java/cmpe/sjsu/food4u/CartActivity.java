package cmpe.sjsu.food4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    ListView cartListView;
    Button placeOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView=(ListView)findViewById(R.id.cartListView);
        CartAdapter cartAdapter = new CartAdapter(getApplicationContext(),Cart.getInstance().cartItemList);
        cartListView.setAdapter(cartAdapter);

        placeOrder = (Button)findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change this
                Database.getInstance().setNodeOrderDetails( "orders",Cart.getInstance().cartItemList);

            }
        });
    }
}
