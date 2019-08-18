package com.yakamozapp.piano.Login;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yakamozapp.piano.Helper.MyTextview;
import com.yakamozapp.piano.Main2Activity;
import com.yakamozapp.piano.R;

public class Login extends AppCompatActivity {

    private Typeface sans;
    EditText numEt,passEt;
    View sendBtn;
    MyTextview forgetTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

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
                startActivity(i);
            }
        });

        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
