package com.yakamozapp.piano.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yakamozapp.piano.Helper.MyTextview;
import com.yakamozapp.piano.Main2Activity;
import com.yakamozapp.piano.Network.Api;
import com.yakamozapp.piano.Network.NetworkInterface;
import com.yakamozapp.piano.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private Typeface sans;
    EditText numEt,passEt;
    View sendBtn;
    MyTextview forgetTxt;
    private View progressOverlay;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        progressOverlay = findViewById(R.id.progress_overlay);
        pref= Login.this.getSharedPreferences("piano", MODE_PRIVATE);


        sans = Typeface.createFromAsset(getAssets(), "font/sans.ttf");

        numEt = findViewById(R.id.numEt);
        numEt.setTypeface(sans);

        passEt = findViewById(R.id.passEt);
        passEt.setTypeface(sans);

        forgetTxt = findViewById(R.id.forgetTxt);
        forgetTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SetPass.class);
                i.putExtra("forget",true);
                startActivity(i);
            }
        });

        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login();
            }
        });
    }


    private void Login(){

        if (numEt.getText() .toString().equals("")||passEt.getText().toString().equals("")){
            showError("لطفا تمامی اطلاعات را وارد کنید");
        }
        else {
            progressOverlay.setVisibility(View.VISIBLE);
            NetworkInterface service = Api.getClient(Login.this).create(NetworkInterface.class);
            Call<JsonElement> call = service.verify(passEt.getText().toString(),numEt.getText().toString());
            call.enqueue(new Callback<JsonElement>() {

                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    progressOverlay.setVisibility(View.INVISIBLE);
                    JsonObject resObj = response.body().getAsJsonObject();

                    if (resObj.get("result").toString().equals("0")){
                        showError(resObj.get("message").toString());
                    }

                    else if (resObj.get("result").toString().equals("1")) {
                        String useid = resObj.get("user_id").toString();
                        pref.edit().putString("userid",useid).apply();

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
        Toast toast = Toast.makeText(Login.this, e, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTypeface(sans);
        toast.show();
    }

}
