package cmpe.sjsu.food4u;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText category;
    private EditText picture;
    private EditText price;
    private EditText calories;
    private EditText time;
    private Button add;
    private Button uploadImage;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri menuItemPicURl;
    private String imageid;
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

                if(menuItemPicURl !=null)
                    uploadPictureToFirebase();
                else
                    imageid="";
                // ToDo set dynamic values
                FoodItem f = new FoodItem("Dessert","Gulabjamun",imageid,10.00,100,30);
                dbReference.push().setValue(f);
                Intent intent = new Intent(AddFoodItemActivity.this,RestaurantActivity.class);
                AddFoodItemActivity.this.startActivity(intent);
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
