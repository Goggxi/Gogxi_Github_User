package com.gogxi.githubusers.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UsersResponse {

	@SerializedName("items")
	private List<Users> items;

	public UsersResponse(List<Users> items) {
		this.items = items;
	}

	public List<Users> getItems(){
		return items;
	}
}