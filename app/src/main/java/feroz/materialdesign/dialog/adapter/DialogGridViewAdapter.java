package feroz.materialdesign.dialog.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.feroz.materialdesign.R;
import feroz.materialdesign.theme.pojo.ThemeObject;

import java.util.ArrayList;

/**
 * Created by Feroz on 17/11/2016.
 */

public class DialogGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private final ArrayList<ThemeObject> themeObjectArrayList;

    public DialogGridViewAdapter(Context context, ArrayList<ThemeObject> themeObjectArrayList) {
        mContext = context;
        this.themeObjectArrayList = themeObjectArrayList;
    }

    @Override
    public int getCount() {
        return themeObjectArrayList.size();
    }

    @Override
    public ThemeObject getItem(int i) {
        return themeObjectArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View gridViewAndroid;
        final int position = i;
        final ViewGroup parents = parent;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = inflater.inflate(R.layout.dialog_grid_view_item, null);

        } else {
            gridViewAndroid = (View) convertView;
        }
        final Button button =  (Button)gridViewAndroid.findViewById(R.id.button);
        button.setText(themeObjectArrayList.get(i).getColor());
        GradientDrawable shapeDrawable =(GradientDrawable) mContext.getResources().getDrawable(R.drawable.corner_button);
        shapeDrawable.setColor(Color.parseColor(themeObjectArrayList.get(i).getHashcode()));
        button.setBackground(shapeDrawable);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ddddddd");
                ((GridView)parents).performItemClick(view,position,0) ;               }
        });
        return gridViewAndroid;
    }
}