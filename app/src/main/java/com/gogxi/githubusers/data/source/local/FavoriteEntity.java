package com.gogxi.githubusers.data.source.local;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = FavoriteEntity.TABLE_NAME)
public class FavoriteEntity implements Parcelable {

    public static final String TABLE_NAME = "favorite";

    public static final String COLUMN_ID = "id";

    public static final String COLUMN_USER_ID = "user_id";

    public static final String COLUMN_LOGIN = "login";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID )
    private int id;

    @ColumnInfo(name = COLUMN_USER_ID)
    private int user_id;

    @ColumnInfo(name = COLUMN_LOGIN)
    private String login;

    @ColumnInfo(name = "public_repos")
    private int publicRepos;

    @ColumnInfo(name = "followers")
    private int followers;

    @ColumnInfo(name = "following")
    private int following;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "company")
    private String company;

    @ColumnInfo(name = "blog")
    private String blog;

    @ColumnInfo(name = "location")
    private String location;

    public FavoriteEntity() {
    }

    protected FavoriteEntity(Parcel in) {
        id = in.readInt();
        user_id = in.readInt();
        login = in.readString();
        publicRepos = in.readInt();
        followers = in.readInt();
        following = in.readInt();
        name = in.readString();
        company = in.readString();
        blog = in.readString();
        location = in.readString();
    }

    public static final Creator<FavoriteEntity> CREATOR = new Creator<FavoriteEntity>() {
        @Override
        public FavoriteEntity createFromParcel(Parcel in) {
            return new FavoriteEntity(in);
        }

        @Override
        public FavoriteEntity[] newArray(int size) {
            return new FavoriteEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(user_id);
        dest.writeString(login);
        dest.writeInt(publicRepos);
        dest.writeInt(followers);
        dest.writeInt(following);
        dest.writeString(name);
        dest.writeString(company);
        dest.writeString(blog);
        dest.writeString(location);
    }
}
