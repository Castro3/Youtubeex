package ibo.esilv.youtubex.dataClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mcas on 26/02/2016.
 */
public class items implements Parcelable {
    private String kind;
    private String etag;
    private id id;
    private ibo.esilv.youtubex.dataClass.snippet snippet;

    protected items(Parcel in) {
        kind = in.readString();
        etag = in.readString();
        id = (id) in.readParcelable(id.class.getClassLoader());
        snippet = (snippet) in.readParcelable(snippet.class.getClassLoader());
    }

    public static final Creator<items> CREATOR = new Creator<items>() {
        @Override
        public items createFromParcel(Parcel in) {
            return new items(in);
        }

        @Override
        public items[] newArray(int size) {
            return new items[size];
        }
    };

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public ibo.esilv.youtubex.dataClass.id getId() {
        return id;
    }

    public ibo.esilv.youtubex.dataClass.snippet getSnippet() {
        return snippet;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(etag);
        dest.writeParcelable(id,flags);
        dest.writeParcelable(snippet,flags);
    }


}
