package com.gogxi.githubusers.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Users implements Parcelable {

	@SerializedName("id")
	private int id;

	@SerializedName("login")
	private String login;

	@SerializedName("avatar_url")
	private String avatarUrl;


	protected Users(Parcel in) {
		id = in.readInt();
		login = in.readString();
		avatarUrl = in.readString();
	}

	public static final Creator<Users> CREATOR = new Creator<Users>() {
		@Override
		public Users createFromParcel(Parcel in) {
			return new Users(in);
		}

		@Override
		public Users[] newArray(int size) {
			return new Users[size];
		}
	};

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public String getLogin(){
		return login;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(login);
		dest.writeString(avatarUrl);
	}
}