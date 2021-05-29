package com.mtechcomm.nanocreditnative.net;

import com.mtechcomm.nanocreditnative.models.CheckEmailModel;
import com.mtechcomm.nanocreditnative.models.CheckMSISDNmodel;
import com.mtechcomm.nanocreditnative.models.CheckMSISDNresult;
import com.mtechcomm.nanocreditnative.models.LoginUserDetailsModel;
import com.mtechcomm.nanocreditnative.models.LoginUserResponseModel;
import com.mtechcomm.nanocreditnative.models.RegisterDetailsModel;
import com.mtechcomm.nanocreditnative.models.RegisterResponseModel;
import com.mtechcomm.nanocreditnative.models.UpdateLoanDataModel;
import com.mtechcomm.nanocreditnative.models.UpdateUserDataModel;
import com.mtechcomm.nanocreditnative.models.UpdateUserDataResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Mtech_API_Interface {
    @POST("register")
    Call<RegisterResponseModel> registerUser(@Body RegisterDetailsModel registerDetailsModel);

    @POST("login")
    Call<LoginUserResponseModel> loginUser(@Body LoginUserDetailsModel loginUserDetailsModel);

    @POST("checkmsisdn")
    Call<CheckMSISDNresult> checkMSISDN(@Body CheckMSISDNmodel checkMSISDNmodel);

    @POST("checkemail")
    Call<CheckMSISDNresult> checkEmail(@Body CheckEmailModel checkEmailModel);

    @POST("updateuserdata")
    Call<UpdateUserDataResult> updateUserData(@Body UpdateUserDataModel updateUserDataModel);

    @POST("updateloaninfo")
    Call<UpdateUserDataModel> updateLoanInfo(@Body UpdateLoanDataModel updateLoanDataModel);
}
