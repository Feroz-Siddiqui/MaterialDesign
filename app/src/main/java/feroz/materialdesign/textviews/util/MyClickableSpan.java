package feroz.materialdesign.textviews.util;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.example.feroz.materialdesign.R;

/**
 * Created by Feroz on 21/11/2016.
 */

public class MyClickableSpan extends ClickableSpan {
    private Context context;
    public MyClickableSpan(String string, Context context) {
        super();
       this.context = context;

    }

    public void onClick(View tv) {

        Toast.makeText(context, "Thanks for the click!",
                Toast.LENGTH_SHORT).show();
    }

    public void updateDrawState(TextPaint ds) {

        ds.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        ds.setUnderlineText(true); // set to false to remove underline
    }


}