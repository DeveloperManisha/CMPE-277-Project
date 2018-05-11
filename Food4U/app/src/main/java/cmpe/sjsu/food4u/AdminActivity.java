package cmpe.sjsu.food4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText)findViewById(R.id.username)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();
                System.out.println("Admin Username Password"+username+" "+password);
                if(username.contentEquals("a") && password.contentEquals("a"))
                {
                    LoginContext.user = new User(username,true);
                    Intent intent = new Intent(AdminActivity.this,RestaurantActivity.class);
                    AdminActivity.this.startActivity(intent);
                }
                else
                    Toast.makeText(AdminActivity.this, "Invalid user name/password",Toast.LENGTH_SHORT).show();
            }
        });

        //Sample db invoke
        //when u pass object, id for object is automatically created
        //Database.getInstance().setNodeOrderDetails("testNode","order2");

    }
}
