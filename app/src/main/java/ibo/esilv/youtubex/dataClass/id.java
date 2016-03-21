package ibo.esilv.youtubex.dataClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mcas on 26/02/2016.
 */
public class id  implements Parcelable{
    private String kind;
    private String videoId;

    protected id(Parcel in) {
        kind = in.readString();
        videoId = in.readString();
    }

    public String getVideoId() {
        return videoId;
    }

    public String getKind() {
        return kind;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(videoId);
    }

    public static final Creator<id> CREATOR = new Creator<id>() {
        @Override
        public id createFromParcel(Parcel in) {
            return new id(in);
        }

        @Override
        public id[] newArray(int size) {
            return new id[size];
        }
    };
}
