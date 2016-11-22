package feroz.materialdesign.bottomsheet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import feroz.materialdesign.MainActivity;
import com.example.feroz.materialdesign.R;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;

public class BottomSheetMenuActivity extends AppCompatActivity {
    protected BottomSheetLayout bottomSheetLayout;
    private static Toolbar toolbar;
    private static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_menu);
        sharedpreferences = getSharedPreferences(getResources().getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BottomSheet Menu");
        Drawable mDrawable = getResources().getDrawable(R.mipmap.ic_arrow_back_black_24dp);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawable.setTint(Color.WHITE);
        }
        toolbar.setNavigationIcon(mDrawable);
        if(!sharedpreferences.getString("theme_color", "").equalsIgnoreCase("") ){
            toolbar.setBackgroundColor(Color.parseColor(sharedpreferences.getString("theme_color", "")));

        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BottomSheetMenuActivity.this, MainActivity.class);
                i.putExtra("bottomsheet","bottomsheet");

                startActivity(i);
            }
        });
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        bottomSheetLayout.setPeekOnDismiss(true);
        findViewById(R.id.list_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuSheet(MenuSheetView.MenuType.LIST);
            }
        });
        findViewById(R.id.grid_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuSheet(MenuSheetView.MenuType.GRID);
            }
        });
    }

    private void showMenuSheet(final MenuSheetView.MenuType menuType) {
        MenuSheetView menuSheetView =
                new MenuSheetView(BottomSheetMenuActivity.this, menuType, "Create...", new MenuSheetView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(BottomSheetMenuActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (bottomSheetLayout.isSheetShowing()) {
                            bottomSheetLayout.dismissSheet();
                        }
                        if (item.getItemId() == R.id.reopen) {
                            showMenuSheet(menuType == MenuSheetView.MenuType.LIST ? MenuSheetView.MenuType.GRID : MenuSheetView.MenuType.LIST);
                        }
                        return true;
                    }
                });
        menuSheetView.inflateMenu(R.menu.bottom_sheet_create);
        bottomSheetLayout.showWithSheetView(menuSheetView);
    }
}