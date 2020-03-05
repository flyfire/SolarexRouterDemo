package com.solarexsoft.solarexrouterdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 09:16/2020/3/5
 *    Desc:
 * </pre>
 */

public class DemoParcelable implements Parcelable {
    private int id;
    private String text;

    public DemoParcelable(int id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.text);
    }

    public DemoParcelable() {
    }

    protected DemoParcelable(Parcel in) {
        this.id = in.readInt();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<DemoParcelable> CREATOR = new Parcelable.Creator<DemoParcelable>() {
        @Override
        public DemoParcelable createFromParcel(Parcel source) {
            return new DemoParcelable(source);
        }

        @Override
        public DemoParcelable[] newArray(int size) {
            return new DemoParcelable[size];
        }
    };
}
