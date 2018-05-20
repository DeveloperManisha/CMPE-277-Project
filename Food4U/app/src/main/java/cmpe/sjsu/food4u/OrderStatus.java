package cmpe.sjsu.food4u;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.google.android.gms.common.internal.service.Common;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.lang.String;
import cmpe.sjsu.food4u.Request;
import cmpe.sjsu.food4u.Order;
import cmpe.sjsu.food4u.Common;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Order,OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("orders");



        loadOrders(LoginContext.currentUser.getEmail());
        Log.d("A Debug Tag", "Value user: " + LoginContext.currentUser.getEmail());

    }

    public void loadOrders(String email) {

        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("orders")
                .equalTo(email);

        Log.d("A Debug Tag", "Value q: " + query);

        //filter query
        Query filterQuery =  query.orderByChild("useremail");
        Log.d("A Debug Tag", "Value fQ: " + filterQuery);

        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(filterQuery, Order.class)
                        .build();


        Log.d("A Debug Tag", "Value o: " + options);

//        adapter = new FirebaseRecyclerAdapter<Order,OrderViewHolder>(
//                Order.class,
//                R.layout.order_layout,
//                OrderViewHolder.class,
//                requests.orderByChild("useremail")
//                        .equalTo(email) ) {
//
//

        adapter =  new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options){

            @Override
            protected void onBindViewHolder( OrderViewHolder viewHolder, int position, Order model) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                Log.d("A Debug Tag", "Value bind: ");
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderEmail.setText(model.getUseremail());

            }
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_layout, parent, false);

                Log.d("A Debug Tag", "Value create: " + view);

                return new OrderViewHolder(view);
                //return null;
            }


            protected void populateViewHolder(OrderViewHolder viewHolder, Order model, int position) {
            }
        };

        recyclerView.setAdapter(adapter);
        Log.d("A Debug Tag", "Value a: " + adapter);
    }

    private String convertCodeToStatus(String status) {
        Log.d("A Debug Tag", "Value s: " + status);

        if (status.equals("0"))
            return "placed";
        else if (status.equals("1"))
            return "queued";
        else if (status.equals("2"))
            return "being-prepared";
        else if (status.equals("3"))
            return "fulfilled";
        else
            return "abandoned";
    }

}