package feroz.materialdesign.fab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.feroz.materialdesign.R;
import feroz.materialdesign.dashboard.NewDashboard;
import feroz.materialdesign.fab.adapter.FabGridAdapter;
import feroz.materialdesign.theme.pojo.ThemeObject;

import java.util.ArrayList;

/**
 * Created by Feroz on 19/11/2016.
 */

public class FabFragment extends Fragment {
    private GridView androidGridView;
    private ArrayList<ThemeObject> themeObjectArrayList;
    private FabGridAdapter fabGridAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fab_fragment, container, false);
        androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);
        themeObjectArrayList = new ArrayList<>();
        themeObjectArrayList.add(new ThemeObject("Reveal Fab","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("Home Fab","#33b5e5"));

        fabGridAdapter = new FabGridAdapter(getContext(),themeObjectArrayList);
        androidGridView.setAdapter(fabGridAdapter);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                gotoFragment(getActivity(),i,themeObjectArrayList);
            }
        });
    return view;
    }

    private void gotoFragment(FragmentActivity activity, int i, ArrayList<ThemeObject> themeObjectArrayList) {
        switch (themeObjectArrayList.get(i).getColor()){
            case "Reveal Fab":
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new RevealFab()).commit();

                break;
            case "Home Fab":
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new FabHomeFragment()).commit();

                break;
            default:
                break;
        }
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
