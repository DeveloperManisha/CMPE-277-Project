package cmpe.sjsu.food4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFoodItemActivity extends AppCompatActivity {

    private EditText category;
    private EditText picture;
    private EditText price;
    private EditText calories;
    private EditText time;
    private Button add;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);
        category = (EditText)findViewById(R.id.category);
        picture = (EditText)findViewById(R.id.picture);
        price = (EditText)findViewById(R.id.unitPrice);
        calories=(EditText)findViewById(R.id.calories);
        time=(EditText)findViewById(R.id.time);
        add = findViewById(R.id.addFoodItem);

        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("MenuItems");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ToDo set dynamic values
                FoodItem f = new FoodItem("Dessert","Gulabjamun",10.00,100,30);
                dbReference.push().setValue(f);
                Intent intent = new Intent(AddFoodItemActivity.this,RestaurantActivity.class);
                AddFoodItemActivity.this.startActivity(intent);
            }
        });
    }
}
