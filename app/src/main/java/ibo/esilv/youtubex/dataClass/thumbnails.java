package ibo.esilv.youtubex.dataClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mcas on 26/02/2016.
 */
public class thumbnails implements Parcelable{
    protected thumbnails(Parcel in) {
        medium = (medium) in.readParcelable(medium.class.getClassLoader());
    }

    public static final Creator<thumbnails> CREATOR = new Creator<thumbnails>() {
        @Override
        public thumbnails createFromParcel(Parcel in) {
            return new thumbnails(in);
        }

        @Override
        public thumbnails[] newArray(int size) {
            return new thumbnails[size];
        }
    };

    public ibo.esilv.youtubex.dataClass.medium getMedium() {
        return medium;
    }

    private medium medium;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(medium,flags);
    }
}
