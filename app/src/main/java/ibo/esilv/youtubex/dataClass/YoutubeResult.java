package ibo.esilv.youtubex.dataClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mcas on 26/02/2016.
 */
public class YoutubeResult{
    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public ibo.esilv.youtubex.dataClass.pageInfo getPageInfo() {
        return pageInfo;
    }


    public List<ibo.esilv.youtubex.dataClass.items> getItems() {
        return items;
    }

    private String kind;
    private String etag;
    private String regionCode;
    private pageInfo pageInfo;
    private ArrayList<items> items;


}
