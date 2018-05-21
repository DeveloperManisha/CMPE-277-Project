package cmpe.sjsu.food4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SystemReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_report);

        Button orderreport= findViewById(R.id.getOrderReport);
        orderreport.setOnClickListener(generateOrderReports);
        Button popularityreport= findViewById(R.id.getPopularity);
        orderreport.setOnClickListener(generatePopularityReports);


    }
    View.OnClickListener generateOrderReports = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),StatusReportActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener generatePopularityReports = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),StatusReportActivity.class);
            startActivity(intent);
        }
    };
}
