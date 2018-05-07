package cmpe.sjsu.food4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Intent intent = new Intent(SignupActivity.this,RestaurantActivity.class);
        SignupActivity.this.startActivity(intent);
    }
}
