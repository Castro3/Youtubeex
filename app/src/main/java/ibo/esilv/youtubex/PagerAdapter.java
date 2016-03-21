package ibo.esilv.youtubex;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mcas on 12/03/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private final List fragments;
    /*
    public PagerAdapter(FragmentManager fm) {
        super(fm);
        //this.fragments = fragments;
        fragments = new ArrayList();
    }*/

    public PagerAdapter(FragmentManager fm, List fragments) {
        super(fm);
        this.fragments = fragments;

    }


    @Override
    public Fragment getItem(int position) {
        return (Fragment) fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment frag)
    {
        fragments.add(frag);
    }
}
