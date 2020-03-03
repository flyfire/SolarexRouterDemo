package com.solarexsoft.solarexroutercore;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

import com.solarexsoft.solarexrouter.annotation.model.RouteMeta;
import com.solarexsoft.solarexroutercore.template.IProvider;

import java.util.ArrayList;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 19:39/2020/3/3
 *    Desc:
 * </pre>
 */

public class PostCard extends RouteMeta {
    private Bundle bundle;
    private int flags = -1;

    private Bundle optionsCompat;
    private int enterAnim, exitAnim;

    private IProvider provider;

    public PostCard(String path, String group, Bundle bundle) {
        setPath(path);
        setGroup(group);
        this.bundle = bundle == null ? new Bundle() : bundle;
    }

    public PostCard(String path, String group) {
        this(path, group, null);
    }

    public Bundle getExtras() {
        return this.bundle;
    }

    public int getEnterAnim() {
        return enterAnim;
    }

    public int getExitAnim() {
        return exitAnim;
    }

    public IProvider getProvider() {
        return provider;
    }

    public void setProvider(IProvider provider) {
        this.provider = provider;
    }

    public int getFlags() {
        return this.flags;
    }

    public PostCard withFlags(int flags) {
        this.flags = flags;
        return this;
    }
    
    public PostCard withTransition(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }
    
    public PostCard withOptionsCompat(ActivityOptionsCompat activityOptionsCompat) {
        this.optionsCompat = activityOptionsCompat.toBundle();
        return this;
    }
    
    public PostCard withString(String key, String value) {
        this.bundle.putString(key, value);
        return this;
    }
    
    public PostCard withBoolean(String key, boolean value) {
        this.bundle.putBoolean(key, value);
        return this;
    }
    
    public PostCard withShort(String key, short value) {
        this.bundle.putShort(key, value);
        return this;
    }
    
    public PostCard withInt(String key, int value) {
        this.bundle.putInt(key, value);
        return this;
    }
    
    public PostCard withLong(String key, long value) {
        this.bundle.putLong(key, value);
        return this;
    }
    
    public PostCard withDouble(String key, double value) {
        this.bundle.putDouble(key, value);
        return this;
    }
    
    public PostCard withByte(String key, byte value) {
        this.bundle.putByte(key, value);
        return this;
    }
    
    public PostCard withChar(String key, char value) {
        this.bundle.putChar(key, value);
        return this;
    }
    
    public PostCard withFloat(String key, float value) {
        this.bundle.putFloat(key, value);
        return this;
    }
    
    public PostCard withParcelable(String key, Parcelable value) {
        this.bundle.putParcelable(key, value);
        return this;
    }
    
    public PostCard withStringArray(String key, String[] value) {
        this.bundle.putStringArray(key, value);
        return this;
    }
    
    public PostCard withBooleanArray(String key, boolean[] value) {
        this.bundle.putBooleanArray(key, value);
        return this;
    }
    
    public PostCard withShortArray(String key, short[] value) {
        this.bundle.putShortArray(key, value);
        return this;
    }
    
    public PostCard withIntArray(String key, int[] value) {
        this.bundle.putIntArray(key, value);
        return this;
    }

    public PostCard withLongArray(@Nullable String key, long[] value) {
        this.bundle.putLongArray(key, value);
        return this;
    }


    public PostCard withDoubleArray(@Nullable String key, double[] value) {
        this.bundle.putDoubleArray(key, value);
        return this;
    }


    public PostCard withByteArray(@Nullable String key, byte[] value) {
        this.bundle.putByteArray(key, value);
        return this;
    }


    public PostCard withCharArray(@Nullable String key, char[] value) {
        this.bundle.putCharArray(key, value);
        return this;
    }


    public PostCard withFloatArray(@Nullable String key, float[] value) {
        this.bundle.putFloatArray(key, value);
        return this;
    }


    public PostCard withParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        this.bundle.putParcelableArray(key, value);
        return this;
    }

    public PostCard withParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends
                Parcelable> value) {
        this.bundle.putParcelableArrayList(key, value);
        return this;
    }

    public PostCard withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        this.bundle.putIntegerArrayList(key, value);
        return this;
    }

    public PostCard withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        this.bundle.putStringArrayList(key, value);
        return this;
    }
    
}
