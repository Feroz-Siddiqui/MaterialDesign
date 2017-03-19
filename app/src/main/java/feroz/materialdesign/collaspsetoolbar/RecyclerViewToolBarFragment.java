package feroz.materialdesign.collaspsetoolbar;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feroz.materialdesign.R;

import java.util.List;

import feroz.materialdesign.collaspsetoolbar.recycleview.Role;
import feroz.materialdesign.collaspsetoolbar.recycleview.RoleDetailAdapter;

/**
 * Created by Feroz on 20-03-2017.
 */

public class RecyclerViewToolBarFragment extends Fragment {
    CollapsingToolbarLayout collapsingToolbarLayout;
    RoleDetailAdapter roleVerticalRecyclerViewAdapter;
    RecyclerView verticalRecycler;
    private List<Role> roles;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_view_fragment, container, false);
        return view;
    }
}
