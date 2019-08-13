package com.yakamozapp.piano;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.yakamozapp.piano.Helper.MyTextview;
import com.yakamozapp.piano.Login.Login_Activity;

public class Main2Activity extends AppCompatActivity {

    private SliderLayout mDemoSlider;
    Button orderBtn;
    private Typeface sans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);


        DefaultSliderView defaultSliderView = new DefaultSliderView(this);
        defaultSliderView
                .image("https://tvfiles.alphacoders.com/100/hdclearart-10.png")
                .setScaleType(BaseSliderView.ScaleType.Fit)
                ;

        mDemoSlider.addSlider(defaultSliderView);

        sans = Typeface.createFromAsset(getAssets(), "font/sans.ttf");


        orderBtn = findViewById(R.id.orderBtn);
        orderBtn.setTypeface(sans);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this, Main2Activity.class);
                startActivity(i);
            }
        });

        MyTextview logTxt = findViewById(R.id.logTxt);
        logTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this, Login_Activity.class);
                startActivity(i);
            }
        });


    }
}
