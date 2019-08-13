package com.yakamozapp.piano.Login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yakamozapp.piano.Helper.MyTextview;
import com.yakamozapp.piano.MainActivity;
import com.yakamozapp.piano.Network.Api;
import com.yakamozapp.piano.Network.NetworkInterface;
import com.yakamozapp.piano.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {

    private Typeface sans;
    EditText numEt;
    private View progressOverlay;
    private View sendBtn;
    private Dialog alertDialogBaze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressOverlay = findViewById(R.id.progress_overlay);


        sans = Typeface.createFromAsset(getAssets(), "font/sans.ttf");

        numEt = findViewById(R.id.numEt);
        numEt.setTypeface(sans);
        numEt.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(this, R.drawable.ic_smartphone_call), null);


        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });


    }

    public void register(){

        if (numEt.getText().toString().equals("")){
            showError("شماره خود را وارد کنید");

        }

        else {

            progressOverlay.setVisibility(View.VISIBLE);
            NetworkInterface service = Api.getClient(Login_Activity.this).create(NetworkInterface.class);
            Call<JsonElement> call = service.checkUser(numEt.getText().toString());
            call.enqueue(new Callback<JsonElement>() {

                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    progressOverlay.setVisibility(View.INVISIBLE);
                    JsonObject resObj = response.body().getAsJsonObject();

                    if (resObj.get("user").toString().equals("0")){
                        verifyAlert();
                        Log.i("", "onResponse: ");
                    }

                    else {
                        Log.i("", "onResponse: ");

                    }


                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    progressOverlay.setVisibility(View.GONE);
                    showError("مشکلی پیش آمد مجددا تلاش کنید");
                }
            });


        }


    }

    public void verify(String code){

        if (code.equals("")){
            showError("کد را وارد کنید");

        }

        else {

            progressOverlay.setVisibility(View.VISIBLE);
            NetworkInterface service = Api.getClient(Login_Activity.this).create(NetworkInterface.class);
            Call<JsonElement> call = service.verify(code,numEt.getText().toString());
            call.enqueue(new Callback<JsonElement>() {

                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    progressOverlay.setVisibility(View.INVISIBLE);
                    JsonObject resObj = response.body().getAsJsonObject();

                    if (resObj.get("result").toString().equals("0")){
                       showError(resObj.get("message").toString());
                    }

                    else {
                        Log.i("", "onResponse: ");

                    }


                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    progressOverlay.setVisibility(View.GONE);
                    showError("مشکلی پیش آمد مجددا تلاش کنید");
                }
            });


        }


    }


    public void showError(String e) {
        Toast toast = Toast.makeText(Login_Activity.this, e, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTypeface(sans);
        toast.show();


    }

    public void verifyAlert() {

        alertDialogBaze = new Dialog(Login_Activity.this);
        alertDialogBaze.getWindow().setDimAmount(0.8f);
        alertDialogBaze.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialogBaze.setContentView(R.layout.verify_alert);
        alertDialogBaze.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        MyTextview textTxt = alertDialogBaze.findViewById(R.id.textTxt);
        textTxt.setText("کد ارسالی به شماره موبایلتان را وارد کنید"+"\n"+numEt.getText().toString());

        final EditText codeEt = alertDialogBaze.findViewById(R.id.codeEt);
        codeEt.setTypeface(sans);
        codeEt.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(this, R.drawable.binary_gray), null);

        Button submitBtn = alertDialogBaze.findViewById(R.id.submitBtn);
        submitBtn.setTypeface(sans);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verify(codeEt.getText().toString());
            }
        });


        Button resendBtn = alertDialogBaze.findViewById(R.id.resendBtn);
        resendBtn.setTypeface(sans);
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        alertDialogBaze.show();

    }
}