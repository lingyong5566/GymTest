package com.sp.gymsgalore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class DailyList extends AppCompatActivity {
    private EditText dayName;
    private RadioGroup repetitions;
    private EditText secActivities;
    private EditText priActivities;
    private Button buttonSave;
    private boolean showMenu = false;
    private EditText priTimings;
    private EditText secTimings;
    private EditText priReps;
    private EditText secReps;

    private List<Restaurant> model = new ArrayList <Restaurant>();
    private ListView list;
    private RestaurantAdapter adapter = null;
    private TabHost host;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dayName = (EditText) findViewById(R.id.day_name); //former restaurant_name
        //repetitions = (RadioGroup) findViewById(R.id.repetitions); //former restaurant_types
        priActivities = (EditText) findViewById(R.id.pri_activities); //former restaurant_address
        secActivities = (EditText) findViewById(R.id.sec_activities); //former restaurant_tel
        priTimings = (EditText) findViewById(R.id.pri_timing);
        secTimings = (EditText) findViewById(R.id.sec_timing);
        priReps = (EditText) findViewById(R.id.pri_repetitions);
        secReps = (EditText) findViewById(R.id.secondary_repetitions);

        buttonSave = (Button) findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);

        list = (ListView) findViewById(R.id.list);
        adapter = new RestaurantAdapter();
        list.setAdapter(adapter);

        host = (TabHost)findViewById(R.id.tabHost);

        host.setup();
        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("List");
        spec.setContent(R.id.day_tab);
        spec.setIndicator("List");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Details");
        spec.setContent(R.id.details_tab);
        spec.setIndicator("Details");
        host.addTab(spec);
        host.setCurrentTab(1);

        list.setOnItemClickListener(onListClick);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    protected void onStart() {
        invalidateOptionsMenu();
        super.onStart();
    }

    @Override
    public void invalidateOptionsMenu() {
        if (host.getCurrentTab() == 0) {
            showMenu = false;
        } else if (host.getCurrentTab() == 1) {
            showMenu = true;
        }
        super.invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.option, menu);
            if(showMenu == true)
                return true;
            else
                return false;
            }

            @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case (R.id.music):
                    Intent intent2;
                    intent2 = new Intent(DailyList.this, MusicActivity.class);
                    startActivity(intent2);
                    break;
                case (R.id.notes):
                    Intent intent;
                    intent = new Intent(DailyList.this, NoteActivity.class);
                    startActivity(intent);
                    break;
                }
            return super.onOptionsItemSelected(item);
            }

    private View.OnClickListener onSave = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            // To read data from restaurantName EditText
            String nameStr = dayName.getText().toString();
            String pActStr = priActivities.getText().toString();
            String sActStr = secActivities.getText().toString();
            String pTimeStr = priTimings.getText().toString();
            String sTimeStr = secTimings.getText().toString();
            String pRepsStr = priReps.getText().toString();
            String sRepsStr = secReps.getText().toString();

            String combineStr = nameStr + "\n" + pActStr+ "\n" + sActStr + "\n" + pTimeStr + "\n" + sTimeStr
                    + "\n" + pRepsStr + "\n" + sRepsStr;
          //Toast.makeText(v.getContext(),combineStr, Toast.LENGTH_LONG).show();

            Restaurant restaurant = new Restaurant();

            restaurant.setDay(nameStr);
            restaurant.setPrimaryActivites(pActStr);
            restaurant.setSecondaryActivities(sActStr);
            restaurant.setPrimaryTimings(pTimeStr);
            restaurant.setSecondaryTimings(sTimeStr);
            restaurant.setPrimaryReps(pRepsStr);
            restaurant.setSecondaryReps(sRepsStr);
            //restaurant.setRepetitions(reps);

            adapter.add(restaurant);
            host.setCurrentTab(0);
        }
    };
    AdapterView.OnItemClickListener onListClick = new
            AdapterView.OnItemClickListener() {
     @Override
     public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                         Restaurant r = model.get(position);

                         dayName.setText(r.getDay());
                         priActivities.setText(r.getPrimaryActivites());
                         secActivities.setText(r.getSecondaryActivities());
                         priTimings.setText(r.getPrimaryTimings());
                         secTimings.setText(r.getSecondaryTimings());
                         priReps.setText(r.getPrimaryReps());
                         secReps.setText(r.getSecondaryReps());

                         host.setCurrentTab(1);
                         }
        };

    static class RestaurantHolder {
        private TextView dayName = null;
        private TextView priAct = null;

        private ImageView icon = null;
        RestaurantHolder(View row) {
            dayName = (TextView)row.findViewById(R.id.dName);
            priAct = (TextView)row.findViewById(R.id.pAct);
            icon = (ImageView)row.findViewById(R.id.icon);
        }

        void populateFrom(Restaurant r) {
                 dayName.setText(r.getDay());
                 priAct.setText(r.getPrimaryActivites()+ ", " + r.getPrimaryReps() + ", " + r.getPrimaryTimings() +
                         ", " + r.getSecondaryActivities() + ", " + r.getSecondaryReps() + ", " + r.getSecondaryTimings());
                 if (r.getRepetitions().equals("Chinese")) {
                     icon.setImageResource(R.drawable.ball_red);
                     } else if (r.getRepetitions().equals("Western")) {
                     icon.setImageResource(R.drawable.ball_yellow);
                     } else {
                     icon.setImageResource(R.drawable.ball_green);
                     }
                }
             }
            class RestaurantAdapter extends ArrayAdapter<Restaurant> {
                 RestaurantAdapter() {
                 super(DailyList.this,R.layout.row, model);
                 }

         @NonNull
     @Override
         public View getView(int position, @Nullable View convertView, @NonNull
                ViewGroup parent) {
                 View row = convertView;
                 RestaurantHolder holder;
                 if (row == null) {
                     LayoutInflater inflater = getLayoutInflater();
                     row = inflater.inflate(R.layout.row, parent, false);
                     holder = new RestaurantHolder(row);
                     row.setTag(holder);
                     } else {
                 holder = (RestaurantHolder)row.getTag();
                 }
                 holder.populateFrom(model.get(position));
                 return (row);
                 }
     }
}

