package cmpe.sjsu.food4u;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddFoodItemActivity extends AppCompatActivity {

    private TextView picture;
    private EditText price;
    private EditText calories;
    private EditText time;
    private EditText name;
    private Button add;
    private Button uploadImage;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri menuItemPicURl;
    private String imageid;
    private String spinnerContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);
        picture = (TextView) findViewById(R.id.picture);
        price = (EditText)findViewById(R.id.unitPrice);
        calories=(EditText)findViewById(R.id.calories);
        time=(EditText)findViewById(R.id.preparationtime);
        name=(EditText)findViewById(R.id.name);
        add = findViewById(R.id.addFoodItem);


        //get the spinner from the xml.
        Spinner category = findViewById(R.id.category);
        //create a list of items for the spinner.
        final String[] items = new String[]{"drink", "appetizer", "maincourse","dessert"};



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        category.setAdapter(adapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        spinnerContent = items[0];
                        break;
                    case 1:
                        spinnerContent = items[1];
                        break;
                    case 2:
                        spinnerContent = items[2];
                        break;
                    case 3:
                        spinnerContent = items[3];
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("MenuItems");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(menuItemPicURl !=null)
                    uploadPictureToFirebase();
                else
                    imageid="";


            }
        });
        uploadImage = findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
    }
    public void selectPicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 71);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 71 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            menuItemPicURl = data.getData();
            picture.setText("Image Selected");
            System.out.println("********"+menuItemPicURl+"**********");
        }
    }
    private void uploadPictureToFirebase() {

        if(menuItemPicURl != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            imageid= UUID.randomUUID().toString();
            StorageReference ref = storageReference.child("images/"+imageid);
            ref.putFile(menuItemPicURl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(AddFoodItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            FoodItem f = new FoodItem(spinnerContent,name.getText().toString(),imageid,Double.parseDouble(price.getText().toString()),Integer.parseInt(calories.getText().toString()),Integer.parseInt(time.getText().toString()),1);

                            dbReference.push().setValue(f);
                            Intent intent = new Intent(AddFoodItemActivity.this,RestaurantActivity.class);
                            AddFoodItemActivity.this.startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddFoodItemActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}

