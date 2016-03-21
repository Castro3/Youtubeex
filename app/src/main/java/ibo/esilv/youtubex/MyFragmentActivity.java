package ibo.esilv.youtubex;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.*;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ibo.esilv.youtubex.dataClass.items;

/**
 * Created by Mcas on 12/03/2016.
 */



public class MyFragmentActivity extends FragmentActivity{

    private PagerAdapter mPagerAdapter;
    private ViewPager pager;
    private ArrayList<items> itemList;
    private int curpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            itemList = extras.getParcelableArrayList(Config.KEY_LIST);
            curpos = extras.getInt(Config.KEY_POS);
        }


        //mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager());
        List fragments = new Vector();
        for(int i = 0; i<itemList.size();i++)
        {
            /*
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setItem(itemList.get(i));
            mPagerAdapter.addFragment(videoFragment);
            */
            fragments.add(Fragment.instantiate(this, NewFragment.class.getName()));
        }
        mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);

        pager = (ViewPager) super.findViewById(R.id.viewpager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                /*
                    VideoFragment myfrag = (VideoFragment) mPagerAdapter.getItem(i);
                    myfrag.setItem(itemList.get(i));
                    myfrag.delete();
                    myfrag.init();
                */
               NewFragment myfrag = (NewFragment) mPagerAdapter.getItem(i);
                myfrag.init(itemList.get(i));
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        pager.setAdapter(mPagerAdapter);
        pager.setCurrentItem(curpos, true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
