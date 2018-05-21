package cmpe.sjsu.food4u;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by manas on 5/20/2018.
 */

public class OrdersViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {


    TextView orderIdView;
    Button cancelButton;
    MenuCategoryClickListener menuCategoryClickListener;

    public OrdersViewHolder(View order){
        super(order);
        orderIdView = order.findViewById(R.id.orderId);
        cancelButton = order.findViewById(R.id.orderCancel);
        cancelButton.setOnClickListener(this);
    }
    public void setMenuItemOnClickListener(MenuCategoryClickListener listner){
        menuCategoryClickListener =listner;
    }

    @Override
    public void onClick(View view) {

        menuCategoryClickListener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View view) {
        menuCategoryClickListener.onLongClick(view,getAdapterPosition(),false);
        return false;
    }
}