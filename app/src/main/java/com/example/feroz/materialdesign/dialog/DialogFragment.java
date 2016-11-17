package com.example.feroz.materialdesign.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.example.feroz.materialdesign.R;
import com.example.feroz.materialdesign.dialog.adapter.DialogGridViewAdapter;
import com.example.feroz.materialdesign.theme.pojo.ThemeObject;

import java.util.ArrayList;

/**
 * Created by Feroz on 17/11/2016.
 */

public class DialogFragment extends Fragment {
    private GridView androidGridView;
    private DialogGridViewAdapter dialogGridViewAdapter;
    private Button button;
    private ArrayList<ThemeObject> themeObjectArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment, container, false);
        themeObjectArrayList = new ArrayList<>();
        themeObjectArrayList.add(new ThemeObject("Basic","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("Basic Icon","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("Basic Check Prompt","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("Stacked","#f4511e"));
        themeObjectArrayList.add(new ThemeObject("Neutral","#f4511e"));
        themeObjectArrayList.add(new ThemeObject("List","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("ListNoTitle","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("LongList","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("ListLongItems","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("ListCheckPrompt","#ff4444"));

        //
        androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);
        dialogGridViewAdapter = new DialogGridViewAdapter(getContext(),themeObjectArrayList);
        androidGridView.setAdapter(dialogGridViewAdapter);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                gotoFragment(getActivity(),i,themeObjectArrayList);
            }
        });

        return view;
    }

    private void gotoFragment(FragmentActivity activity, int i, ArrayList<ThemeObject> themeObjectArrayList) {

        switch (themeObjectArrayList.get(i).getColor()){
            case "Basic":
                new MaterialDialog.Builder(getContext())
                        .title("Location")
                        .content(R.string.useGoogleLocationServicesPrompt)
                        .positiveText("Agree")
                        .negativeText("Disagree")
                        .show();
                break;
            case "Basic Icon":
                showBasicIcon();
                break;
            case "Basic Check Prompt":
                showBasicCheckPrompt();
                break;
            case "Stacked":
                showStacked();
                break;
            case "Neutral":
                showNeutral();
                break;
            case "List":
                showList();
                break;
            case "ListNoTitle":
                showListNoTitle();
                break;
            case "LongList":
                showLongList();
                break;
            case "ListLongItems":
                showListLongItems();
                break;
            case "ListCheckPrompt":
                showListCheckPrompt();
                break;
            default:
                break;
        }
    }



    public void showBasicIcon() {
        new MaterialDialog.Builder(getContext())
                .iconRes(R.mipmap.ic_launcher)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title("Location service")
                .content(R.string.useGoogleLocationServicesPrompt)
                .positiveText("Agree")
                .negativeText("Disagree")
                .show();
    }

    public void showBasicCheckPrompt() {
        new MaterialDialog.Builder(getContext())
                .iconRes(R.mipmap.ic_launcher)
                .limitIconToDefaultSize()
                .title(Html.fromHtml(getString(R.string.permissionSample, getString(R.string.app_name))))
                .positiveText(R.string.allow)
                .negativeText(R.string.deny)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showToast("Prompt checked? " + dialog.isPromptCheckBoxChecked());

                    }
                })
                .checkBoxPromptRes(R.string.dont_ask_again, false, null)
                .show();
    }

    public void showStacked() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt)
                .positiveText(R.string.speedBoost)
                .negativeText(R.string.noThanks)
                .btnStackedGravity(GravityEnum.END)
                .stackingBehavior(StackingBehavior.ALWAYS)  // this generally should not be forced, but is used for demo purposes
                .show();
    }

    public void showNeutral() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .neutralText(R.string.more_info)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showToast(which.name() + "!");
                    }
                })
                .show();
    }


    public void showList() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        showToast(which + ": " + text);
                    }
                })
                .show();
    }

    public void showListNoTitle() {
        new MaterialDialog.Builder(getContext())
                .items(R.array.socialNetworks)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        showToast(which + ": " + text);
                    }
                })
                .show();
    }

    public void showLongList() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.states)
                .items(R.array.states)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        showToast(which + ": " + text);
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }



    public void showListLongItems() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks_longItems)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        showToast(which + ": " + text);
                    }
                })
                .show();
    }

    public void showListCheckPrompt() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        showToast(which + ": " + text);
                    }
                })
                .checkBoxPromptRes(R.string.example_prompt, true, null)
                .negativeText(android.R.string.cancel)
                .show();
    }

    private void showToast(String s) {
        Toast.makeText(getActivity(), s,
                Toast.LENGTH_LONG).show();
    }



}
