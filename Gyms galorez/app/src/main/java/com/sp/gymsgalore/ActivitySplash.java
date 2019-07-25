package com.sp.gymsgalore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;

public class ActivitySplash extends AppCompatActivity {

    private static int SPLASH_TIME = 4000; //This is 4 seconds
    private TextToSpeech textToSpeechSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(ActivitySplash.this, DailyList.class);
                startActivity(mySuperIntent);
                /* This 'finish()' is for exiting the app when back button pressed
                *  from Home page which is ActivityHome
                */
                finish();
            }
        }, SPLASH_TIME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textToSpeechSystem = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechSystem.setSpeechRate(0.6f);
                    String textToSay = "Welcome to Gyms Galore!";
                    textToSpeechSystem.speak(textToSay, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });
    }
}
