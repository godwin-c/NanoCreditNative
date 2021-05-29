package com.mtechcomm.nanocreditnative.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.mtechcomm.nanocreditnative.NanoCreditNative;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.LogOutTimerUtil;
import com.mtechcomm.nanocreditnative.fragments.CustomerServiceFragment;
import com.mtechcomm.nanocreditnative.fragments.FAQsFragment;
import com.mtechcomm.nanocreditnative.fragments.HomeFragment;
import com.mtechcomm.nanocreditnative.fragments.LegalFragment;
import com.mtechcomm.nanocreditnative.fragments.MyAccountFragment;
import com.mtechcomm.nanocreditnative.fragments.PromotionsFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DashboardActivity extends AppCompatActivity implements LogOutTimerUtil.LogOutListener{

    private DrawerLayout mDrawer;

    private Toolbar toolbar;

    private NavigationView nvDrawer;
    TextView nav_header_username, nav_header_phone_number;
    private ActionBarDrawerToggle drawerToggle;

    private static final String TAG = DashboardActivity.class.getSimpleName();

    private Class currentClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.dash_toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.dash_drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        if (nvDrawer.getHeaderCount() > 0) {

            // avoid NPE by first checking if there is at least one Header View available

            View headerLayout = LayoutInflater.from(this).inflate(R.layout.nav_header, nvDrawer); // nvDrawer.getHeaderView(0);
            nav_header_username = headerLayout.findViewById(R.id.nav_header_username);
            nav_header_phone_number = headerLayout.findViewById(R.id.nav_header_phone_number);
        }

        //5010175299
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        setupFragmentToDisplay(HomeFragment.class);
        currentClass = HomeFragment.class;
        setTitle("Home");


    }

    @Override
    protected void onStart() {
        super.onStart();
        LogOutTimerUtil.startLogoutTimer(this, this);
        Log.e(TAG, "OnStart () &&& Starting timer");
    }

    public void setupNavHeader(AppUser appUser){
        nav_header_username.setText(appUser.getSurname() + " " + appUser.getOthernames());
        nav_header_phone_number.setText(appUser.getPhones().get(0));
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it

        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        selectDrawerItem(menuItem);

                        return true;

                    }

                });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        Class fragmentClass = null;

        switch (menuItem.getItemId()) {

            case R.id.nav_account_fragment:

                fragmentClass = MyAccountFragment.class;
                currentClass = fragmentClass;

                break;

            case R.id.nav_cus_service_fragment:

                fragmentClass = CustomerServiceFragment.class;
                currentClass = fragmentClass;

                break;

            case R.id.nav_promotions_fragment:

                fragmentClass = PromotionsFragment.class;
                currentClass = fragmentClass;

                break;

            case R.id.nav_faq_fragment:

                fragmentClass = FAQsFragment.class;
                currentClass = fragmentClass;

                break;

            case R.id.nav_legal_fragment:

                fragmentClass = LegalFragment.class;
                currentClass = fragmentClass;

                break;

//            case R.id.nav_logout_fragment:
//
//                fragmentClass = currentClass;
//                logoutRequested(true);
//
//                break;

            case R.id.nav_home_fragment:

            default:

                fragmentClass = HomeFragment.class;
                currentClass = fragmentClass;

        }

        setupFragmentToDisplay(fragmentClass);

        // Highlight the selected item has been done by NavigationView

        menuItem.setChecked(true);

        // Set action bar title

        setTitle(menuItem.getTitle());

        // Close the navigation drawer

        mDrawer.closeDrawers();

    }

//    private void logoutRequested(boolean b) {
//        SharedPreferences preferences = getSharedPreferences("logout_requested", Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor prefsEditor = preferences.edit();
//
//        prefsEditor.putBoolean("logout_request", b);
//        prefsEditor.commit();
//
//        storeDate(getDateAndTime());
//    }

    private void storeDate(String date) {
        SharedPreferences preferences = getSharedPreferences("last_login", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString("last_login", date);
        prefsEditor.commit();
    }

    private String getDateAndTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss aaa");
        String todaysDate = (sdf.format(cal.getTime()));
        return todaysDate;
    }

    private void setupFragmentToDisplay(Class fragmentClass) {
        Fragment fragment = null;
        try {

            fragment = (Fragment) fragmentClass.newInstance();

        } catch (Exception e) {

            e.printStackTrace();

        }

        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;


// Insert the fragment by replacing any existing fragment

        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && fragmentManager.findFragmentByTag((fragmentTag)) == null) {
            fragmentManager.beginTransaction().replace(R.id.dashboardContent, fragment).addToBackStack(fragment.getTag()).commit();
        }

        // Insert the fragment by replacing any existing fragment

//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        fragmentManager.beginTransaction().replace(R.id.dashboardContent, fragment).commit();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen(GravityCompat.START)){
            this.mDrawer.closeDrawer(GravityCompat.START);
        }else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1){
                logout(null);
            }else {
                super.onBackPressed();
            }
        }

    }

    public void logout(MenuItem item) {

        if (mDrawer != null){
            mDrawer.closeDrawers();
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logout")
                .setMessage("You are about to logout from Nano Credit")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                        finish();
                    }
                }).show();


//        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Logout")
//                .setContentText("Won't be able to recover this file!")
//                .setConfirmText("Yes,delete it!")
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.dismissWithAnimation();
//                        finish();
//                    }
//                })
//                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.dismissWithAnimation();
//                    }
//                })
//                .show();
//        new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Logout?")
//                .setContentText("You have chosen to logout from NanoCredit!")
//                .setConfirmText("Logout")
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        //requestLogout(false);
//                        sDialog.dismissWithAnimation();
//                        finish();
//                    }
//                })
//                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        //cancelLogout();
//                        sDialog.dismissWithAnimation();
//                    }
//                })
//                .show();
    }

    @Override
    public void doLogout() {
        finish();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        LogOutTimerUtil.startLogoutTimer(this, this);
        Log.e(TAG, "User interacting with screen");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkActivity();
    }

    private void checkActivity(){
        NanoCreditNative nanoCreditAppLifeCycle = (NanoCreditNative)getApplicationContext();
        if(nanoCreditAppLifeCycle.isActivityInForeground()){
            Log.d("TAG","Activity is in foreground");
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.dashboardContent);
//        PhoneNumberSelectFragment
            if (fragment instanceof HomeFragment) {
                HomeFragment homeFragment = (HomeFragment)getSupportFragmentManager().findFragmentById(R.id.homeFrameLayout);
                homeFragment.setupViewPager();
            }
        }
        else
        {
            Log.d("TAG","Activity is in background");
        }
    }
}
