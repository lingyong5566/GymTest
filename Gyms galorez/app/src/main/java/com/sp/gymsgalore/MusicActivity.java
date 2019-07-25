package com.sp.gymsgalore;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MusicActivity extends AppCompatActivity {
    MediaPlayer player;

    private Button play1;
    private Button play2;
    private Button play3;
    private Button pause1;
    private Button stop1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);

        play1 = (Button) findViewById(R.id.play1);
        play1.setOnClickListener(onPlay1);

        play2 = (Button) findViewById(R.id.play2);
        play2.setOnClickListener(onPlay2);

        play3 = (Button) findViewById(R.id.button3);
        play3.setOnClickListener(onPlay3);

        /*pause1 = (Button) findViewById(R.id.pause1);
        pause1.setOnClickListener(onPause1);

        stop1 = (Button) findViewById(R.id.stop1);
        stop1.setOnClickListener(onStop1);*/
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
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
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

        player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}
