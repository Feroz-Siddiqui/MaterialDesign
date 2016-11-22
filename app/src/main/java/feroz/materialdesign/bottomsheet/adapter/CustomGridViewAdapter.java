package feroz.materialdesign.bottomsheet.adapter;

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

public class CustomGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private final ArrayList<ThemeObject> themeObjectArrayList;

    public CustomGridViewAdapter(Context context, ArrayList<ThemeObject> themeObjectArrayList) {
        mContext = context;
        this.themeObjectArrayList = themeObjectArrayList;
    }

    @Override
    public int getCount() {
        return themeObjectArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        final int position = i;
        final ViewGroup parents = parent;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.bottomsheet_grid_item, null);
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
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}