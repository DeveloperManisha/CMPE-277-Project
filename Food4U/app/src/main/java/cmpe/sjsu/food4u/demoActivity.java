package cmpe.sjsu.food4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amazonaws.mobile.auth.core.IdentityManager;

public class demoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                IdentityManager.getDefaultIdentityManager().signOut();
                Intent intent = new Intent(demoActivity.this,UserActivity.class);
                demoActivity.this.startActivity(intent);
            }
        });
    }
}
