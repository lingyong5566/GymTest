package com.sp.gymsgalore;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteList extends AppCompatActivity {
    private Cursor model2 = null;
    private ListView list2;
    private DayAdapter adapter2 = null;
    private NoteHelper helper2 = null;
    private TextView empty2 = null;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private boolean showMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notelist);

        empty2 = (TextView)findViewById(R.id.empty2);
        helper2 = new NoteHelper(this);
        list2 = (ListView) findViewById(R.id.list2);
        model2 = helper2.getAll3();
        adapter2 = new DayAdapter(this, model2, 0);
        list2.setOnItemClickListener(onListClick);
        list2.setAdapter(adapter2);

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
                    case R.id.list:
                        Intent listNL = new Intent(NoteList.this, DailyList.class);
                        startActivity(listNL);
                        break;
                    case R.id.music:
                        Intent musicNL = new Intent(NoteList.this, MusicPlayerActivity.class);
                        startActivity(musicNL);
                        break;
                    case R.id.web:
                        Intent webNL = new Intent(NoteList.this, WebViewActivity.class);
                        startActivity(webNL);
                        break;
                    case R.id.alarm:
                        /*Intent alarmWV = new Intent(WebViewActivity.this, MusicActivity.class);
                        startActivity(alarmWV);*/
                        Intent alarmIntent = new Intent(NoteList.this, Alarm.class);
                        startActivity(alarmIntent);
                        System.out.println("we clicked here");
                        break;
                    case R.id.exit:
                        moveTaskToBack(true);
                        System.exit(0);
                        break;
                    default: break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option3, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.addnotes):
                Intent intent1;
                intent1 = new Intent(NoteList.this, NoteActivity.class);
                startActivity(intent1);
                break;
        }
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (model2 != null) {
            model2.close();
        }
        model2 = helper2.getAll3();

        if (model2.getCount() > 0){
            empty2.setVisibility(View.INVISIBLE);
        }
        adapter2.swapCursor(model2);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        helper2.close();
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            model2.moveToPosition(i);
            String recordID = helper2.getID2(model2);
            Intent intent;
            intent = new Intent(NoteList.this, NoteActivity.class);
            intent.putExtra("ID", recordID);
            startActivity(intent);
        }
    };

    static class DayHolder {
        private TextView dayName = null;
        private TextView pact = null;
        private ImageView icon = null;

        DayHolder(View row) {
            dayName = (TextView)row.findViewById(R.id.dayName);
            pact = (TextView)row.findViewById(R.id.pNote);
            icon = (ImageView)row.findViewById(R.id.icon2);
        }

        void populateFrom(Cursor c, NoteHelper helper) {
            dayName.setText(helper.getDayGroup(c));
            String temp = helper.getNotes(c);
            pact.setText(temp);

            if (helper.getDayGroup(c).equals("Monday")){
                icon.setImageResource(R.drawable.letter_m);
            } else if (helper.getDayGroup(c).equals("Tuesday")){
                icon.setImageResource(R.drawable.letter_t);
            } else if (helper.getDayGroup(c).equals("Wednesday")){
                icon.setImageResource(R.drawable.letter_w);
            } else if (helper.getDayGroup(c).equals("Thursday")){
                icon.setImageResource(R.drawable.letter_t);
            } else if (helper.getDayGroup(c).equals("Friday")){
                icon.setImageResource(R.drawable.letter_f);
            } else if (helper.getDayGroup(c).equals("Saturday")){
                icon.setImageResource(R.drawable.letter_s);
            } else if (helper.getDayGroup(c).equals("Sunday")){
                icon.setImageResource(R.drawable.letter_s);
            }
        }
    }

    class DayAdapter extends CursorAdapter {
        DayAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor,  flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            DayHolder holder2 = (DayHolder) view.getTag();
            holder2.populateFrom(cursor, helper2);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater2 = getLayoutInflater();
            View row2 = inflater2.inflate(R.layout.row2, parent, false);
            DayHolder holder2 = new DayHolder(row2);
            row2.setTag(holder2);
            return (row2);
        }
    }
}

