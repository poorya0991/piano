package com.yakamozapp.piano.Login;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yakamozapp.piano.R;

public class SetPass extends AppCompatActivity {

    private Typeface sans;
    EditText passEt,pass2Et;
    Button notBtn;
    View sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass);

        sans = Typeface.createFromAsset(getAssets(), "font/sans.ttf");

        passEt = findViewById(R.id.passEt);
        passEt.setTypeface(sans);

        pass2Et = findViewById(R.id.pass2Et);
        pass2Et.setTypeface(sans);

        notBtn = findViewById(R.id.notBtn);
        notBtn.setTypeface(sans);

        sendBtn = findViewById(R.id.sendBtn);



    }
}
