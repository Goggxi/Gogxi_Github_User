package com.gogxi.githubusers.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UsersResponse {

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("incomplete_results")
	private boolean incompleteResults;

	@SerializedName("items")
	private List<Users> items;

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setIncompleteResults(boolean incompleteResults){
		this.incompleteResults = incompleteResults;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public void setItems(List<Users> items){
		this.items = items;
	}

	public List<Users> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"UsersResponse{" +
			"total_count = '" + totalCount + '\'' + 
			",incomplete_results = '" + incompleteResults + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}