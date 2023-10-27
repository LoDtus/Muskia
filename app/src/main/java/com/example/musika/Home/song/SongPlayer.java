package com.example.musika.Home.song;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musika.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SongPlayer extends AppCompatActivity {

    private TextView nameSong, nameArtist, currentTime, durationTime;
    private ImageView like, pause, posterPlayer;
    private boolean isLike;
    private SeekBar timelineSong;
    private MediaPlayer songPlaying = new MediaPlayer();
    private Handler handler = new Handler();
    private String SongID;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        // Nhận dữ liệu bài hát:
        nameSong = (TextView) findViewById(R.id.nameSong_xml);
        nameArtist = (TextView) findViewById(R.id.nameArtist_xml);
        posterPlayer = (ImageView) findViewById(R.id.posterPlayer_xml);

        Bundle bundle = getIntent().getExtras();
        if (bundle==null) {
            return;
        }
        SongID = (String) bundle.get("SongID");

        // Set timeline:
        pause = (ImageView) findViewById(R.id.pause_xml);
        currentTime = (TextView) findViewById(R.id.currentTime_xml);
        durationTime = (TextView) findViewById(R.id.durationTime_xml);
        timelineSong = (SeekBar) findViewById(R.id.timelineSong_xml);
        timelineSong.setMax(100);

        // set sự kiện phát nhạc:
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songPlaying.isPlaying()) {
                    handler.removeCallbacks(updater);
                    songPlaying.pause();
                    pause.setImageResource(R.drawable.play_button_arrowhead_1);
                } else {
                    songPlaying.start();
                    pause.setImageResource(R.drawable.pause_2);
                    updateTimeline();
                }
            }
        });
        prepareSong();

        // kéo thả Timeline:
        timelineSong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (songPlaying.getDuration() / 100) * seekBar.getProgress();
                songPlaying.seekTo(playPosition);
                currentTime.setText(millisec_toTimer(songPlaying.getCurrentPosition()));
                return false;
            }
        });

        // set quá trình Load nhạc:
        songPlaying.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                timelineSong.setSecondaryProgress(percent);
            }
        });

        // Nếu bài hát hết, bài hát được phát lại từ đầu:
        songPlaying.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                timelineSong.setProgress(0);
                pause.setImageResource(R.drawable.play_button_arrowhead_1);
                songPlaying.reset();
                prepareSong();
            }
        });

        // Set Like:
        like = (ImageView) findViewById(R.id.notLike_xml);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLike) {
                    isLike = true;
                    like.setColorFilter(Color.rgb(249, 89, 95));
                    like.setImageDrawable(getResources().getDrawable(R.drawable.heart));
                }
                else {
                    isLike = false;
                    like.setColorFilter(null);
                    like.setImageDrawable(getResources().getDrawable(R.drawable.heart1));
                }
            }
        }); //======================================================================================
    }

    private void prepareSong() {

            databaseReference.child("Song").child(SongID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final String getURL = snapshot.child("url").getValue().toString();
                    final String getPoster = snapshot.child("poster").getValue().toString();
                    final String getSong = snapshot.child("song").getValue().toString();
                    final String getArtist = snapshot.child("artist").getValue().toString();

                    Picasso.get().load(getPoster).into(posterPlayer);
                    nameSong.setText(getSong);
                    nameArtist.setText(getArtist);

                    try {
                        songPlaying.setDataSource(getURL);
                        songPlaying.prepare();
                        durationTime.setText(millisec_toTimer(songPlaying.getDuration()));
                    } catch (Exception exception) {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateTimeline();
            long currentDuration = songPlaying.getCurrentPosition();
            currentTime.setText(millisec_toTimer(currentDuration));
        }
    };

    private void updateTimeline() {
        if (songPlaying.isPlaying()) {
            timelineSong.setProgress((int) (  ((float) songPlaying.getCurrentPosition() / songPlaying.getDuration()) * 100 ));
            handler.postDelayed(updater, 1000);
        }
    }

    private String millisec_toTimer(long millisec) {
        String timerString = "";
        String secString;

        int hours = (int) (millisec / (1000 * 60 * 60));
        int minutes = (int) (millisec % (1000 * 60 *60)) / (1000 * 60);
        int sec = (int) ((millisec % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours>0) {
            timerString = hours + ":";
        }

        if (sec<10) {
            secString = "0" + sec;
        } else {
            secString = "" + sec;
        }

        timerString = timerString + minutes + ":" + secString;
        return timerString;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        timelineSong.setProgress(0);
        pause.setImageResource(R.drawable.play_button_arrowhead_1);
        songPlaying.reset();
        prepareSong();
    }
}