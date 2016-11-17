package com.example.feroz.materialdesign.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feroz.materialdesign.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;


/**
 * Created by Feroz on 15/11/2016.
 */

public class NewDashboard extends Fragment {
   // private TextView messageView;
   private  SharedPreferences sharedpreferences;
    BottomBar bottomBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_dashboard, container, false);
        //messageView = (TextView) view.findViewById(R.id.messageView);
        sharedpreferences = getActivity().getSharedPreferences(getResources().getString(R.string.shared_preference_key), Context.MODE_PRIVATE);

        bottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
        System.out.println("Tab Count "+bottomBar.getTabCount());
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                for (int i = 0; i < bottomBar.getTabCount(); i++) {
                    BottomBarTab tab = bottomBar.getTabAtPosition(i);
                    if(tab.getId() == tabId){
//                        tab.setBackgroundColor(Color.parseColor(sharedpreferences.getString("theme_color", "")));


                        tab.setBackground(getResources().getDrawable(R.drawable.circle));
                        LayerDrawable layerDrawable = (LayerDrawable) tab.getBackground();
                        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                                .findDrawableByLayerId(R.id.circular);
                        if(!sharedpreferences.getString("theme_color", "").equalsIgnoreCase(""))
                        gradientDrawable.setColor(Color.parseColor(sharedpreferences.getString("theme_color", "")));
                    }else{
                   tab.setBackground(null);

                    }
                }
               // messageView.setText(TabMessage.get(tabId, false));
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                System.out.println("Tab Reselected --- "+tabId);

            }
        });

        // Use the dark theme. Ignored on mobile when there are more than three tabs.
        //bottomBar.useDarkTheme(true);

        // Use custom text appearance in tab titles.
        //bottomBar.setTextAppearance(R.style.MyTextAppearance);

        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
        // custom text appearance, set the text appearance first.
        //bottomBar.setTypeFace("MyFont.ttf");
        return view;
    }


}
