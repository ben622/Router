package com.ben.router.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017-09-05.
 */

public class UserInfo implements Parcelable {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.userName = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
