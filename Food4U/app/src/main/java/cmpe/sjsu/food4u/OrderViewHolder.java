package cmpe.sjsu.food4u;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

public TextView txtOrderId,txtOrderStatus,txtOrderEmail;

    private ItemClickListener itemClickListener;

public OrderViewHolder(View itemView){
    super(itemView);

    txtOrderEmail=(TextView)itemView.findViewById(R.id.order_email);
    txtOrderId=(TextView)itemView.findViewById(R.id.order_id);
    txtOrderStatus=(TextView)itemView.findViewById(R.id.order_status);

    itemView.setOnClickListener(this);
}

public void setItemClickListener(ItemClickListener itemClickListener ){
    this.itemClickListener=itemClickListener;
}

    @Override
    public void onClick(View view) {
itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
