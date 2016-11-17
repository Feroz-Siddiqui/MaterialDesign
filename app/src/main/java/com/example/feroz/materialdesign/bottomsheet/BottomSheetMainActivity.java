package com.example.feroz.materialdesign.bottomsheet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.feroz.materialdesign.MainActivity;
import com.example.feroz.materialdesign.R;

public class BottomSheetMainActivity extends AppCompatActivity {
    private static Toolbar toolbar;
    private static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_main);
        sharedpreferences = getSharedPreferences(getResources().getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BottomSheet");
        Drawable mDrawable = getResources().getDrawable(R.mipmap.ic_arrow_back_black_24dp);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawable.setTint(Color.WHITE);
        }
        toolbar.setNavigationIcon(mDrawable);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BottomSheetMainActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        if(sharedpreferences !=null){
            if(!sharedpreferences.getString("theme_color", "").equalsIgnoreCase("") ){
                toolbar.setBackgroundColor(Color.parseColor(sharedpreferences.getString("theme_color", "")));

            }
        }
        findViewById(R.id.picker_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BottomSheetMainActivity.this, PickerActivity.class));
            }
        });

        findViewById(R.id.menu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BottomSheetMainActivity.this, BottomSheetMenuActivity.class));
            }
        });

        findViewById(R.id.image_picker_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BottomSheetMainActivity.this, BottomSheetImagePickerActivity.class));
            }
        });

        findViewById(R.id.bottomsheet_fragment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BottomSheetMainActivity.this, BottomSheetFragmentActivity.class));
            }
        });
    }
}