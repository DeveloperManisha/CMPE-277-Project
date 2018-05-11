package cmpe.sjsu.food4u;

import android.view.View;

/**
 * Created by manas on 5/6/2018.
 */

public interface MenuCategoryClickListener {
    void onClick(View v,int posistion,boolean flag);
    void onLongClick(View v,int posistion,boolean flag);
}
