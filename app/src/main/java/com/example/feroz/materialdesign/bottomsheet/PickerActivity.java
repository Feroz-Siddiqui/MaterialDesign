package com.example.feroz.materialdesign.bottomsheet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.feroz.materialdesign.MainActivity;
import com.example.feroz.materialdesign.R;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

import java.util.Collections;
import java.util.Comparator;
public class PickerActivity extends AppCompatActivity {
    protected BottomSheetLayout bottomSheetLayout;
    private static Toolbar toolbar;
    private static SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        final TextView shareText = (TextView) findViewById(R.id.share_text);
        final Button shareButton = (Button) findViewById(R.id.share_button);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BottomSheet Intent Picker");
        Drawable mDrawable = getResources().getDrawable(R.mipmap.ic_arrow_back_black_24dp);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawable.setTint(Color.WHITE);
        }
        toolbar.setNavigationIcon(mDrawable);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PickerActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(shareText.getWindowToken(), 0);

                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText.getText().toString());
                shareIntent.setType("text/plain");
                IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(PickerActivity.this, shareIntent, "Share with...", new IntentPickerSheetView.OnIntentPickedListener() {
                    @Override
                    public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                        bottomSheetLayout.dismissSheet();
                        startActivity(activityInfo.getConcreteIntent(shareIntent));
                    }
                });
                // Filter out built in sharing options such as bluetooth and beam.
                intentPickerSheet.setFilter(new IntentPickerSheetView.Filter() {
                    @Override
                    public boolean include(IntentPickerSheetView.ActivityInfo info) {
                        return !info.componentName.getPackageName().startsWith("com.android");
                    }
                });
                // Sort activities in reverse order for no good reason
                intentPickerSheet.setSortMethod(new Comparator<IntentPickerSheetView.ActivityInfo>() {
                    @Override
                    public int compare(IntentPickerSheetView.ActivityInfo lhs, IntentPickerSheetView.ActivityInfo rhs) {
                        return rhs.label.compareTo(lhs.label);
                    }
                });

                // Add custom mixin example
                Drawable customDrawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, null);
                IntentPickerSheetView.ActivityInfo customInfo = new IntentPickerSheetView.ActivityInfo(customDrawable, "Custom mix-in", PickerActivity.this, BottomSheetMainActivity.class);
                intentPickerSheet.setMixins(Collections.singletonList(customInfo));

                bottomSheetLayout.showWithSheetView(intentPickerSheet);
            }
        });
    }
}