package com.mtechcomm.nanocreditnative;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mtechcomm.nanocreditnative.activities.LoginActivity;
import com.mtechcomm.nanocreditnative.classes.LogOutTimerUtil;
import com.mtechcomm.nanocreditnative.fragments.ApplyForLoanFragment;
import com.mtechcomm.nanocreditnative.fragments.AvaillableDiscountsFragment;
import com.mtechcomm.nanocreditnative.fragments.LoanHistoryFragment;
import com.mtechcomm.nanocreditnative.fragments.LoanSummaryFragment;
import com.mtechcomm.nanocreditnative.fragments.PaymentSchedulleFragment;
import com.mtechcomm.nanocreditnative.fragments.PromotionsFragment;

public class MainActivity extends AppCompatActivity implements LogOutTimerUtil.LogOutListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    BottomNavigationView navigationView;
    FrameLayout frameLayout;
    private ActionBar toolbar;

    // Instantiate all Fragmenmts
    private ApplyForLoanFragment applyForLoanFragment;
    private AvaillableDiscountsFragment availlableDiscountsFragment;
    private LoanHistoryFragment loanHistoryFragment;
    private LoanSummaryFragment loanSummaryFragment;
    private PaymentSchedulleFragment paymentSchedulleFragment;
    private PromotionsFragment promotionsFragment;

    private String fragmentName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the intent from the activity
//        Bundle bundle = getIntent().getBundleExtra("bundle");
//        fragmentName = bundle.getString("fragment_id");

        //Retrieve The Choice Made
        fragmentName = retrieveChoice();

        navigationView = findViewById(R.id.homeBottomNavigationView);
        frameLayout = findViewById(R.id.homeFrameLayout);

        toolbar = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        Log.d("nano **************", "onCreate: Fragment Name : " + fragmentName);
        //Initialize all Fragments
        applyForLoanFragment = new ApplyForLoanFragment();

        availlableDiscountsFragment = new AvaillableDiscountsFragment();
        loanHistoryFragment = new LoanHistoryFragment();
        loanSummaryFragment = new LoanSummaryFragment();
        paymentSchedulleFragment = new PaymentSchedulleFragment();
        promotionsFragment = new PromotionsFragment();

        // Initialize the Apply for Loan Fragment
//        toolbar.setTitle("Apply For Loan");
//        InitializeFragment(applyForLoanFragment);

        switch (fragmentName){

            case "loan_payment" :
                InitializeFragment(paymentSchedulleFragment);
                toolbar.setTitle("Loan Payment Schedule");
                break;

            case "availlable_discounts" :
                InitializeFragment(availlableDiscountsFragment);
                toolbar.setTitle("Available Discounts");
                break;

            case "loan_summary" :
                InitializeFragment(loanSummaryFragment);
                toolbar.setTitle("Loan Summary");
                break;

            case "loan_history" :
                InitializeFragment(loanHistoryFragment);
                toolbar.setTitle("Loan History");
                break;

            case "year_to_date" :
                InitializeFragment(promotionsFragment);
                toolbar.setTitle("Year to Date");
                break;

                default:
                    InitializeFragment(applyForLoanFragment);
                    toolbar.setTitle("Apply For Loan");
                    break;
        }
//        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//                switch (menuItem.getItemId()){
//
//                    case R.id.apply_for_loan_menu_item :
//                        // Take care
//                        InitializeFragment(applyForLoanFragment);
//                        toolbar.setTitle("Apply For Loan");
//                        return true;
//
//                    case R.id.payment_schedule_menu_item :
//                        InitializeFragment(paymentSchedulleFragment);
//                        toolbar.setTitle("Loan Payment Schedule");
//                        return true;
//
//                    case R.id.availlable_discount_menu_item :
//                        InitializeFragment(availlableDiscountsFragment);
//                        toolbar.setTitle("Available Discounts");
//                        return  true;
//
//                    case R.id.loan_summary_menu_item :
//                        InitializeFragment(loanSummaryFragment);
//                        toolbar.setTitle("Loan Summary");
//                        return  true;
//
//                    case R.id.loan_history_menu_item :
//                        InitializeFragment(loanHistoryFragment);
//                        toolbar.setTitle("Loan History");
//                        return  true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogOutTimerUtil.startLogoutTimer(this, this);
        Log.e(TAG, "OnStart () &&& Starting timer");
    }

    private String retrieveChoice() {
        SharedPreferences preferences = getSharedPreferences("current_choice", Context.MODE_PRIVATE);
        String choice = preferences.getString("fragment_id", "");
        return choice;
    }

    private void InitializeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.homeFrameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        applyForLoanFragment.onActivityResult(requestCode,resultCode,data);
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            if (fragment.)
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
    }

    @Override
    public void doLogout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        LogOutTimerUtil.startLogoutTimer(this, this);
        Log.e(TAG, "User interacting with screen");
    }

}
