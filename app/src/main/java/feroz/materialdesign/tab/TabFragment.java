package feroz.materialdesign.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.feroz.materialdesign.R;
import feroz.materialdesign.tab.activity.ViewPagerTabActivity;

/**
 * Created by Feroz on 15/11/2016.
 */

public class TabFragment extends Fragment {
    private Button material_tab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.material_tab_fragment, container, false);
        material_tab = (Button) view.findViewById(R.id.materialTab);

        material_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ViewPagerTabActivity.class);
                startActivity(i);
            }
        });
    return view;
    }
}
