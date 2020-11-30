package com.miguel.minimaltodo;

import android.media.Image;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Task implements Parcelable {
    int mId;
    int mImage;
    String mTitle;
    String mReminderDate;

    public Task(){
    }

    public int getId(){
        return mId;
    }
    public int getImage() {
        return mImage;
    }

    public void setId(int id){
        this.mId = id;
    }
    public void setImage(int mImage) {
        this.mImage = mImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getReminderDate() {
        return mReminderDate;
    }

    public void setReminderDate(String mReminderDate) {
        this.mReminderDate = mReminderDate;
    }

    public static final Parcelable.Creator<Task> CREATOR = new Creator<Task>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Task(Parcel in){
        mImage = in.readInt();
        mTitle = in.readString();
        mReminderDate = in.readString();
        mId = in.readInt();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
       parcel.writeInt(mImage);
       parcel.writeString(mTitle);
       parcel.writeString(mReminderDate);
       parcel.writeInt(mId);
    }
}
