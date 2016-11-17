package com.example.feroz.materialdesign.bottomsheet;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.feroz.materialdesign.R;
import com.example.feroz.materialdesign.bottomsheet.adapter.CustomGridViewAdapter;
import com.example.feroz.materialdesign.theme.pojo.ThemeObject;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.ImagePickerSheetView;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Feroz on 17/11/2016.
 */

public class BottomSheet extends Fragment {

    GridView androidGridView;
    private ArrayList<ThemeObject> themeObjectArrayList;
    private BottomSheetBehavior bottomSheetBehavior;
    private static final int REQUEST_STORAGE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = REQUEST_STORAGE + 1;
    private static final int REQUEST_LOAD_IMAGE = REQUEST_IMAGE_CAPTURE + 1;
    protected BottomSheetLayout bottomSheetLayout;
    private Uri cameraImageUri = null;
    private ImageView selectedImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottomsheet, container, false);
        themeObjectArrayList = new ArrayList<>();
        themeObjectArrayList.add(new ThemeObject("Expand","#ff4444"));
        themeObjectArrayList.add(new ThemeObject("Collapse","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("Hide","#2BBBAD"));
        themeObjectArrayList.add(new ThemeObject("Show","#f4511e"));
        themeObjectArrayList.add(new ThemeObject("Intent Picker","#f4511e"));
        themeObjectArrayList.add(new ThemeObject("Menu Picker","#33b5e5"));
        themeObjectArrayList.add(new ThemeObject("Image Picker","#2BBBAD"));

        CustomGridViewAdapter adapterViewAndroid = new CustomGridViewAdapter(getContext(), themeObjectArrayList);
        androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);

        androidGridView.setAdapter(adapterViewAndroid);
        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheetLayout));
        bottomSheetLayout = (BottomSheetLayout) view.findViewById(R.id.bottomsheet);
        selectedImage = (ImageView) view.findViewById(R.id.image_picker_selected);


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

        switch(themeObjectArrayList.get(i).getColor()){
            case "Expand":
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
            case "Collapse":
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                break;
            case "Hide":
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                break;
            case "Show":
                new CustomBottomSheetDialogFragment().show(activity.getSupportFragmentManager(), "Dialog");
                break;
            case "Image Picker":
                if (checkNeedsPermission()) {
                    requestStoragePermission();
                } else {
                    showSheetView();
                }
                break;
            case "Menu Picker":
                Intent ii = new Intent(getContext(),BottomSheetMenuActivity.class);

                startActivity(ii);
                break;
            case "Intent Picker":
                /*Intent iii = new Intent(getContext(),PickerActivity.class);
                startActivity(iii);*/
                showIntentPicker();

                break;
            default:
                break;

        }

    }

    private void showIntentPicker() {

      /*  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(shareText.getWindowToken(), 0);*/

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Share++");
        shareIntent.setType("text/plain");
        IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(getContext(), shareIntent, "Share with...", new IntentPickerSheetView.OnIntentPickedListener() {
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
        IntentPickerSheetView.ActivityInfo customInfo = new IntentPickerSheetView.ActivityInfo(customDrawable, "Custom mix-in", getContext(), BottomSheetMainActivity.class);
        intentPickerSheet.setMixins(Collections.singletonList(customInfo));

        bottomSheetLayout.showWithSheetView(intentPickerSheet);
    }


    //************************* start of Image Picker ***********************************************//
    private boolean checkNeedsPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        } else {
            // Eh, prompt anyway
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showSheetView();
            } else {
                // Permission denied
                Toast.makeText(getContext(), "Sheet is useless without access to external storage :/", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Show an {@link ImagePickerSheetView}
     */
    private void showSheetView() {
        ImagePickerSheetView sheetView = new ImagePickerSheetView.Builder(getContext())
                .setMaxItems(30)
                .setShowCameraOption(createCameraIntent() != null)
                .setShowPickerOption(createPickIntent() != null)
                .setImageProvider(new ImagePickerSheetView.ImageProvider() {
                    @Override
                    public void onProvideImage(ImageView imageView, Uri imageUri, int size) {
                        Glide.with(getContext())
                                .load(imageUri)
                                .centerCrop()
                                .crossFade()
                                .into(imageView);
                    }
                })
                .setOnTileSelectedListener(new ImagePickerSheetView.OnTileSelectedListener() {
                    @Override
                    public void onTileSelected(ImagePickerSheetView.ImagePickerTile selectedTile) {
                        bottomSheetLayout.dismissSheet();
                        if (selectedTile.isCameraTile()) {
                            dispatchTakePictureIntent();
                        } else if (selectedTile.isPickerTile()) {
                            startActivityForResult(createPickIntent(), REQUEST_LOAD_IMAGE);
                        } else if (selectedTile.isImageTile()) {
                            showSelectedImage(selectedTile.getImageUri());
                        } else {
                            genericError();
                        }
                    }
                })
                .setTitle("Choose an image...")
                .create();

        bottomSheetLayout.showWithSheetView(sheetView);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        cameraImageUri = Uri.fromFile(imageFile);
        return imageFile;
    }

    /**
     * This checks to see if there is a suitable activity to handle the `ACTION_PICK` intent
     * and returns it if found. {@link Intent#ACTION_PICK} is for picking an image from an external app.
     *
     * @return A prepared intent if found.
     */
    @Nullable
    private Intent createPickIntent() {
        Intent picImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (picImageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            return picImageIntent;
        } else {
            return null;
        }
    }

    /**
     * This checks to see if there is a suitable activity to handle the {@link MediaStore#ACTION_IMAGE_CAPTURE}
     * intent and returns it if found. {@link MediaStore#ACTION_IMAGE_CAPTURE} is for letting another app take
     * a picture from the camera and store it in a file that we specify.
     *
     * @return A prepared intent if found.
     */
    @Nullable
    private Intent createCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            return takePictureIntent;
        } else {
            return null;
        }
    }

    /**
     * This utility function combines the camera intent creation and image file creation, and
     * ultimately fires the intent.
     *
     * @see {@link #createCameraIntent()}
     * @see {@link #createImageFile()}
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = createCameraIntent();
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent != null) {
            // Create the File where the photo should go
            try {
                File imageFile = createImageFile();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                // Error occurred while creating the File
                genericError("Could not create imageFile for camera");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage = null;
            if (requestCode == REQUEST_LOAD_IMAGE && data != null) {
                selectedImage = data.getData();
                if (selectedImage == null) {
                    genericError();
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Do something with imagePath
                selectedImage = cameraImageUri;
            }

            if (selectedImage != null) {
                showSelectedImage(selectedImage);
            } else {
                genericError();
            }
        }
    }

    private void showSelectedImage(Uri selectedImageUri) {
        selectedImage.setImageDrawable(null);
        Glide.with(this)
                .load(selectedImageUri)
                .crossFade()
                .fitCenter()
                .into(selectedImage);
    }

    private void genericError() {
        genericError(null);
    }

    private void genericError(String message) {
        Toast.makeText(getContext(), message == null ? "Something went wrong." : message, Toast.LENGTH_SHORT).show();
    }


    //************************* End of Image Picker ***********************************************//

}
