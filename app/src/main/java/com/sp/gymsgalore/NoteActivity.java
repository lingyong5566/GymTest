package com.sp.gymsgalore;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {
    private Button buttonSave;
    private EditText notes;
    private RadioGroup dayGroup;
    private NoteHelper helper3 = null;
    private String notesID = "";
    private Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);

        buttonSave = (Button) findViewById(R.id.save);
        buttonSave.setOnClickListener(onSave);
        buttonDelete = (Button) findViewById(R.id.delete);
        buttonDelete.setOnClickListener(onDelete);
        notes = (EditText) findViewById(R.id.notez);
        dayGroup = (RadioGroup) findViewById(R.id.Dayz);

        helper3 = new NoteHelper(this);

        checkPermission();

        final EditText notes = findViewById(R.id.notez);

        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    notes.setText(matches.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        findViewById(R.id.note_button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        notes.setHint("Say what you want");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        notes.setText("");
                        notes.setHint("Listening...");
                        break;
                }
                return false;
            }
        });

        notesID = getIntent().getStringExtra("ID");
        if (notesID != null){
            load();
        }

    }

    private void load() {
        Cursor c = helper3.getById3(notesID);
        c.moveToFirst();
        notes.setText(helper3.getNotes(c));

        if (helper3.getDayGroup(c).equals("Monday")){
            dayGroup.check(R.id.mon);
        } else if (helper3.getDayGroup(c).equals("Tuesday")){
            dayGroup.check(R.id.tue);
        } else if (helper3.getDayGroup(c).equals("Wednesday")){
            dayGroup.check(R.id.wed);
        } else if (helper3.getDayGroup(c).equals("Thursday")){
            dayGroup.check(R.id.thur);
        } else if (helper3.getDayGroup(c).equals("Friday")){
            dayGroup.check(R.id.fri);
        } else if (helper3.getDayGroup(c).equals("Saturday")){
            dayGroup.check(R.id.sat);
        } else {
            dayGroup.check(R.id.sun);
        }

    }

    View.OnClickListener onSave = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            String nameStr = notes.getText().toString();
            String dayType = " ";

            switch (dayGroup.getCheckedRadioButtonId()) {
                case R.id.mon:
                    dayType = "Monday";
                    break;
                case R.id.tue:
                    dayType = "Tuesday";
                    break;
                case R.id.wed:
                    dayType = "Wednesday";
                    break;
                case R.id.thur:
                    dayType = "Thursday";
                    break;
                case R.id.fri:
                    dayType = "Friday";
                    break;
                case R.id.sat:
                    dayType = "Saturday";
                    break;
                case R.id.sun:
                    dayType = "Sunday";
                    break;
            }

            if (notesID == null){
                helper3.insert3(nameStr, dayType);
            } else {
                helper3.update3(notesID, nameStr, dayType);
            }

            finish();
        }
    };

    View.OnClickListener onDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            String nameStr = notes.getText().toString();
            String dayType = " ";

            switch (dayGroup.getCheckedRadioButtonId()) {
                case R.id.mon:
                    dayType = "Monday";
                    break;
                case R.id.tue:
                    dayType = "Tuesday";
                    break;
                case R.id.wed:
                    dayType = "Wednesday";
                    break;
                case R.id.thur:
                    dayType = "Thursday";
                    break;
                case R.id.fri:
                    dayType = "Friday";
                    break;
                case R.id.sat:
                    dayType = "Saturday";
                    break;
                case R.id.sun:
                    dayType = "Sunday";
                    break;
            }

            helper3.delete3(notesID, nameStr, dayType);

            finish();
        }
    };

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
}
