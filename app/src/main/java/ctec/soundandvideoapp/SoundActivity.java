package ctec.soundandvideoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.media.MediaPlayer;
import android.widget.*;
import android.app.Activity;
import android.content.Intent;

public class SoundActivity extends Activity implements Runnable {

    private Button startButton;
    private Button stopButton;
    private Button pauseButton;
    private Button videoButton;
    private Button randomSoundButton;
    private MediaPlayer soundPlayer;
    private SeekBar soundBar;
    private Thread soundThread;
    private MediaPlayer kidlaughing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        startButton = (Button) findViewById(R.id.playButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        randomSoundButton = (Button) findViewById(R.id.randomSoundButton);
        soundBar = (SeekBar) findViewById(R.id.soundBar);
        videoButton = (Button) findViewById(R.id.videoButton);
        soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.birdsound);
        kidlaughing = MediaPlayer.create(this.getBaseContext(), R.raw.kidlaughing);

        setUpListeners();

        soundThread = new Thread(this);
        soundThread.start();
    }

    /**
     * Method for the setUpListeners. This responds to the user interacting with the application
     */
    private void setUpListeners() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPlayer.start();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                soundPlayer.pause();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View currentView) {
                soundPlayer.stop();
                soundPlayer = MediaPlayer.create(getBaseContext(), R.raw.birdsound);
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View currentView) {
                Intent myIntent = new Intent(currentView.getContext(), VideoActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        randomSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View currentView) {
                randomSound();
            }
        });

        soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
           @Override
            public void onStopTrackingTouch(SeekBar seekBar){}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                if (fromUser) {
                    soundPlayer.seekTo(progress);
                }
            }
        });


    }

    /**
     * We need this method since we are implemeting Runnable
     * Allows the seekBar to update
     *
     */
    public void run() {
        int currentPosition = 0;
        int soundTotal = soundPlayer.getDuration();
        soundBar.setMax(soundTotal);

        while (soundPlayer != null && currentPosition < soundTotal) {
            try {
                Thread.sleep(300);
                currentPosition = soundPlayer.getCurrentPosition();
            }
            catch (InterruptedException soundException) {
                return;
            }
            catch (Exception otherException) {
                return;
            }
            soundBar.setProgress(currentPosition);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method generates a random number. Depending on that number it plays a certain sound
     */
    public void randomSound() {
        int random = (int) (Math.random() * 6);

        if(random <= 1) {
            soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.lazer);
        }
        else if(random <= 2){
            soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.siren);
        }
        else if(random <= 3) {
            soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.kidlaughing);
        }
        else if(random <= 4) {
            soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.motorcycle);
        }
        else if(random <= 5) {
            soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.thunderstorm);
        }
    }
}
