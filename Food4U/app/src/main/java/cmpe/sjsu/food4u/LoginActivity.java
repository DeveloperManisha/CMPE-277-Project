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
        //Intent intent = new Intent(LoginActivity.this,UserActivity.class);
        //LoginActivity.this.startActivity(intent);

        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);
        admin.setOnClickListener(adminActionHandler);
        user.setOnClickListener(userActionHandler);
    }

    View.OnClickListener adminActionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    };
    View.OnClickListener userActionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this,UserActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    };
}
