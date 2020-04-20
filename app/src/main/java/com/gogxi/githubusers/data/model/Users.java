package com.gogxi.githubusers.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Users implements Parcelable {

	@SerializedName("id")
	private int id;

	@SerializedName("public_repos")
	private int publicRepos;

	@SerializedName("followers")
	private int followers;

	@SerializedName("following")
	private int following;

	@SerializedName("name")
	private String name;

	@SerializedName("company")
	private String company;

	@SerializedName("blog")
	private String blog;

	@SerializedName("location")
	private String location;

	@SerializedName("login")
	private String login;

	@SerializedName("avatar_url")
	private String avatarUrl;


	protected Users(Parcel in) {
		id = in.readInt();
		publicRepos = in.readInt();
		followers = in.readInt();
		following = in.readInt();
		name = in.readString();
		company = in.readString();
		blog = in.readString();
		location = in.readString();
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

	public int getPublicRepos() {
		return publicRepos;
	}

	public void setPublicRepos(int publicRepos) {
		this.publicRepos = publicRepos;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setLogin(String login){
		this.login = login;
	}

	public String getLogin(){
		return login;
	}

	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl = avatarUrl;
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
		dest.writeInt(publicRepos);
		dest.writeInt(followers);
		dest.writeInt(following);
		dest.writeString(name);
		dest.writeString(company);
		dest.writeString(blog);
		dest.writeString(location);
		dest.writeString(login);
		dest.writeString(avatarUrl);
	}
}