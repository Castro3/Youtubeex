package ibo.esilv.youtubex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ibo.esilv.youtubex.dataClass.items;

/**
 * Created by Mcas on 13/03/2016.
 */
public class NewFragment extends Fragment {
    private VideoFragment youTubePlayerFragment;
    private TextView titleTx;
    private TextView dateTx;
    private TextView authorTx;
    private TextView desTx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_video, container, false);
        youTubePlayerFragment = new VideoFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.youtube_fragment, youTubePlayerFragment).commit();
        titleTx = (TextView) v.findViewById(R.id.videoTitle);
        dateTx = (TextView) v.findViewById(R.id.videoDate);
        authorTx = (TextView) v.findViewById(R.id.videoAuthor);
        desTx = (TextView) v.findViewById(R.id.videoDesc);
        return v;
    }

    public void init(items items)
    {
        titleTx.setText(items.getSnippet().getTitle());
        authorTx.setText(getString(R.string.owner)+items.getSnippet().getChannelTitle());
        desTx.setText(items.getSnippet().getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat(Config.MYDATEFORMATE, Locale.FRANCE);
        Calendar cal = Calendar.getInstance();
        String date = items.getSnippet().getPublishedAt();
        try {
            cal.setTime(sdf.parse(date));
            dateTx.setText(getString(R.string.published) + String.format("%1$td %1$tb %1$tY", cal));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        youTubePlayerFragment.setItem(items);
        youTubePlayerFragment.delete();
        youTubePlayerFragment.init();
    }
}
