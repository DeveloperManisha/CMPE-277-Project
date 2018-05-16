package cmpe.sjsu.food4u;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CartActivity extends Activity {
    DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
    ListView cartListView;
    Button placeOrder;
    TextView totalPrice;
    Button pickupDate;
    Button pickupTime;
    Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("DD-MMM-yyyy");
    String pickupTimeVal;
    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = (ListView) findViewById(R.id.cartListView);
        CartAdapter cartAdapter = new CartAdapter(getApplicationContext(), Cart.getInstance().cartItemList);
        cartListView.setAdapter(cartAdapter);
        totalPrice = findViewById(R.id.totalPrice);
        //set price on view
        Double tPrice = 0.0;
        for (int i = 0; i < Cart.getInstance().cartItemList.size(); i++)
            tPrice += Cart.getInstance().cartItemList.get(i).getItem().getPrice() * Cart.getInstance().cartItemList.get(i).getQuantity();

        totalPrice.setText(tPrice.toString());
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String strdate = null;

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                if (myCalendar != null) {
                    strdate = sdf.format(myCalendar.getTime());
                    flag = true;
                }
                Log.d("ADebugTag-->", strdate);
            }

        };

        final TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                pickupTimeVal = fmtDateAndTime.format(myCalendar.getTime());
                Log.d("ADebugTag-->", pickupTimeVal);
            }
        };

        pickupDate = (Button) findViewById(R.id.pickupDate);
        pickupDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new DatePickerDialog(CartActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        pickupTime = (Button) findViewById(R.id.pickupTime);
        pickupTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });
        pickupTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (flag.equals(true)) {
                    new TimePickerDialog(CartActivity.this,
                            t,
                            myCalendar.get(Calendar.HOUR_OF_DAY),
                            myCalendar.get(Calendar.MINUTE),
                            true).show();
                } else {
                    Toast.makeText(CartActivity.this, "Please select the date first!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        placeOrder = (Button) findViewById(R.id.placeOrder);

        placeOrder.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                if (flag.equals(true)) {
                    String orderPlacementTime = Calendar.getInstance().getTime().toString();

//                    String orderPickupTime = Calendar.getInstance().getTime().toString();

                    Double totalPrice = 0.0;
                    Double totalTime = 0.0;

                    for (int i = 0; i < Cart.getInstance().cartItemList.size(); i++) {
                        totalPrice += Cart.getInstance().cartItemList.get(i).getItem().getPrice() * Cart.getInstance().cartItemList.get(i).getQuantity();
                        totalTime += Cart.getInstance().cartItemList.get(i).getItem().getTime();
                    }
                    Order order = new Order(Cart.getInstance().cartItemList, totalPrice, LoginContext.currentUser.getEmail(), orderPlacementTime, pickupTimeVal, totalTime);

                    Database.getInstance().setNodeOrderDetails("orders", order);


                    Toast.makeText(getApplicationContext(), "Order placed Successfully", Toast.LENGTH_LONG).show();

                    Cart.getInstance().cartItemList.clear();
                    //move to next activity
                    Intent intent = new Intent(getApplicationContext(), UserRestaurantActivity.class);

                    startActivity(intent);
                }
            }

        });
    }
}
