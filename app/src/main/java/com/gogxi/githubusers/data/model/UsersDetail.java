package com.gogxi.githubusers.data.model;

import com.google.gson.annotations.SerializedName;

public class UsersDetail{

	@SerializedName("blog")
	private String blog;

	@SerializedName("company")
	private String company;

	@SerializedName("id")
	private int id;

	@SerializedName("public_repos")
	private int publicRepos;

	@SerializedName("followers")
	private int followers;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("following")
	private int following;

	@SerializedName("name")
	private String name;

	@SerializedName("location")
	private String location;

	public UsersDetail(String blog, String company, int publicRepos, int followers, String avatarUrl, int following, String name, String location) {
		this.blog = blog;
		this.company = company;
		this.publicRepos = publicRepos;
		this.followers = followers;
		this.avatarUrl = avatarUrl;
		this.following = following;
		this.name = name;
		this.location = location;
	}

	public String getBlog(){
		return blog;
	}

	public String getCompany(){
		return company;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public int getPublicRepos(){
		return publicRepos;
	}

	public int getFollowers(){
		return followers;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public int getFollowing(){
		return following;
	}

	public String getName(){
		return name;
	}

	public String getLocation(){
		return location;
	}
}