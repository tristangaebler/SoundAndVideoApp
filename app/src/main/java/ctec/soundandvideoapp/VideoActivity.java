package ctec.soundandvideoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.*;
import android.net.Uri;

public class VideoActivity extends Activity {

    private VideoView myPlayer;
    private Button returnButton;
    private MediaController myVideoController;
    private Uri videoLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        myPlayer = (VideoView) findViewById(R.id.videoView);
        returnButton = (Button) findViewById(R.id.homeButton);

        videoLocation = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ultimateskyrim);
        myVideoController = new MediaController(this);
        //Prepare the video
        setUpMedia();
        setUpListeners();
    }

    private void setUpMedia() {
        myPlayer.setMediaController(myVideoController);
        myPlayer.setVideoURI(videoLocation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video, menu);
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

    private void setUpListeners() {
        returnButton.setOnClickListener(new View.OnClickListener(){
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();
        });
    }
}
