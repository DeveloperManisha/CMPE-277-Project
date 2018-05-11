package cmpe.sjsu.food4u;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by manas on 5/6/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {


    ImageView menuCategoryImage;
    TextView  menuCategoryName;
    MenuCategoryClickListener menuCategoryClickListener;

    public MenuViewHolder(View menuCategory){
        super(menuCategory);
        menuCategoryImage = menuCategory.findViewById(R.id.categoryImage);
        menuCategoryName = menuCategory.findViewById(R.id.categoryName);
        menuCategory.setOnClickListener(this);
        menuCategory.setOnLongClickListener(this);


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
