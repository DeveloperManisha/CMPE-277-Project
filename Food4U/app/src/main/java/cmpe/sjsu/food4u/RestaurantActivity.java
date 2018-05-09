package cmpe.sjsu.food4u;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class RestaurantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference dbReference;
    RecyclerView categoryMenu;
    LinearLayoutManager rLayoutManager;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseRecyclerAdapter<FoodItem,MenuViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fireBaseAnonymousSigninSetup();
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("MenuItems");

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(floatingButtonListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(dbReference !=null) {
            System.out.println("***********Database is connected");
        }
        fetchAndDisplayMenuItems();

    }
    View.OnClickListener floatingButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(RestaurantActivity.this,AddFoodItemActivity.class);
            RestaurantActivity.this.startActivity(intent);

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


        FirebaseRecyclerOptions<FoodItem> options =
                new FirebaseRecyclerOptions.Builder<FoodItem>()
                        .setQuery(query, FoodItem.class)
                        .build();
        adapter =  new FirebaseRecyclerAdapter<FoodItem, MenuViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull FoodItem model) {

                String path = "images/"+model.getPicture();
                StorageReference pathReference = storageReference.child(path);
                setDownloadUrl(path);
                GlideApp.with(RestaurantActivity.this)
                        .load(pathReference).centerCrop()
                        .into(holder.menuCategoryImage);

                holder.menuCategoryName.setText(model.getName());
                holder.menuCategoryName.setLongClickable(true);
                holder.menuCategoryName.setClickable(true);
                final FoodItem selectedFoodItem = model;
                holder.menuCategoryName.setText( model.getName());
                holder.setMenuItemOnClickListener(new MenuCategoryClickListener() {
                                                          @Override
                                                          public void onClick(View v, int posistion, boolean flag) {

                                                              Toast.makeText(RestaurantActivity.this, selectedFoodItem.getCategory(),Toast.LENGTH_LONG).show();
                                                          }

                                                          @Override
                                                          public void onLongClick(View v, int posistion, boolean flag) {

                                                              Toast.makeText(RestaurantActivity.this, "delete",Toast.LENGTH_LONG).show();
                                                              query.orderByChild("name")
                                                                      .equalTo(selectedFoodItem.getName())
                                                                      .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              if (dataSnapshot.hasChildren()) {
                                                                                  DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                                                                  firstChild.getRef().removeValue();
                                                                              }
                                                                          }

                                                                          public void onCancelled(DatabaseError firebaseError) {
                                                                          }
                                                                      });
                                                          }
                                                      }
                );
            }

            @Override
            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_category, parent, false);

                return new MenuViewHolder(view);
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
                //imageUrl = uri.toString();
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

