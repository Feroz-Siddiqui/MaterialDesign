package feroz.materialdesign;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.feroz.materialdesign.R;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import feroz.materialdesign.bottomsheet.BottomSheet;
import feroz.materialdesign.dashboard.NewDashboard;
import feroz.materialdesign.dialog.DialogFragment;
import feroz.materialdesign.fab.FabFragment;
import feroz.materialdesign.tab.TabFragment;
import feroz.materialdesign.textviews.TextViewFragment;
import feroz.materialdesign.theme.ThemeFragment;

public class MainActivity  extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;


    //save our header or result
    private AccountHeader headerResult = null;
    private static Drawer result = null;
    private static SharedPreferences sharedpreferences;
    private IProfile profile;
    private static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(getResources().getString(R.string.shared_preference_key), Context.MODE_PRIVATE);


        // Handle Toolbar
        toolbar= (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WAKE_LOCK,Manifest.permission.RECEIVE_BOOT_COMPLETED,Manifest.permission.SET_ALARM,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }



        // Create a few sample profile


        if(sharedpreferences != null && !sharedpreferences.getString(getResources().getString(R.string.email),"").equalsIgnoreCase("")){
            profile = new ProfileDrawerItem().withName(sharedpreferences.getString(getResources().getString(R.string.first_name),"")+" "+sharedpreferences.getString(getResources().getString(R.string.last_name),"")).
                    withEmail(sharedpreferences.getString(getResources().getString(R.string.email),"")).withIcon(getResources().getDrawable(R.drawable.person_unmarried_grey));
        }else{
            profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.person_unmarried_grey));

        }

        // Create the AccountHeader
        buildHeader(false, savedInstanceState);

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withFullscreen(true)

                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.dashboard).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.theme).withIcon(GoogleMaterial.Icon.gmd_palette).withIdentifier(2),
                        new SectionDrawerItem().withName(R.string.featurelist),
                        new SecondaryDrawerItem().withName(R.string.toolbar).withIcon(GoogleMaterial.Icon.gmd_select_all).withIdentifier(3),
                        new SecondaryDrawerItem().withName(R.string.tab).withIcon(GoogleMaterial.Icon.gmd_tab).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.fab).withIcon(GoogleMaterial.Icon.gmd_menu).withIdentifier(5),
                        new SecondaryDrawerItem().withName(R.string.dialog).withSelectedIconColor(Color.RED).withIconTintingEnabled(true).withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_flip_to_front).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Bullhorn").withIdentifier(6),
                        new SecondaryDrawerItem().withName(R.string.recycleview).withIcon(GoogleMaterial.Icon.gmd_confirmation_number).withIdentifier(7),
                        new SecondaryDrawerItem().withName(R.string.bottomsheet).withIcon(GoogleMaterial.Icon.gmd_border_bottom).withIdentifier(8),
                        new SecondaryDrawerItem().withName(R.string.materialscrollbar).withIcon(GoogleMaterial.Icon.gmd_swap_vertical).withIdentifier(9),
                        new SecondaryDrawerItem().withName(R.string.searchview).withIcon(GoogleMaterial.Icon.gmd_search).withIdentifier(10),
                        new SecondaryDrawerItem().withName(R.string.materialpicker).withIcon(FontAwesome.Icon.faw_calendar_check_o).withIdentifier(11),
                        new SecondaryDrawerItem().withName(R.string.textview).withIcon(FontAwesome.Icon.faw_calendar_check_o).withIdentifier(12)

        ) // add the items we want to use with our Drawer
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        MainActivity.this.finish();
                        //return true if we have consumed the event
                        return true;
                    }
                }).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                    gotoDashboard();
                            }else if(drawerItem.getIdentifier() == 2){
                                gotoTheme();
                            }else if(drawerItem.getIdentifier() == 4){
                                gotoTab();
                            }else if(drawerItem.getIdentifier() == 5){
                                gotoFab();
                            }else if(drawerItem.getIdentifier() == 6){
                                gotoDialog();
                            }else if(drawerItem.getIdentifier() == 8){
                                gotoBottomSheet();
                            }else if(drawerItem.getIdentifier() == 12){
                                gotoTextFragment();
                            }
                        }
                        return false;

                    }
                })
                /*.addStickyDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.logout).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10)
                )*/
                .withSavedInstance(savedInstanceState)
                .build();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        if(extras.getString("bottomsheet") != null && extras.getString("bottomsheet").equalsIgnoreCase("bottomsheet")){
            gotoBottomSheet();
            result.setSelection(8);
        }else {
            gotoDashboard();
        }
        }else {
            gotoDashboard();
        }
        checkDrawerColorExists();





    }



    /**
     * small helper method to reuse the logic to build the AccountHeader
     * this will be used to replace the header of the drawer with a compact/normal header
     *
     * @param compact
     * @param savedInstanceState
     */
    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(
                        profile,

                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.person_unmarried_grey));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        Drawable yourdrawable = menu.findItem(R.id.action_settings).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_IN);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_1:
                //update the profile2 and set a new image.
               /* profile2.withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_android).backgroundColorRes(R.color.accent).sizeDp(48).paddingDp(4));
                headerResult.updateProfileByIdentifier(profile2);*/
                return true;
            case R.id.menu_2:
                //show the arrow icon
                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                return true;
            case R.id.menu_3:
                //show the hamburger icon
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                return true;
            case R.id.menu_4:
                //we want to replace our current header with a compact header
                //build the new compact header
                buildHeader(true, null);
                //set the view to the result
                result.setHeader(headerResult.getView());
                //set the drawer to the header (so it will manage the profile list correctly)
                headerResult.setDrawer(result);
                return true;
            case R.id.menu_5:
                //we want to replace our current header with a normal header
                //build the new compact header
                buildHeader(false, null);
                //set the view to the result
                result.setHeader(headerResult.getView());
                //set the drawer to the header (so it will manage the profile list correctly)
                headerResult.setDrawer(result);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }



    private void gotoDashboard(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new NewDashboard()).commit();
    }
    private void gotoTheme(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ThemeFragment()).commit();
    }

    private void gotoTab(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new TabFragment()).commit();
    }
    //
    private void gotoFab(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new FabFragment()).commit();
    }
    private void gotoBottomSheet(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new BottomSheet()).commit();
    }

    private void gotoTextFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new TextViewFragment()).commit();
    }
    //
    private void gotoDialog(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new DialogFragment()).commit();
    }
    public static void changeDrawerBackground(String color){
        result.getSlider().setBackgroundColor(Color.parseColor(color));
        changeToolBarColor(color);
        result.openDrawer();
        if(sharedpreferences !=null) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("theme_color", color);
            editor.apply();
            editor.commit();
        }
    }

    private static void changeToolBarColor(String color) {
        toolbar.setBackgroundColor(Color.parseColor(color));

    }

    private void checkDrawerColorExists(){
        if(sharedpreferences !=null){
            if(!sharedpreferences.getString("theme_color", "").equalsIgnoreCase("") ){
                result.getSlider().setBackgroundColor(Color.parseColor(sharedpreferences.getString("theme_color", "")));
                toolbar.setBackgroundColor(Color.parseColor(sharedpreferences.getString("theme_color", "")));

            }
        }
    }

    public static void openDrawer(){
        if(result != null){
            result.openDrawer();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }










}