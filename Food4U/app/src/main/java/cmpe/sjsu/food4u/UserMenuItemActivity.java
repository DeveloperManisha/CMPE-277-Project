package cmpe.sjsu.food4u;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserMenuItemActivity extends AppCompatActivity {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference dbReference;
    RecyclerView categoryMenu;
    LinearLayoutManager rLayoutManager;
    private String menuFilter;
    FirebaseRecyclerAdapter<FoodItem,MenuViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu_item);
        //set the filter string
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            menuFilter = intent.getStringExtra("filter");
        }
        else
            menuFilter = "dessert";

        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("MenuItems");

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        fetchAndDisplayMenuItems();

    }

    public void fetchAndDisplayMenuItems()
    {
        categoryMenu = (RecyclerView)findViewById(R.id.menuCategory);
        categoryMenu.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(this);
        categoryMenu.setLayoutManager(rLayoutManager);

        final Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("MenuItems")
                .limitToLast(50);

        //filter query
        Query filterQuery =  query.orderByChild("category")
                .equalTo(menuFilter);

        FirebaseRecyclerOptions<FoodItem> options =
                new FirebaseRecyclerOptions.Builder<FoodItem>()
                        .setQuery(filterQuery, FoodItem.class)
                        .build();
        adapter =  new FirebaseRecyclerAdapter<FoodItem, MenuViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull FoodItem model) {

                String path = "images/"+model.getPicture();
                StorageReference pathReference = storageReference.child(path);
                setDownloadUrl(path);
                GlideApp.with(getApplicationContext())
                        .load(pathReference).centerCrop()
                        .into(holder.menuCategoryImage);

                String textString = model.getName()+"\n\n"+"Calories :" + model.getCalories()+
                        "\n\n"+"Price : "+model.getPrice();

                System.out.println("User menu description:"+textString);
                holder.menuCategoryName.setText(textString);
               // holder.menuCategoryName.setLongClickable(true);
               // holder.menuCategoryName.setClickable(true);
                //holder.menuCategoryImage.setLongClickable(true);
               // holder.menuCategoryImage.setClickable(true);
                final FoodItem selectedFoodItem = model;
                holder.setMenuItemOnClickListener(new MenuCategoryClickListener() {
                                                      @Override
                                                      public void onClick(View v, int posistion, boolean flag) {

                                                          String msg = selectedFoodItem.getName()+" has been added to cart";
                                                          Toast.makeText(getApplicationContext(), selectedFoodItem.getName(),Toast.LENGTH_LONG).show();
                                                          CartItem item = new CartItem(selectedFoodItem,1);
                                                          Cart.getInstance().addToCart(item);
                                                      }

                                                      @Override
                                                      public void onLongClick(View v, int posistion, boolean flag) {


                                                      }
                                                  }
                );
            }

            @Override
            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_item, parent, false);

                return new MenuViewHolder(view);
            }
            @Override
            public void onDataChanged() {
            }
        };
        categoryMenu.setAdapter(adapter);

    }

    private void setImageUrl(String s){
        this.foodItemImageURL = s;
    }
    private String foodItemImageURL;

    public void setDownloadUrl(String path){
        String imageUrl;
        StorageReference pathReference = storageReference.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                setImageUrl(uri.toString());
                System.out.println(uri.toString());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {

            }
        });
    }
    public void fireBaseAnonymousSigninSetup()
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

        } else {
            mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                @Override public void onSuccess(AuthResult authResult) {

                }
            }) .addOnFailureListener(this, new OnFailureListener() {
                @Override public void onFailure( Exception exception) {

                }
            });
        }
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
