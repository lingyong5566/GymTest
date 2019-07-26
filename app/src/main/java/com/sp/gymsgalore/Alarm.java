package com.sp.gymsgalore;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    ArrayList< String > keyArray;

    public void updateRealDb(String time , String desc, String alarmId) {
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



    public void updateDb(String time , String desc, String alarmId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        System.out.println("keyArray : " + keyArray.toString());
        boolean overall = false;
        for(int i = 0 ; i < keyArray.size() ; i++){
            boolean found = false;
            for(int j = 0 ; j < keyArray.size() ; j++){
                System.out.println("compare 1 : " + keyArray.get(j));
                System.out.println("compare 2 : " + ("alarm" + i));
                System.out.println("compare 3 : " + keyArray.get(j).equals("alarm" + i));
                if(keyArray.get(j).equals("alarm" + i)){
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println("compare 4 : set id to alarm"+ i);
                alarmId = "alarm"+ i;
                overall = true;
            }
        }
        if(!overall){
            alarmId = "alarm" + (keyArray.size());
        }


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

    public void deleteDb(String position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        String[] separated = position.split("   |   ");
        System.out.println("trying to delete : " + separated[0]);
        try{
            myRef.child("users").child(separated[0]).removeValue();
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
                Map<String , Object> allAlarms;

                try{
                    allAlarms = (Map <String , Object >) map.get("users");
                }
                catch(Exception e){
                    return;
                }

                Log.d("CREATION", "Value is: " + map.toString());
                counter = allAlarms.size();

                ListView listview = (ListView) findViewById(R.id.listAlarm);
                ArrayList< String > asd  = new ArrayList< String >();
                keyArray  = new ArrayList< String >();
                for ( String key : allAlarms.keySet() ) {
                    keyArray.add(key);
                    System.out.println( key );
                    try{
                        JSONObject alarmObj =  new JSONObject((String)allAlarms.get(key));
                        asd.add( key + "   |   " + alarmObj.get("time") + "   |   " + alarmObj.get("desc"));

                        List< String > ListElementsArrayList = asd;

                        ArrayAdapter< String > adapter = new ArrayAdapter < String >
                                (Alarm.this, android.R.layout.simple_list_item_1,
                                        ListElementsArrayList);

                        listview.setAdapter(adapter);
                    }
                    catch(Exception e){

                    }
                }
//                System.out.println("Counter : " + counter);


//                for(int i = 0 ; i < counter ; i++){
//                    System.out.println("Counter i : " + i);
//                    System.out.println("allAlarms : " + allAlarms.toString());
//                    System.out.println("alarm" + (i + 1) + " " + allAlarms.get("alarm" + (i + 1)).toString());
//                    try{
//                        JSONObject alarmObj =  new JSONObject(allAlarms.get("alarm" + (i + 1)).toString());
//                        asd.add( alarmObj.get("time") + "   |    " + alarmObj.get("desc"));
//
//                        List< String > ListElementsArrayList = asd;
//
//                        ArrayAdapter< String > adapter = new ArrayAdapter < String >
//                                (Alarm.this, android.R.layout.simple_list_item_1,
//                                        ListElementsArrayList);
//
//                        listview.setAdapter(adapter);
//                    }
//                    catch(Exception e){
//
//                    }
//
//                }

//                List< String > ListElementsArrayList = new ArrayList< String >
//                        (Arrays.asList(ListElements));



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void showItem(String position){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final String pos = position;

        CheckBox chkbox = (CheckBox) findViewById(R.id.checkBox);

        alert.setTitle("Edit Alarm");

        if(chkbox.isChecked()){
            alert.setMessage("Time");
        }
        else{
            alert.setMessage("Description");
        }


        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if(((CheckBox) findViewById(R.id.checkBox)).isChecked()){
                    System.out.println("input.getText().toString() : " + input.getText().toString());
                    String[] separated = pos.split("   |   ");

                    updateRealDb(input.getText().toString() , separated[4] , separated[0]);
                }
                else{
                    System.out.println("input.getText().toString() : " + input.getText().toString());
                    String[] separated = pos.split("   |   ");

                    updateRealDb(separated[2] , input.getText().toString() , separated[0]);
                }



                // Do something with value!
            }
        });

        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                deleteDb(pos);
            }
        });

        alert.show();
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

        final ListView listview = (ListView) findViewById(R.id.listAlarm);

        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = listview.getItemAtPosition(position);
                String str=(String)o;//As you are using Default String Adapter
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
                showItem(str);
            }
        });

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
