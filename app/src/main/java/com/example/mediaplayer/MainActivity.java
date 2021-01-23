package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar skVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.takebackthepower);

        configSeekVolume();

    }

    private void configSeekVolume(){
        skVolume = findViewById(R.id.skVolume);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //recuperar o valor de volume máximo e atual
        int volumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        skVolume.setMax(volumeMax);
        skVolume.setProgress(volumeAtual);

        skVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void playMusic(View view){
        if ( mediaPlayer != null ){
            mediaPlayer.start();
        }
    }

    public void pauseMusic( View view ){
        if ( mediaPlayer.isPlaying() ){
            mediaPlayer.pause();
        }
    }

    public void stopMusic( View view ){
        if ( mediaPlayer.isPlaying() ){
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.takebackthepower);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mediaPlayer.isPlaying() ){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if ( mediaPlayer != null ){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( mediaPlayer != null && mediaPlayer.isPlaying() ){
            mediaPlayer.stop();
            //libera espaço de memória
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}