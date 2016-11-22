package feroz.materialdesign.theme.adapter;

/**
 * Created by Feroz on 04-11-2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.feroz.materialdesign.R;

public class ThemeRecyclerViewHolders extends RecyclerView.ViewHolder {

    public Button theme_button;

    public ThemeRecyclerViewHolders(View itemView) {
        super(itemView);
        theme_button = (Button) itemView.findViewById(R.id.theme_button);

    }


}