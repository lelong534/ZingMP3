package com.example.zingmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private Button stop, next, previous, play;
    private TextView nameSong, timeCurrent, timeEnd;
    private int position;
    private int index = 0;
    private Handler handler;
    ArrayList<Song> songs;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findID();
        addSong();

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

        setTotalTime();
        setCurrentTimeSong();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    seekBar.setProgress(progress);
                    if(mediaPlayer != null){
                        mediaPlayer.seekTo(progress*1000);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index >= 0 && index < songs.size() - 1) {
                    index++;
                } else {
                    index = 0;
                }

                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                mediaPlayer = MediaPlayer.create(MainActivity.this, songs.get(index).getFile());
                mediaPlayer.start();

                nameSong.setText(songs.get(index).getNameSong());
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0 && index < songs.size() - 1) {
                    index--;
                } else {
                    index = 0;
                }

                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                mediaPlayer = MediaPlayer.create(MainActivity.this, songs.get(index).getFile());
                mediaPlayer.start();

                nameSong.setText(songs.get(index).getNameSong());
            }
        });
    }


    private void createMeia() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, songs.get(position).getFile());
    }

    private void addSong() {
        Song song1 = new Song("first song", R.raw.song1);
        Song song2 = new Song("second song", R.raw.song2);
        Song song3 = new Song("third song", R.raw.song3);
        songs.add(song1);
        songs.add(song3);
        songs.add(song2);
    }

    private void findID() {
        this.play = findViewById(R.id.playButton);
        this.previous = findViewById(R.id.previousButton);
        this.next = findViewById(R.id.nextButton);
        this.seekBar = findViewById(R.id.seekBar);
        this.nameSong = findViewById(R.id.tenBaiHat);
        this.timeCurrent = findViewById(R.id.currentTime);
        this.timeEnd = findViewById(R.id.endTime);
    }

    public void setTotalTime() {
        int timeSong = mediaPlayer.getDuration();
        String time = String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(timeSong),
                TimeUnit.MILLISECONDS.toSeconds(timeSong) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeSong)));
        timeEnd.setText(time);
    }

    public void setCurrentTimeSong() {
        handler = new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                int currentTime = 0;
                if (mediaPlayer != null) {
                    currentTime = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(currentTime);
                }
                handler.postDelayed(this, 1000);
                String time = String.format("%d:%d",
                        TimeUnit.SECONDS.toMinutes(currentTime),
                        TimeUnit.SECONDS.toSeconds(currentTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(currentTime)));
                timeCurrent.setText(time);
            }
        });
    }
}
