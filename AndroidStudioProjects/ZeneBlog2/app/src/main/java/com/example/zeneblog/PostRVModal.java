package com.example.zeneblog;

import android.os.Parcel;
import android.os.Parcelable;

public class PostRVModal implements Parcelable {
    private String postName;
    private String postDescription;
    private String postID;

    public PostRVModal(){

    }

    public PostRVModal(String postName, String postDescription, String postID) {
        this.postName = postName;
        this.postDescription = postDescription;
        this.postID = postID;
    }

    protected PostRVModal(Parcel in) {
        postName = in.readString();
        postDescription = in.readString();
        postID = in.readString();
    }

    public static final Creator<PostRVModal> CREATOR = new Creator<PostRVModal>() {
        @Override
        public PostRVModal createFromParcel(Parcel in) {
            return new PostRVModal(in);
        }

        @Override
        public PostRVModal[] newArray(int size) {
            return new PostRVModal[size];
        }
    };

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(postName);
        parcel.writeString(postDescription);
        parcel.writeString(postID);
    }
}
