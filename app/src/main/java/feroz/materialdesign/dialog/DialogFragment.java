package feroz.materialdesign.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.afollestad.materialdialogs.util.DialogUtils;
import com.example.feroz.materialdesign.R;

import java.util.ArrayList;

import feroz.materialdesign.MainActivity;
import feroz.materialdesign.dashboard.NewDashboard;
import feroz.materialdesign.dialog.adapter.ButtonItemAdapter;
import feroz.materialdesign.dialog.adapter.DialogGridViewAdapter;
import feroz.materialdesign.theme.pojo.ThemeObject;

/**
 * Created by Feroz on 17/11/2016.
 */

public class DialogFragment extends Fragment {
    private GridView androidGridView;
    private DialogGridViewAdapter dialogGridViewAdapter;
    private Button button;
    private ArrayList<ThemeObject> themeObjectArrayList;
    private EditText passwordInput;
    private View positiveAction;
    private int primaryPreselect;
    private int accentPreselect;
    private AppCompatActivity hero;
    private Thread mThread;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hero = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.dialog_fragment, container, false);
        primaryPreselect = DialogUtils.resolveColor(getContext(), R.attr.colorPrimary);
        accentPreselect = DialogUtils.resolveColor(getContext(), R.attr.colorAccent);
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
        themeObjectArrayList.add(new ThemeObject("ListCheckPrompt","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("ListLongPress","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("SingleChoice","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("SingleChoice LongItems","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("MultiChoice","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("MultiChoice Limited","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("MultiChoice LongItems","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("MultiChoice DisabledItems","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("SimpleList","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("CustomList","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("CustomView","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("CustomWebView","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("ShowTheme","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("ShowCancel DismissCallbacks","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("InputDialog","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("InputDialog Custom Invalidation","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("InputDialog CheckPrompt","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("Progress Determinate Dialog","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("Progress Indeterminate Dialog","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("Progress Horizontal Indeterminate Dialog","#33b5e5"));

        //ProgressIndeterminateDialog
        androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);
        dialogGridViewAdapter = new DialogGridViewAdapter(getContext(),themeObjectArrayList);
        androidGridView.setAdapter(dialogGridViewAdapter);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.grid_item_anim);
        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
        androidGridView.setLayoutAnimation(controller);

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
            case "ListLongPress":
                showListLongPress();
                break;
            case "SingleChoice":
                showSingleChoice();
                break;
            case "SingleChoice LongItems":
                showSingleChoiceLongItems();
                break;
            case "MultiChoice":
                showMultiChoice();
                break;
            case"MultiChoice Limited":
                showMultiChoiceLimited();
                break;
            case "MultiChoice LongItems":
                showMultiChoiceLongItems();
                break;
            case "MultiChoice DisabledItems":
                showMultiChoiceDisabledItems();
                break;
            case "SimpleList":
                showSimpleList();
                break;
            case "CustomList":
                showCustomList();
                break;
            case "CustomView":
                showCustomView();
                break;
            case "CustomWebView":
                showCustomWebView();
                break;
            case "ShowTheme":
                showThemed();
                break;
            case "ShowCancel DismissCallbacks":
                showShowCancelDismissCallbacks();
                break;
            case "InputDialog":
                showInputDialog();
                break;
            case "InputDialog Custom Invalidation":
                showInputDialogCustomInvalidation();
                break;
            case "InputDialog CheckPrompt":
                showInputDialogCheckPrompt();
                break;
            case "Progress Determinate Dialog":
                showProgressDeterminateDialog();
                break ;
            case "Progress Indeterminate Dialog":
                showProgressIndeterminateDialog();
                break;
            case "Progress Horizontal Indeterminate Dialog":
                showProgressHorizontalIndeterminateDialog();
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

    static int index = 0;

    public void showListLongPress() {
        index = 0;
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        showToast(which + ": " + text);
                    }
                })
                .autoDismiss(false)
                .itemsLongCallback(new MaterialDialog.ListLongCallback() {
                    @Override
                    public boolean onLongSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        dialog.getItems().remove(position);
                        dialog.notifyItemsChanged();
                        return false;
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        index++;
                        dialog.getItems().add("Item " + index);
                        dialog.notifyItemInserted(dialog.getItems().size() - 1);
                    }
                })
                .neutralText(R.string.add_item)
                .show();
    }


    public void showSingleChoice() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallbackSingleChoice(2, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        showToast(which + ": " + text);
                        return true; // allow selection
                    }
                })
                .positiveText(R.string.md_choose_label)
                .show();
    }



    public void showSingleChoiceLongItems() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks_longItems)
                .itemsCallbackSingleChoice(2, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        showToast(which + ": " + text);
                        return true; // allow selection
                    }
                })
                .positiveText(R.string.md_choose_label)
                .show();
    }


    public void showMultiChoice() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallbackMultiChoice(new Integer[]{1, 3}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < which.length; i++) {
                            if (i > 0) str.append('\n');
                            str.append(which[i]);
                            str.append(": ");
                            str.append(text[i]);
                        }
                        showToast(str.toString());
                        return true; // allow selection
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.clearSelectedIndices();
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.md_choose_label)
                .autoDismiss(false)
                .neutralText(R.string.clear_selection)
                .show();
    }

    public void showMultiChoiceLimited() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallbackMultiChoice(new Integer[]{1}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        boolean allowSelection = which.length <= 2; // limit selection to 2, the new selection is included in the which array
                        if (!allowSelection) {
                            showToast("Selection limit reached!");
                        }
                        return allowSelection;
                    }
                })
                .positiveText(R.string.dismiss)
                .alwaysCallMultiChoiceCallback() // the callback will always be called, to check if selection is still allowed
                .show();
    }
    public void showMultiChoiceLongItems() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks_longItems)
                .itemsCallbackMultiChoice(new Integer[]{1, 3}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < which.length; i++) {
                            if (i > 0) str.append('\n');
                            str.append(which[i]);
                            str.append(": ");
                            str.append(text[i]);
                        }
                        showToast(str.toString());
                        return true; // allow selection
                    }
                })
                .positiveText(R.string.md_choose_label)
                .show();
    }
    public void showMultiChoiceDisabledItems() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallbackMultiChoice(new Integer[]{0, 1, 2}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < which.length; i++) {
                            if (i > 0) str.append('\n');
                            str.append(which[i]);
                            str.append(": ");
                            str.append(text[i]);
                        }
                        showToast(str.toString());
                        return true; // allow selection
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.clearSelectedIndices();
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.md_choose_label)
                .autoDismiss(false)
                .neutralText(R.string.clear_selection)
                .itemsDisabledIndices(0, 1)
                .show();
    }


    public void showSimpleList() {
        final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(new MaterialSimpleListAdapter.Callback() {
            @Override
            public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                showToast(item.getContent().toString());
            }
        });
        adapter.add(new MaterialSimpleListItem.Builder(getContext())
                .content("username@gmail.com")
                .icon(R.drawable.ic_action_new)
                .backgroundColor(Color.WHITE)
                .build());
        adapter.add(new MaterialSimpleListItem.Builder(getContext())
                .content("user02@gmail.com")
                .icon(R.drawable.ic_action_refresh)
                .backgroundColor(Color.WHITE)
                .build());
        adapter.add(new MaterialSimpleListItem.Builder(getContext())
                .content(R.string.add_account)
                .icon(R.mipmap.ic_menu_black_36dp)
                .iconPaddingDp(8)
                .build());

        new MaterialDialog.Builder(getContext())
                .title(R.string.set_backup)
                .adapter(adapter, null)
                .show();
    }



    public void showCustomList() {
        final ButtonItemAdapter adapter = new ButtonItemAdapter(getContext(), R.array.socialNetworks);
        adapter.setCallback(new ButtonItemAdapter.Callback() {
            @Override
            public void onItemClicked(int index) {
                showToast("Item clicked: " + index);
            }

            @Override
            public void onButtonClicked(int index) {
                showToast("Button clicked: " + index);
            }
        });

        new MaterialDialog.Builder(getContext())
                .title(R.string.socialNetworks)
                .adapter(adapter, null)
                .show();
    }
    public void showCustomView() {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title(R.string.googleWifi)
                .customView(R.layout.dialog_customview, true)
                .positiveText(R.string.connect)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showToast("Password: " + passwordInput.getText().toString());
                    }
                }).build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
        passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.password);
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                positiveAction.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Toggling the show password CheckBox will mask or unmask the password input EditText
        CheckBox checkbox = (CheckBox) dialog.getCustomView().findViewById(R.id.showPassword);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passwordInput.setInputType(!isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                passwordInput.setTransformationMethod(!isChecked ? PasswordTransformationMethod.getInstance() : null);
            }
        });

        int widgetColor = ThemeSingleton.get().widgetColor;
        MDTintHelper.setTint(checkbox,
                widgetColor == 0 ? ContextCompat.getColor(getContext(), R.color.accent) : widgetColor);

        MDTintHelper.setTint(passwordInput,
                widgetColor == 0 ? ContextCompat.getColor(getContext(), R.color.accent) : widgetColor);

        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
    }

    public void showCustomWebView() {
        int accentColor = ThemeSingleton.get().widgetColor;
        if (accentColor == 0)
            accentColor = ContextCompat.getColor(getContext(), R.color.accent);
        ChangelogDialog.create(false, accentColor)
                .show(getActivity().getSupportFragmentManager(), "changelog");
    }

    public void showThemed() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .positiveColorRes(R.color.mdb_red)
                .negativeColorRes(R.color.materilize_cyan)
                .titleGravity(GravityEnum.CENTER)
                .titleColorRes(R.color.mdb_red)
                .contentColorRes(android.R.color.white)
                .backgroundColorRes(R.color.material_blue_grey_800)
                .dividerColorRes(R.color.accent)
                .btnSelector(R.drawable.md_btn_selector_custom, DialogAction.POSITIVE)
                .positiveColor(Color.WHITE)
                .negativeColorAttr(android.R.attr.textColorSecondaryInverse)
                .theme(Theme.DARK)
                .show();
    }

    public void showShowCancelDismissCallbacks() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .neutralText(R.string.more_info)
                .showListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        showToast("onShow");
                    }
                })
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        showToast("onCancel");
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        showToast("onDismiss");
                    }
                })
                .show();
    }

    public void showInputDialog() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.input)
                .content(R.string.input_content)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 16)
                .positiveText(R.string.submit)
                .input(R.string.input_hint, R.string.input_hint, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        showToast("Hello, " + input.toString() + "!");
                    }
                }).show();
    }

    public void showInputDialogCustomInvalidation() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.input)
                .content(R.string.input_content_custominvalidation)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .positiveText(R.string.submit)
                .alwaysCallInputCallback() // this forces the callback to be invoked with every input change
                .input(R.string.input_hint, 0, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (input.toString().equalsIgnoreCase("hello")) {
                            dialog.setContent("I told you not to type that!");
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        } else {
                            dialog.setContent(R.string.input_content_custominvalidation);
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        }
                    }
                }).show();
    }

    public void showInputDialogCheckPrompt() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.input)
                .content(R.string.input_content)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 16)
                .positiveText(R.string.submit)
                .input(R.string.input_hint, R.string.input_hint, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        showToast("Hello, " + input.toString() + "!");
                    }
                })
                .checkBoxPromptRes(R.string.example_prompt, true, null)
                .show();
    }

    public void showProgressDeterminateDialog() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .contentGravity(GravityEnum.CENTER)
                .progress(false, 150, true)
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (mThread != null)
                            mThread.interrupt();
                    }
                })
                .showListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        final MaterialDialog dialog = (MaterialDialog) dialogInterface;
                        startThread(new Runnable() {
                            @Override
                            public void run() {
                                while (dialog.getCurrentProgress() != dialog.getMaxProgress() &&
                                        !Thread.currentThread().isInterrupted()) {
                                    if (dialog.isCancelled())
                                        break;
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        break;
                                    }
                                    dialog.incrementProgress(1);
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mThread = null;
                                        dialog.setContent(getString(R.string.md_done_label));
                                    }
                                });

                            }
                        });
                    }
                }).show();
    }

    public void showProgressIndeterminateDialog() {
        showIndeterminateProgressDialog(false);
    }
    public void showProgressHorizontalIndeterminateDialog() {
        showIndeterminateProgressDialog(true);
    }


    private void startThread(Runnable run) {
        if (mThread != null)
            mThread.interrupt();
        mThread = new Thread(run);
        mThread.start();
    }
    private void showIndeterminateProgressDialog(boolean horizontal) {
        new MaterialDialog.Builder(getContext())
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .show();
    }



    private void showToast(String s) {
        Toast.makeText(getActivity(), s,
                Toast.LENGTH_LONG).show();
    }



    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new NewDashboard()).commit();
                    return true;
                }
                return false;
            }
        });
    }

}
