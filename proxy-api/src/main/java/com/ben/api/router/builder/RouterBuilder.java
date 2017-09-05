package com.ben.api.router.builder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import com.ben.api.router.core.Router;
import com.ben.api.router.parameter.DefaultParameterImpl;
import com.ben.api.router.parameter.IParameter;

import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017-08-04
 * @desc 路由构造器
 */
public class RouterBuilder {
    public static final String ROOT = "router";
    private Context mContext;
    private Bundle mBundle;
    private int mRequestCode;
    /**
     * 使用Class跳转
     */
    private Class mTarget;
    /**
     * 通过制定Activity别名跳转
     */
    private String mAction;
    /**
     * Uri类型跳转必要格式
     * scheme://host
     * 必须通过注解将action设置为 "scheme/host"
     */
    private Uri mUri;
    /**
     * activity进入和退出动画
     */
    private int mActivityEnterAnim;
    private int mActivityExitAnim;

    /**
     * 任务栈
     */
    private int mFlags = -1;

    private IParameter mIParameter;

    public Context getContext() {
        return mContext;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public String getAction() {
        return mAction;
    }

    public Uri getUri() {
        return mUri;
    }

    public int getActivityEnterAnim() {
        return mActivityEnterAnim;
    }

    public int getActivityExitAnim() {
        return mActivityExitAnim;
    }

    public int getFlags() {
        return mFlags;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public Class getTarget() {
        return mTarget;
    }

    public RouterBuilder(Context context) {
        mContext = context;
        mBundle = new Bundle();
        //默认参数实现
        mIParameter = new DefaultParameterImpl();
    }

    /**
     * Inserts a Boolean value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a boolean
     */
    public RouterBuilder putBoolean(@Nullable String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    /**
     * Inserts a byte value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a byte
     */
    RouterBuilder putByte(@Nullable String key, byte value) {

        mBundle.putByte(key, value);
        return this;
    }

    /**
     * Inserts a char value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a char
     */
    RouterBuilder putChar(@Nullable String key, char value) {
        mBundle.putChar(key, value);
        return this;
    }

    /**
     * Inserts a short value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a short
     */
    RouterBuilder putShort(@Nullable String key, short value) {
        mBundle.putShort(key, value);
        return this;
    }

    /**
     * Inserts an int value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value an int
     */
    public RouterBuilder putInt(@Nullable String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    /**
     * Inserts a long value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a long
     */
    public RouterBuilder putLong(@Nullable String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    /**
     * Inserts a float value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a float
     */
    RouterBuilder putFloat(@Nullable String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    /**
     * Inserts a double value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a double
     */
    public RouterBuilder putDouble(@Nullable String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    /**
     * Inserts a String value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a String, or null
     */
    public RouterBuilder putString(@Nullable String key, @Nullable String value) {
        mBundle.putString(key, value);
        return this;
    }

    /**
     * Inserts a CharSequence value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a CharSequence, or null
     */
    RouterBuilder putCharSequence(@Nullable String key, @Nullable CharSequence value) {
        mBundle.putCharSequence(key, value);
        return this;
    }

    /**
     * Inserts a boolean array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a boolean array object, or null
     */
    public RouterBuilder putBooleanArray(@Nullable String key, @Nullable boolean[] value) {
        mBundle.putBooleanArray(key, value);
        return this;
    }


    /**
     * Inserts an int array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an int array object, or null
     */
    public RouterBuilder putIntArray(@Nullable String key, @Nullable int[] value) {
        mBundle.putIntArray(key, value);
        return this;
    }

    /**
     * Inserts a long array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a long array object, or null
     */
    public RouterBuilder putLongArray(@Nullable String key, @Nullable long[] value) {
        mBundle.putLongArray(key, value);
        return this;
    }


    /**
     * Inserts a double array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a double array object, or null
     */
    public RouterBuilder putDoubleArray(@Nullable String key, @Nullable double[] value) {
        mBundle.putDoubleArray(key, value);
        return this;
    }

    /**
     * Inserts a String array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a String array object, or null
     */
    public RouterBuilder putStringArray(@Nullable String key, @Nullable String[] value) {
        mBundle.putStringArray(key, value);
        return this;
    }


    /**
     * Inserts a Parcelable value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Parcelable object, or null
     */
    public RouterBuilder putParcelable(@Nullable String key, @Nullable Parcelable value) {
        mBundle.putParcelable(key, value);
        return this;
    }

    /**
     * Inserts an array of Parcelable values into the mapping of this Bundle,
     * replacing any existing value for the given key.  Either key or value may
     * be null.
     *
     * @param key   a String, or null
     * @param value an array of Parcelable objects, or null
     */
    public RouterBuilder putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        mBundle.putParcelableArray(key, value);
        return this;
    }

    /**
     * Inserts a List of Parcelable values into the mapping of this Bundle,
     * replacing any existing value for the given key.  Either key or value may
     * be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList of Parcelable objects, or null
     */
    public RouterBuilder putParcelableArrayList(@Nullable String key,
                                                @Nullable ArrayList<? extends Parcelable> value) {
        mBundle.putParcelableArrayList(key, value);
        return this;
    }


    /**
     * Inserts a SparceArray of Parcelable values into the mapping of this
     * Bundle, replacing any existing value for the given key.  Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a SparseArray of Parcelable objects, or null
     */
    public RouterBuilder putSparseParcelableArray(@Nullable String key,
                                                  @Nullable SparseArray<? extends Parcelable> value) {
        mBundle.putSparseParcelableArray(key, value);
        return this;
    }


    /**
     * Inserts an ArrayList<String> value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList<String> object, or null
     */
    public RouterBuilder putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

    /**
     * Inserts an ArrayList<CharSequence> value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList<CharSequence> object, or null
     */
    public RouterBuilder putCharSequenceArrayList(@Nullable String key,
                                                  @Nullable ArrayList<CharSequence> value) {
        mBundle.putCharSequenceArrayList(key, value);
        return this;
    }

    /**
     * Inserts a byte array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a byte array object, or null
     */
    public RouterBuilder putByteArray(@Nullable String key, @Nullable byte[] value) {
        mBundle.putByteArray(key, value);
        return this;
    }

    /**
     * Inserts a short array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a short array object, or null
     */
    public RouterBuilder putShortArray(@Nullable String key, @Nullable short[] value) {
        mBundle.putShortArray(key, value);
        return this;
    }

    /**
     * Inserts a char array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a char array object, or null
     */
    public RouterBuilder putCharArray(@Nullable String key, @Nullable char[] value) {
        mBundle.putCharArray(key, value);
        return this;
    }

    /**
     * Inserts a float array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a float array object, or null
     */
    public RouterBuilder putFloatArray(@Nullable String key, @Nullable float[] value) {
        mBundle.putFloatArray(key, value);
        return this;
    }

    /**
     * Inserts a CharSequence array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a CharSequence array object, or null
     */
    public RouterBuilder putCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
        mBundle.putCharSequenceArray(key, value);
        return this;
    }

    /**
     * Inserts a Bundle value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Bundle object, or null
     */
    public RouterBuilder putBundle(@Nullable String key, @Nullable Bundle value) {
        mBundle.putBundle(key, value);
        return this;
    }

    public RouterBuilder setActivityEnterAnim(int activityEnterAnim) {
        mActivityEnterAnim = activityEnterAnim;
        return this;
    }

    public RouterBuilder setActivityExitAnim(int activityExitAnim) {
        mActivityExitAnim = activityExitAnim;
        return this;
    }

    public RouterBuilder setFlags(int flags) {
        mFlags = flags;
        return this;
    }

    public RouterBuilder setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public RouterBuilder setIParameter(IParameter IParameter) {
        mIParameter = IParameter;
        return this;
    }

    public void build(@Nullable String action) {
        this.mAction = action;
        buildParams();
    }

    public void build(@Nullable Uri uri) {
        this.mUri = uri;
        buildParams();
    }

    public void build(@Nullable Class<?> target) {
        this.mTarget = target;
        buildParams();
    }

    public static RouterBuilder newInstance(@Nullable Context context) {
        return new RouterBuilder(context);
    }

    /**
     * 参数构建
     */
    private void buildParams() {
        if (mIParameter!=null) {
            Uri uri = mUri;
            if (uri == null) {
                uri = Uri.parse(ROOT + "://" + mAction);
            }
            this.mAction = mIParameter.parseAction(uri);
            if (mUri != null) {
                for (Map.Entry<String, Object> entry : mIParameter.parseParams(mUri).entrySet()) {
                    mBundle.putString(entry.getKey(), entry.getValue().toString());
                }
            }
            if (Router.isInit) {
                Router.getInstance(this).build();
            }else{
                Log.e(TAG, "buildParams:未初始化,请先在Application中调用[Router.init(this)]");
            }
        }
    }

}
