package com.sp.gymsgalore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MusicPlayerActivity extends AppCompatActivity {
    //Toolbar mToolbar;
    PagerAdapter mPagerAdapter;
    TabLayout mTabLayout;
    TabItem musicTabItem;
    ViewPager mViewPager;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player_actual);
        //mToolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(mToolbar);
        //getSupportActionBar().setTitle(getString(R.string.app_name));

        mTabLayout = findViewById(R.id.tabLayout);
        musicTabItem = findViewById(R.id.musicTabItem);
        mViewPager = findViewById(R.id.pager);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount()); // call the pager class
        mViewPager.setAdapter(mPagerAdapter); // set adapter for pager

        // do something when the tab is selected
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition()); // set current tab position

                if(tab.getPosition() == 1){
//                    Toast.makeText(MusicPlayerActivity.this, "Album Tab Selected.", Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() == 2){
//                    Toast.makeText(MusicPlayerActivity.this, "Music Tab Selected.", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(MusicPlayerActivity.this, "Playlist Tab Selected.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

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
                        Intent list1 = new Intent(MusicPlayerActivity.this, DailyList.class);
                        startActivity(list1);
                        break;
                    case R.id.notes:
                        Intent notes2 = new Intent(MusicPlayerActivity.this, NoteList.class);
                        startActivity(notes2);
                        break;
                    case R.id.web:
                        Intent web2 = new Intent(MusicPlayerActivity.this, WebViewActivity.class);
                        startActivity(web2);
                        break;
                    case R.id.alarm:
                        /*Intent alarmWV = new Intent(MusicActivity.this, MusicActivity.class);
                        startActivity(alarmWV);*/
                        Toast.makeText(MusicPlayerActivity.this, "Alarm",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.exit:
                        moveTaskToBack(true );
                        System.exit(0);
                        break;
                    default: break;
                }

                return true;
            }
        });

    }

}
