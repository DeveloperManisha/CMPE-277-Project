package cmpe.sjsu.food4u;

import android.content.Intent;
import android.os.Bundle;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RestaurantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference dbReference;
    RecyclerView categoryMenu;
    LinearLayoutManager rLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("Menu");
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
       /* dbReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s){

                FoodItem value = dataSnapshot.getValue(FoodItem.class);
                System.out.println("Database is connected:"+value.getPicture());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

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
            // Handle the camera action
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

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("MenuItems")
                .limitToLast(50);


        FirebaseRecyclerAdapter<FoodItem,MenuViewHolder> adapter =  new FirebaseRecyclerAdapter<FoodItem, MenuViewHolder>(FoodItem.class,R.layout.menu_category, MenuViewHolder.class,query) {

            @Override
            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_category, parent, false);

                return new MenuViewHolder(view);
            }
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, FoodItem model, int position) {
                System.out.println("************tried to populate ***************");
                System.out.println("Hello---"+model.getPicture()+"_________"+model.getCategory());
                viewHolder.menuCategoryImage.setText(model.getPicture());
                viewHolder.menuCategoryName.setText(model.getCategory());
                final FoodItem selectedFoodItem = model;
                viewHolder.setMenuItemOnClickListener(new MenuCategoryClickListener() {
                                                          @Override
                                                          public void onClick(View v, int posistion, boolean flag) {
                                                              Toast.makeText(RestaurantActivity.this, selectedFoodItem.getCategory(),Toast.LENGTH_LONG).show();
                                                          }
                                                      }
                );

            }

        };
        categoryMenu.setAdapter(adapter);

    }

}