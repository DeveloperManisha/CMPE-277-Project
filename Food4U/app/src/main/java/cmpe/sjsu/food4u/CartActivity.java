package cmpe.sjsu.food4u;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Random;

public class CartActivity extends AppCompatActivity {
    DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
    ListView cartListView;
    Button placeOrder;
    TextView totalPrice;
    Button pickupDate;
    Button pickupTime;
    Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("DD-MMM-yyyy");
    String pickupTimeVal = null;
    Boolean flag = false;
    final long sevenDays = 1000*60*60*24*7;
    Boolean orderCancel = false;

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
                long now = System.currentTimeMillis() - 1000;
                long time2 =   myCalendar.getTimeInMillis();

                long diff = time2 - now;
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                if (myCalendar != null) {
                    if(diff>0 && diff < sevenDays){
                        strdate = sdf.format(myCalendar.getTime());
                        flag = true;
                        pickupTime.setVisibility(View.VISIBLE);
                        Log.d("ADebugTag-->", strdate);
                    }
                    else{
                        Toast.makeText(CartActivity.this, "Please select within the next 7 days!!",
                                Toast.LENGTH_LONG).show();
                    }
                }
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

        final TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                if(hourOfDay > 6 && hourOfDay < 21) {
                    pickupTimeVal = fmtDateAndTime.format(myCalendar.getTime());
                    Log.d("ADebugTag-->", pickupTimeVal);
                }
                else{
                    Toast.makeText(CartActivity.this, "Please select within the office hours of 6am to 9pm!!",
                            Toast.LENGTH_LONG).show();
                }
            }
        };


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

                if (flag.equals(true) && pickupTimeVal != null) {
                    String orderPlacementTime = Calendar.getInstance().getTime().toString();

//                    String orderPickupTime = Calendar.getInstance().getTime().toString();

                    Double totalPrice = 0.0;
                    Double totalTime = 0.0;
                    Integer pop = 0;

                    for (int i = 0; i < Cart.getInstance().cartItemList.size(); i++) {
                        totalPrice += Cart.getInstance().cartItemList.get(i).getItem().getPrice() * Cart.getInstance().cartItemList.get(i).getQuantity();
                        if(Cart.getInstance().cartItemList.get(i).getQuantity()>50);
                            orderCancel=true;
                        totalTime += Cart.getInstance().cartItemList.get(i).getItem().getTime();
                      //  pop = Cart.getInstance().cartItemList.get(i).getItem().getPopularity();
                     //   FoodItem f = new FoodItem(category.getText().toString(),name.getText().toString(),imageid,Double.parseDouble(price.getText().toString()),Integer.parseInt(calories.getText().toString()),Integer.parseInt(time.getText().toString()),1);

                    //    Cart.getInstance().cartItemList.set(i,Cart.getInstance().cartItemList.get(i)).setItem(Cart.getInstance().cartItemList.get(i).getItem().setPopularity(pop));
                    }

                    Random rand = new Random();

                    Integer  orderId = rand.nextInt(1000) + 1;
                    Order order = new Order(Cart.getInstance().cartItemList, totalPrice, LoginContext.currentUser.getEmail(), orderPlacementTime, pickupTimeVal, totalTime,orderId.toString(),"In Progress");

                    Database.getInstance().setNodeOrderDetails("orders", order);


                    if(orderCancel)
                        showPickupTimeConflictDialog();
                    else {
                        Toast.makeText(getApplicationContext(), "Order placed Successfully", Toast.LENGTH_LONG).show();

                        Cart.getInstance().cartItemList.clear();
                        //move to next activity
                        Intent intent = new Intent(getApplicationContext(), UserRestaurantActivity.class);

                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(CartActivity.this, "Please select date and time correctly!!",
                            Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    public void showPickupTimeConflictDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(CartActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Order Canceled");
        dialog.setMessage("Please select another pickup time and order again.Your order request has been canceled." );
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();
    }
}
