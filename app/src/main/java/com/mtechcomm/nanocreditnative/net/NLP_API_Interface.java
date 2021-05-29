package com.mtechcomm.nanocreditnative.net;


import com.mtechcomm.nanocreditnative.models.AcceptScoringInput;
import com.mtechcomm.nanocreditnative.models.AcceptScoringModel;
import com.mtechcomm.nanocreditnative.models.AuthenticationResponse;
import com.mtechcomm.nanocreditnative.models.CheckScoreReadyResult;
import com.mtechcomm.nanocreditnative.models.ConfirmLoanModel;
import com.mtechcomm.nanocreditnative.models.CustomerEnquiryResult;
import com.mtechcomm.nanocreditnative.models.CustomerModel;
import com.mtechcomm.nanocreditnative.models.CustomerModelResult;
import com.mtechcomm.nanocreditnative.models.EnquiryModel;
import com.mtechcomm.nanocreditnative.models.LoanEnquiryModelResult;
import com.mtechcomm.nanocreditnative.models.LoanModel;
import com.mtechcomm.nanocreditnative.models.LoanModelResult;
import com.mtechcomm.nanocreditnative.models.LoanOfferModel;
import com.mtechcomm.nanocreditnative.models.LoanOfferResult;
import com.mtechcomm.nanocreditnative.models.PriorityDataModelInput;
import com.mtechcomm.nanocreditnative.models.RepayLoanModel;
import com.mtechcomm.nanocreditnative.models.RepayLoanResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NLP_API_Interface {

    @POST("authentication")
    Call<AuthenticationResponse> getAuthToken();

    // Loan section
    @POST("loan/enquiry")
    Call<LoanEnquiryModelResult> makeLoanEnquiry(@Body EnquiryModel enquiryModel, @Header("Authorization") String auth);

    @POST("loan/application")
    Call<LoanModelResult> requestLoan(@Body LoanModel loanModel, @Header("Authorization") String auth);

    @POST("loan/offer")
    Call<LoanOfferResult> offerLoan(@Body LoanOfferModel loanOfferModel, @Header("Authorization") String auth);

    @POST("loan/confirm")
    Call<LoanOfferResult> confirmLoan(@Body ConfirmLoanModel modelInput, @Header("Authorization") String auth);

    @POST("loan/payment")
    Call<RepayLoanResponseModel> repayLoan(@Body RepayLoanModel repayLoanModel, @Header("Authorization") String auth);

    // Customer Section
    @POST("customer")
    Call<CustomerModelResult> createCustomer(@Body CustomerModel customerModel, @Header("Authorization") String auth);

    @POST("customer/acceptScoring ")
    Call<AcceptScoringModel> acceptScoring(@Body AcceptScoringInput scoringInput, @Header("Authorization") String auth);

    @POST("customer/enquiry")
    Call<CustomerEnquiryResult> makeCustomerEnquiry (@Body EnquiryModel enquiryModel, @Header("Authorization") String auth);

    @POST("loan/priorityData")
    Call<Void> setPriorityData(@Body PriorityDataModelInput modelInput, @Header("Authorization") String auth);

    @POST("loan/scoreReady")
    Call<CheckScoreReadyResult> checkScoreReady(@Body PriorityDataModelInput modelInput, @Header("Authorization") String auth);

}
