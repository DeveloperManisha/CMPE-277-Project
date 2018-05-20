package cmpe.sjsu.food4u;

import android.view.View;

public interface ItemClickListener {
    void onClick(View view,int position,boolean isLongCLick);
}
