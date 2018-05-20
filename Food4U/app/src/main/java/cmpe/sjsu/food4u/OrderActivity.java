package cmpe.sjsu.food4u;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OrderActivity extends AppCompatActivity {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference dbReference;
    RecyclerView orderRecyclerView;
    LinearLayoutManager rLayoutManager;
    FirebaseRecyclerAdapter<Order,OrdersViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("orders");

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        fetchAndDisplayOrdertems();
    }
    public void fetchAndDisplayOrdertems(){
        orderRecyclerView = (RecyclerView)findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(this);
        orderRecyclerView.setLayoutManager(rLayoutManager);

        final Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("orders")
                .limitToLast(50);

        //filter query
        Query filterQuery =  query.orderByChild("useremail")
                .equalTo(LoginContext.currentUser.getEmail());

        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(filterQuery, Order.class)
                        .build();
        adapter =  new FirebaseRecyclerAdapter<Order, OrdersViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull OrdersViewHolder holder, final int position, @NonNull Order model) {

                String orderString = "Order Id #"+model.getOrderId();
                holder.orderIdView.setText(orderString);
                System.out.println("model order date"+ model.getOrderDate());
                final Order selectedOrderItem = model;
                holder.setMenuItemOnClickListener(new MenuCategoryClickListener() {
                                                      @Override
                                                      public void onClick(View v, int posistion, boolean flag) {

                                                          adapter.getRef(position).removeValue();
                                                          String msg = selectedOrderItem.getOrderId()+" has been canceled";
                                                          Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_LONG).show();

                                                      }

                                                      @Override
                                                      public void onLongClick(View v, int posistion, boolean flag) {


                                                      }
                                                  }
                );

            }
            @Override
            public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_item, parent, false);

                return new OrdersViewHolder(view);
            }
            @Override
            public void onDataChanged() {

            }


        };


        orderRecyclerView.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
