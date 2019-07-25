package com.sp.gymsgalore;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MusicActivity extends AppCompatActivity {
    MediaPlayer player;

    private ImageView musicicon;
    private Button play1;
    private Button play2;
    private Button play3;
    private Button pause1;
    private Button stop1;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);

        musicicon = (ImageView) findViewById(R.id.imageView2);

        play1 = (Button) findViewById(R.id.play1);
        play1.setOnClickListener(onPlay1);

        play2 = (Button) findViewById(R.id.play2);
        play2.setOnClickListener(onPlay2);

        play3 = (Button) findViewById(R.id.play3);
        play3.setOnClickListener(onPlay3);

        /*pause1 = (Button) findViewById(R.id.pause1);
        pause1.setOnClickListener(onPause1);

        stop1 = (Button) findViewById(R.id.stop1);
        stop1.setOnClickListener(onStop1);*/

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
                        Intent list1 = new Intent(MusicActivity.this, DailyList.class);
                        startActivity(list1);
                        break;
                    case R.id.notes:
                        Intent notes2 = new Intent(MusicActivity.this, NoteList.class);
                        startActivity(notes2);
                        break;
                    case R.id.web:
                        Intent web2 = new Intent(MusicActivity.this, WebViewActivity.class);
                        startActivity(web2);
                        break;
                    case R.id.alarm:
                        /*Intent alarmWV = new Intent(WebViewActivity.this, MusicActivity.class);
                        startActivity(alarmWV);*/
                        Intent alarmIntent = new Intent(MusicActivity.this, Alarm.class);
                        startActivity(alarmIntent);
                        System.out.println("we clicked here");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    private View.OnClickListener onPlay1 = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            startPlayer1();
        }
    };

    private View.OnClickListener onPlay2 = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            startPlayer2();
        }
    };

    private View.OnClickListener onPlay3 = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            startPlayer3();
        }
    };

   /* public void play(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.tg);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        player.start();
    }*/

    public void pause(View v) {
        if (player != null) {
            player.pause();
        }
    }

    public void stop(View v) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            musicicon.setImageResource(R.drawable.image);
            Toast.makeText(this, "MediaPlayer stopped", Toast.LENGTH_SHORT).show();
        }
    }

    private void startPlayer1() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.cop);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        musicicon.setImageResource(R.drawable.cop);
        player.start();
    }

    private void startPlayer2() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.ransom);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        musicicon.setImageResource(R.drawable.ransom);
        player.start();
    }

    private void startPlayer3() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.tg);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        musicicon.setImageResource(R.drawable.tg);
        player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}
