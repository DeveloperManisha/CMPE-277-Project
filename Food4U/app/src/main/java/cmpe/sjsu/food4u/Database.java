package cmpe.sjsu.food4u;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by manas on 5/9/2018.
 */

public class Database {
    private FirebaseDatabase database;

    public static Database db;
    private Database(){
        database = FirebaseDatabase.getInstance();

    }
    public static Database getInstance(){
        if(db ==null)
            db = new Database();
        return db;
    }
    //example path MenuItems
    //even to create new node in same hierarchy as MenuItem add nonexisting value
    // eg orderdetails and replicate this method for your object class
    //if path doesnt exist it creates node for you as per path specified
    public void setNodeFoodItemDetails(String path,FoodItem item) {
        DatabaseReference ref = database.getReference(path);
        ref.push().setValue(item);
        System.out.println("item is saved to database");
    }
    public void setNodeOrderDetails(String path,Order/*change type to your object class */ order ) {
        DatabaseReference ref = database.getReference(path);
        ref.push().setValue(order);
        //ref.child("totalTime").push().setValue(order.totalTime);
        System.out.println("order item o database");    }

    //sample invoke from any class or any method
    // Database.getInstance().setNodeOrderDetails("order,item);
    //it ll create a order node if it doesnt exist and add item as child to this node
}
