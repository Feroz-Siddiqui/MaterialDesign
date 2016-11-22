package feroz.materialdesign.tab.adapter;

/**
 * Created by Feroz on 15/11/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import feroz.materialdesign.tab.activity.fragment.MaterialTabOne;
import feroz.materialdesign.tab.activity.fragment.MaterialTabThree;
import feroz.materialdesign.tab.activity.fragment.MaterialTabTwo;


public class MaterialTabPagerAdapter extends FragmentStatePagerAdapter {
    public MaterialTabPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragement for Android Tab
                return new MaterialTabOne();
            case 1:
                //Fragment for Ios Tab
                return new MaterialTabTwo();
            case 2:
                //Fragment for Windows Tab
                return new MaterialTabThree();
        }
        return null;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3; //No of Tabs
    }

}