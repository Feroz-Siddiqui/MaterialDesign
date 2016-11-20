package com.example.feroz.materialdesign.textviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feroz.materialdesign.R;
import com.example.feroz.materialdesign.textviews.util.BulletListBuilder;
import com.example.feroz.materialdesign.textviews.util.MyClickableSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feroz on 21/11/2016.
 */

public class TextViewFragment extends Fragment {
    private TextView first_text,list,clickText,size_increase,paragraph;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_view_fragment, container, false);
        first_text = (TextView) view.findViewById(R.id.first_text);
        list = (TextView) view.findViewById(R.id.list);
        clickText = (TextView) view.findViewById(R.id.clickText);
        size_increase =(TextView) view.findViewById(R.id.size_increase);
        paragraph = (TextView) view.findViewById(R.id.paragraph);

        first_text.setText(getForeGroundColor());

        //list
        List<String> lines = new ArrayList<>();
        lines.add("First line");
        lines.add("Second line");
        lines.add("Really long third line that will wrap and indent properly.");
        lines.add("Fourth line");
        list.setText(new BulletListBuilder(getContext()).getBulletList(lines,"",40));
        //click
        clickText.setText(getClickableText());
        clickText.setMovementMethod(LinkMovementMethod.getInstance());
        //size increase
        size_increase.setText(getSizeIncrease());

        paragraph.setText(getParagraphText());
        return view;
    }

    private CharSequence getParagraphText() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        Spannable spannable = new SpannableString("The Reserve Bank of India (RBI, Hindi: भारतीय रिज़र्व बैंक) is India's central banking institution, which controls the monetary policy of the Indian rupee. It commenced its operations on 1 April 1935 during the British Rule in accordance with the provisions of the Reserve Bank of India Act, 1934.[5] The original share capital was divided into shares of 100 each fully paid, which were initially owned entirely by private shareholders.[6] Following India's independence on 15 August 1947, the RBI was nationalised on 1 January 1949.");
        spannable.setSpan(new AlignmentSpan() {
            @Override
            public Layout.Alignment getAlignment() {
                return Layout.Alignment.ALIGN_NORMAL;
            }
        }, 0, spannable.length(), Spanned.SPAN_PARAGRAPH);
     //   spannable.setSpan(new ImageSpan(getContext(),R.drawable.one),4,5,0);
        sb.append(spannable);
        return sb;
    }

    private CharSequence getSizeIncrease() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        Spannable spannable = new SpannableString("FIrst Three word size will increase");
        spannable.setSpan(new RelativeSizeSpan(1.5f), 0, 3, 0);
        sb.append(spannable);
        return sb;
    }

    private CharSequence getClickableText() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        Spannable spannable = new SpannableString("This is Clickable textview");
        spannable.setSpan(new MyClickableSpan(spannable.toString(),getContext()),
                0,spannable.length(), 0);
        sb.append(spannable);
        return sb;

    }

    private CharSequence getForeGroundColor() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        Spannable spannable = new SpannableString("This is Tilte");
        spannable.setSpan(new ForegroundColorSpan(getContext().getResources()
                .getColor(R.color.colorAccent)), 0, spannable.length()-2, 0);
        sb.append(spannable);
        return sb;
    }
}
