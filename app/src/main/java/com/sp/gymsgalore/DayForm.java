package com.sp.gymsgalore;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DayForm extends AppCompatActivity {
    private EditText dayName;
    private EditText priTime;
    private EditText priAct;
    private EditText priReps;
    private EditText secTime;
    private EditText secAct;
    private EditText secReps;
    private Button buttonSave;
    private Button buttonDelete;
    //private TextView speechText = null;
    //private String speechNotes;

    private DayHelper helper = null;
    private String dayID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_form);

        dayName = (EditText) findViewById(R.id.day_name);
        priAct = (EditText) findViewById(R.id.pri_activities);
        priReps = (EditText) findViewById(R.id.pri_repetitions);
        priTime = (EditText) findViewById(R.id.pri_timing);
        secAct = (EditText) findViewById(R.id.sec_activities);
        secReps = (EditText) findViewById(R.id.secondary_repetitions);
        secTime = (EditText) findViewById(R.id.sec_timing);
        //speechText = (TextView) findViewById(R.id.speechtext);

        buttonSave = (Button) findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);
        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(onDelete);

        helper = new DayHelper(this);

        dayID = getIntent().getStringExtra("ID");
        //speechNotes = getIntent().getStringExtra("notes");
        //speechText.setText(speechNotes);

        if (dayID != null) {
            load();
        }
    }

    private void load(){
        Cursor c = helper.getById(dayID);
        c.moveToFirst();
        dayName.setText(helper.getDayName(c));
        priAct.setText(helper.getPriAct(c));
        priReps.setText(helper.getPriReps(c));
        priTime.setText(helper.getPriTime(c));
        secAct.setText(helper.getSecAct(c));
        secReps.setText(helper.getSecReps(c));
        secTime.setText(helper.getSecTime(c));
        //speechText.setText(helper.getSpeechText(c));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    View.OnClickListener onSave = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            String nameStr = dayName.getText().toString();
            String pActStr= priAct.getText().toString();
            String pRepsStr= priReps.getText().toString();
            String pTimeStr = priTime.getText().toString();
            String sActStr= secAct.getText().toString();
            String sRepsStr= secReps.getText().toString();
            String sTimeStr = secTime.getText().toString();

            if (dayID == null){
                helper.insert(nameStr, pActStr, pRepsStr, pTimeStr, sActStr, sRepsStr, sTimeStr);
            } else {
                helper.update(dayID, nameStr, pActStr, pRepsStr, pTimeStr, sActStr, sRepsStr, sTimeStr);
            }

            finish();
        }
    };

    View.OnClickListener onDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            String nameStr = dayName.getText().toString();
            String pActStr= priAct.getText().toString();
            String pRepsStr= priReps.getText().toString();
            String pTimeStr = priTime.getText().toString();
            String sActStr= secAct.getText().toString();
            String sRepsStr= secReps.getText().toString();
            String sTimeStr = secTime.getText().toString();

            helper.delete(dayID, nameStr, pActStr, pRepsStr, pTimeStr, sActStr, sRepsStr, sTimeStr);

            finish();
        }
    };
}