package com.sp.gymsgalore;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alarm extends AppCompatActivity {

    int counter = 0;



    public void updateDb(String time , String desc, String alarmId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        try{
            JSONObject newJson = new JSONObject();
            newJson.put("time" , time);
            newJson.put("desc" , desc);
            myRef.child("users").child(alarmId).setValue(newJson.toString());
            readDb();
        }
        catch (Exception e){

        }
    }

    public void readDb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                        String value = dataSnapshot.getValue(String.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Map<String , Object> allAlarms = (Map <String , Object >) map.get("users");
                Log.d("CREATION", "Value is: " + map.toString());
                counter = allAlarms.size();
                System.out.println("Counter : " + counter);

                ListView listview = (ListView) findViewById(R.id.listAlarm);

                String[] ListElements = new String[] {
                        "Android",
                        "PHP"
                };

                ArrayList< String > asd  = new ArrayList< String >();

                for(int i = 0 ; i < counter ; i++){

                    System.out.println("allAlarms : " + allAlarms.toString());
                    System.out.println("alarm" + (i + 1) + " " + allAlarms.get("alarm" + (i + 1)).toString());
                    try{
                        JSONObject alarmObj =  new JSONObject(allAlarms.get("alarm" + (i + 1)).toString());
                        asd.add( alarmObj.get("time") + "   |    " + alarmObj.get("desc"));
                    }
                    catch(Exception e){

                    }

                }

//                List< String > ListElementsArrayList = new ArrayList< String >
//                        (Arrays.asList(ListElements));
                List< String > ListElementsArrayList = asd;

                ArrayAdapter< String > adapter = new ArrayAdapter < String >
                        (Alarm.this, android.R.layout.simple_list_item_1,
                                ListElementsArrayList);

                listview.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("We are in alarm class");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i("CREATION","We are in alarm class");

        final Button button = (Button) findViewById(R.id.btnAddAlarm);

        readDb();


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                readDb();
//                updateDb("15:30" , "For GYM" , "alarm1");
//                updateDb("14:30" , "For Class", "alarm2");
                EditText chooseTime = findViewById(R.id.timePicker);
                EditText tbDesc = findViewById(R.id.tbDesc);
                updateDb(chooseTime.getText().toString() , tbDesc.getText().toString() , "alarm" + (counter + 1));
                readDb();
                // Time Picker Implementation

//                EditText chooseTime = findViewById(R.id.timePicker);
//
//                chooseTime.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(Alarm.this, new TimePickerDialog.OnTimeSetListener() {
//                    EditText chooseTime = findViewById(R.id.timePicker);
//                    @Override
//
//                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
//                        chooseTime.setText(hourOfDay + ":" + minutes);
//                    }
//                }, 0, 0, false);
//
//                timePickerDialog.show();


            }
        });


    }

}
