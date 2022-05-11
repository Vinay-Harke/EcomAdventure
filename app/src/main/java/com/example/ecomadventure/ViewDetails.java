package com.example.ecomadventure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ViewDetails extends YouTubeBaseActivity {
    private YouTubePlayerView youTubePlayerView;
    private RecyclerView recyclerView;
    int[] imagesId = {R.drawable._009_lamborghini_gallardo_lp560_4_spyder_1280x1024,
            R.drawable._009_lamborghini_insecta_concept_design_1280x1024,
            R.drawable.google_lab_1920x1080,
            R.drawable.marvel_doctor_strange_3840x2160,
            R.drawable.tron_lamborghini_aventador_1920x1080};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        recyclerView = findViewById(R.id.view_details_photos_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ViewDetailsImageAdapter adapter =new ViewDetailsImageAdapter(ViewDetails.this,imagesId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        youTubePlayerView = findViewById(R.id.youtube_player_frame);
        YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("vqsojDj6j_s");
                youTubePlayer.pause();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize("AIzaSyApUIb0QqWpEaLN2p45XGD_5KyXNq9O1Tw",listener);
    }
}