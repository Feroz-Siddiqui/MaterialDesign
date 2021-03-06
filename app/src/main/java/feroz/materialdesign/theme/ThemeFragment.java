package feroz.materialdesign.theme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feroz.materialdesign.R;
import feroz.materialdesign.dashboard.NewDashboard;
import feroz.materialdesign.theme.adapter.ThemeRecyclerViewAdapter;
import feroz.materialdesign.theme.pojo.ThemeObject;

import java.util.ArrayList;

/**
 * Created by Feroz on 15/11/2016.
 */

public class ThemeFragment extends Fragment {
    private GridLayoutManager lLayout;
    private ArrayList<ThemeObject> themeObjectArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.theme_fragment, container, false);
        lLayout = new GridLayoutManager(getContext(), 2);
        themeObjectArrayList = new ArrayList<>();
        themeObjectArrayList.add(new ThemeObject("Red","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("Blue","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("Green","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("Orange","#f4511e"));


        RecyclerView rView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        ThemeRecyclerViewAdapter rcAdapter = new ThemeRecyclerViewAdapter(getContext(), themeObjectArrayList);
        rView.setAdapter(rcAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new NewDashboard()).commit();
                    return true;
                }
                return false;
            }
        });
    }
}