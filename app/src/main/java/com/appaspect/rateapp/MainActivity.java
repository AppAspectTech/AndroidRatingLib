package com.appaspect.rateapp;
/**
 * Created by AppAspect on 25/12/18.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appaspect.rateapp.ratinglibrary.RateAppPopUp;
import com.appaspect.rateapp.ratinglibrary.RateAppPopUp_Data;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RateAppPopUp rateAppPopUp = new RateAppPopUp(this,"darasaini1312@gmail.com");
        rateAppPopUp.setTitle(getString(R.string.app_name))
                .setTheme(RateAppPopUp_Data.THEME_LITE)
                .setRatingRestriction(3) // Market opened if a rating >= 3 is selected
                .showAfter(5);

    }

}
