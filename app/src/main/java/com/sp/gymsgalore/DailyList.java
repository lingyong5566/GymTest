package com.sp.gymsgalore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class DailyList extends AppCompatActivity {
    private Cursor model = null;
    private ListView list;
    private DayAdapter adapter = null;
    private DayHelper helper = null;
    private TextView empty = null;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private boolean showMenu = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_list);

        empty = (TextView)findViewById(R.id.empty);
        helper = new DayHelper(this);
        list = (ListView) findViewById(R.id.list);
        model = helper.getAll();
        adapter = new DayAdapter(this, model, 0);
        list.setOnItemClickListener(onListClick);
        list.setAdapter(adapter);

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.notes:
                        Intent notes1 = new Intent(DailyList.this, NoteList.class);
                        startActivity(notes1);
                        break;
                    case R.id.music:
                        Intent music1 = new Intent(DailyList.this, MusicPlayerActivity.class);
                        startActivity(music1);
                        break;
                    case R.id.web:
                        Intent web1 = new Intent(DailyList.this, WebViewActivity.class);
                        startActivity(web1);
                        break;
                    case R.id.alarm:
                        /*Intent alarmWV = new Intent(WebViewActivity.this, MusicActivity.class);
                        startActivity(alarmWV);*/
                        Intent alarmIntent = new Intent(DailyList.this, Alarm.class);
                        startActivity(alarmIntent);
                        System.out.println("we clicked here");
                        break;
                    case R.id.exit:
                        moveTaskToBack(true);
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (model != null) {
            model.close();
        }
        model = helper.getAll();

        if (model.getCount() > 0){
            empty.setVisibility(View.INVISIBLE);
        }
        adapter.swapCursor(model);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        helper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.add):
                Intent intent1;
                intent1 = new Intent(DailyList.this, DayForm.class);
                startActivity(intent1);
                break;
        }

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);

    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            model.moveToPosition(i);
            String recordID = helper.getID(model);
            Intent intent;
            intent = new Intent(DailyList.this, DayForm.class);
            intent.putExtra("ID", recordID);
            startActivity(intent);
        }
    };

    static class DayHolder {
        private TextView dayName = null;
        private TextView pact = null;
        private ImageView icon = null;
        private TextView sact = null;

        DayHolder(View row) {
            dayName = (TextView)row.findViewById(R.id.dName);
            pact = (TextView)row.findViewById(R.id.pAct);
            sact = (TextView)row.findViewById(R.id.sAct);
            icon = (ImageView)row.findViewById(R.id.icon);
        }

        void populateFrom(Cursor c, DayHelper helper) {
            dayName.setText(helper.getDayName(c));
            String temp = helper.getPriAct(c)+ ", " + helper.getPriReps(c) + ", " + helper.getPriTime(c);
            pact.setText(temp);
            String temp2 = helper.getSecAct(c)+ ", " + helper.getSecReps(c) + ", " + helper.getSecTime(c);
            sact.setText(temp2);

            icon.setImageResource(R.drawable.ball_green);
        }
    }

    class DayAdapter extends CursorAdapter {
        DayAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor,  flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            DayHolder holder = (DayHolder) view.getTag();
            holder.populateFrom(cursor, helper);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            DayHolder holder = new DayHolder(row);
            row.setTag(holder);
            return (row);
        }
    }
}

