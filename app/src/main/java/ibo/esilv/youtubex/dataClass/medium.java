package ibo.esilv.youtubex.dataClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mcas on 26/02/2016.
 */
public class medium implements Parcelable{
    private String url;
    private int width;

    protected medium(Parcel in) {
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<medium> CREATOR = new Creator<medium>() {
        @Override
        public medium createFromParcel(Parcel in) {
            return new medium(in);
        }

        @Override
        public medium[] newArray(int size) {
            return new medium[size];
        }
    };

    public int getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    private int height;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}
