package com.example.contact.apprendrefrancais;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ReadStoryActivity extends Activity {
    boolean ispressed = false;
    String record;
    String descriptionText;
    private static final String TAG_RECROD = "record";
    private static final String TAG_DESCRIPTION = "description";
    MediaPlayer player;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);
        description = (TextView) findViewById(R.id.descriptionStory);
        Intent i = getIntent();
        descriptionText = i.getStringExtra(TAG_DESCRIPTION);
        description.setText(descriptionText);
        record = i.getStringExtra(TAG_RECROD);
        Log.d("bi", record);

        try {
            player = new MediaPlayer();
            player.reset();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource("http://vietnguyen.esy.es/firstapp/AndroidFileUpload/uploads/"+record+".mp3"
            );
            player.prepare();
            player.start();

        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read_story, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.stopStory) {
            if (ispressed == false) {
                player.pause();
                ispressed = true;
                return true;
            }
            if (ispressed == true) {
                player.start();
                ispressed = false;
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
