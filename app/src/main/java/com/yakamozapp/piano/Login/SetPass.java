package com.yakamozapp.piano.Login;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yakamozapp.piano.Helper.MyTextview;
import com.yakamozapp.piano.Network.Api;
import com.yakamozapp.piano.Network.NetworkInterface;
import com.yakamozapp.piano.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPass extends AppCompatActivity {

    private Typeface sans;
    EditText passEt,pass2Et;
    Button notBtn;
    View sendBtn;
    View progressOverlay;
    private SharedPreferences pref;
    boolean forget;
    MyTextview setPassBtnTxt,setPassTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass);

        forget = getIntent().getBooleanExtra("forget",false);

        setPassBtnTxt = findViewById(R.id.setPassBtnTxt);
        setPassTxt = findViewById(R.id.setPassTxt);


        pref= SetPass.this.getSharedPreferences("piano", MODE_PRIVATE);

        sans = Typeface.createFromAsset(getAssets(), "font/sans.ttf");
        progressOverlay = findViewById(R.id.progress_overlay);

        passEt = findViewById(R.id.passEt);
        passEt.setTypeface(sans);

        pass2Et = findViewById(R.id.pass2Et);
        pass2Et.setTypeface(sans);

        notBtn = findViewById(R.id.notBtn);
        notBtn.setTypeface(sans);

        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPass();
            }
        });

        if (forget){
            notBtn.setVisibility(View.INVISIBLE);
            setPassTxt.setText("رمز عبور جدید خودرا وارد کنید");
            setPassBtnTxt.setText("ثبت رمز عبور");
        }
    }

   private void SetPass(){
        if (passEt.getText().toString().equals("")||pass2Et.getText().toString().equals("")){
            showError("لطفا تمامی اطلاعات را وارد نمایید");
            return;
        }

        if (!passEt.getText().toString().equals(pass2Et.getText().toString())){
            showError("رمز عبور وارد شده یکسان نیست");
            return;
        }

        String userId = pref.getString("userid","");
        progressOverlay.setVisibility(View.VISIBLE);
        NetworkInterface service = Api.getClient(SetPass.this).create(NetworkInterface.class);
        Call<JsonElement> call = service.changePass(userId,passEt.getText().toString(),pass2Et.getText().toString());
        call.enqueue(new Callback<JsonElement>() {

            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressOverlay.setVisibility(View.INVISIBLE);
                JsonObject resObj = response.body().getAsJsonObject();

                if (resObj.get("result").toString().equals("0")){

                }

                else if (resObj.get("result").toString().equals("1")) {
                    showError(resObj.get("message").toString());
                    finish();

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressOverlay.setVisibility(View.GONE);
                showError("مشکلی پیش آمد مجددا تلاش کنید");
            }
        });



    }

    public void showError(String e) {
        Toast toast = Toast.makeText(SetPass.this, e, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTypeface(sans);
        toast.show();
    }
}
