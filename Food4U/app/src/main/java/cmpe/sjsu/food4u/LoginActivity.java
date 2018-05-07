package cmpe.sjsu.food4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button admin;
    Button user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //AWSProvider.initialize(getApplicationContext());

        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);
        admin.setOnClickListener(adminActionHandler);
        user.setOnClickListener(userActionHandler);
    }

    View.OnClickListener adminActionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    };
    View.OnClickListener userActionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this,SigninActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    };
}
