package com.mtechcomm.nanocreditnative.classes;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

import com.gmail.samehadar.iosdialog.IOSDialog;
import com.mtechcomm.nanocreditnative.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomCallBack<T> implements Callback<T> {
    @SuppressWarnings("unused")
    private static final String TAG = "RetrofitCallback";
//    private ProgressDialog mProgressDialog;
    Context context;
    private final Callback<T> mCallback;

    IOSDialog dialog0;

   // private Dialog progressbar;

    public CustomCallBack(Context context, Callback<T> callback) {
        this.context = context;
        this.mCallback = callback;

        dialog0 = new IOSDialog.Builder(context)
                .setTitle("loading...")
                .setTitleColorRes(R.color.gray)
                .build();

//        progressbar = new Dialog(context);
//
        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        dialog0.setCanceledOnTouchOutside(false);
        dialog0.show();
//
//        progressbar.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
//        progressbar.setdurationTime(50);
//        progressbar.settype(Type.RIPPLE);
//        progressbar.setdurationTime(100);
//        progressbar.show();

//        mProgressDialog = new ProgressDialog(context);
//        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Loading...");
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.show();

    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (dialog0.isShowing()) {
//            mProgressDialog.dismiss();
//            progressbar.gone();
            dialog0.dismiss();
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        mCallback.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (dialog0.isShowing()) {
            dialog0.dismiss();
//            progressbar.gone();
//            mProgressDialog.dismiss();
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
//        NetworkHelper.onFailure(t, (Activity) mContext);
        mCallback.onFailure(call, t);
    }
}
