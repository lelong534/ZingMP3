package com.example.zingmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private Button stop, next, previous, play;
    private TextView tenBaiHat, timeCurrent, timeEnd;
    private int position;
    ArrayList<Song> songs;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.play = findViewById(R.id.playButton);
        this.previous = findViewById(R.id.previousButton);
        this.next = findViewById(R.id.nextButton);
        this.seekBar = findViewById(R.id.seekBar);
        this.tenBaiHat = findViewById(R.id.tenBaiHat);
        this.timeCurrent = findViewById(R.id.currentTime);
        this.timeEnd = findViewById(R.id.endTime);

        createMeia();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                } else {
                    mediaPlayer.start();
                    play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                }
            }
        });
    }

    private void createMeia() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, songs.get(position).getFile());
    }

    private void addSong() {
        Song song1 = new Song("1", R.raw.a);
        Song song2 = new Song("2", R.raw.b);
        Song song3 = new Song("3", R.raw.b);
        songs.add(song1);
        songs.add(song3);
        songs.add(song2);
    }
}
