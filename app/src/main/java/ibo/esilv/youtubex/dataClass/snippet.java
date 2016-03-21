package ibo.esilv.youtubex.dataClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mcas on 26/02/2016.
 */
public class snippet implements Parcelable{
    private String publishedAt;

    protected snippet(Parcel in) {
        publishedAt = in.readString();
        channelID = in.readString();
        title = in.readString();
        description = in.readString();
        channelTitle = in.readString();
        thumbnails = (thumbnails) in.readParcelable(thumbnails.class.getClassLoader());
    }

    public static final Creator<snippet> CREATOR = new Creator<snippet>() {
        @Override
        public snippet createFromParcel(Parcel in) {
            return new snippet(in);
        }

        @Override
        public snippet[] newArray(int size) {
            return new snippet[size];
        }
    };

    public String getChannelID() {
        return channelID;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ibo.esilv.youtubex.dataClass.thumbnails getThumbnails() {
        return thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    private String channelID;
    private String title;
    private String description;
    private thumbnails thumbnails;
    private String channelTitle;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(publishedAt);
        dest.writeString(channelID);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(channelTitle);
        dest.writeParcelable(thumbnails,flags);
    }
}
