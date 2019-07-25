package com.sp.gymsgalore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("we clicked here ");
        Log.i("CREATION" , "we clicked here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_list);

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        System.out.println("we clicked here ");
        Log.i("CREATION" , "we clicked here");
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.notes:
                        Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.music:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.web:
                        Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.alarm:
                        /*Intent alarmWV = new Intent(WebViewActivity.this, MusicActivity.class);
                        startActivity(alarmWV);*/
                        Intent alarmIntent = new Intent(MainActivity.this, Alarm.class);
                        startActivity(alarmIntent);
                        System.out.println("we clicked here");
                        break;
                    default:
                        System.out.println("we clicked here " + id);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
