package com.sjsu.food4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button signup;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        signup.setOnClickListener(signupActionHandler);
        signin.setOnClickListener(signinActionHandler);
    }

    View.OnClickListener signupActionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    };
    View.OnClickListener signinActionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this,SigninActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    };
}
