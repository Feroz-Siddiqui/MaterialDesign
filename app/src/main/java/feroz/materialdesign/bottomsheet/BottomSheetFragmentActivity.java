package feroz.materialdesign.bottomsheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.feroz.materialdesign.R;
import com.flipboard.bottomsheet.BottomSheetLayout;

public class BottomSheetFragmentActivity extends AppCompatActivity {
    protected BottomSheetLayout bottomSheetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_fragment);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        findViewById(R.id.bottomsheet_fragment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyFragment().show(getSupportFragmentManager(), R.id.bottomsheet);
            }
        });
    }
}
