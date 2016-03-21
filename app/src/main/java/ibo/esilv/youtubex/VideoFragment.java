package ibo.esilv.youtubex;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.w3c.dom.Text;

import ibo.esilv.youtubex.dataClass.items;

/**
 * Created by Mcas on 12/03/2016.
 */

public class VideoFragment extends YouTubePlayerSupportFragment{

    private YouTubePlayer activePlayer;

    private items item;


    public void setItem(items item)
    {
        this.item = item;
    }



    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }


    public void init() {

        initialize(Config.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
            }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                activePlayer = player;
                if(activePlayer.isPlaying())
                {
                    activePlayer.release();
                }
                activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                if (!wasRestored) {
                    activePlayer.cueVideo(item.getId().getVideoId());
                }
            }
        });

    }

    public void delete()
    {
        if(activePlayer != null)
        {
            activePlayer.release();
        }
    }



}
