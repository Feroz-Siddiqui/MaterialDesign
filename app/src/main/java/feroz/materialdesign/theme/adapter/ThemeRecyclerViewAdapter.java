package feroz.materialdesign.theme.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import feroz.materialdesign.MainActivity;
import com.example.feroz.materialdesign.R;
import feroz.materialdesign.theme.pojo.ThemeObject;

import java.util.List;

/**
 * Created by Feroz on 04-11-2016.
 */

public class ThemeRecyclerViewAdapter extends RecyclerView.Adapter<ThemeRecyclerViewHolders> {

    private List<ThemeObject> itemList;
    private Context context;

    public ThemeRecyclerViewAdapter(Context context, List<ThemeObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ThemeRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.theme_button, null);
        ThemeRecyclerViewHolders rcv = new ThemeRecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ThemeRecyclerViewHolders holder,final int position) {
        holder.theme_button.setText(itemList.get(position).getColor());
        holder.theme_button.setBackgroundColor(Color.parseColor(itemList.get(position).getHashcode()));
        holder.theme_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.changeDrawerBackground(itemList.get(position).getHashcode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
