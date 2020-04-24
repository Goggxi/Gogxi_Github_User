package com.gogxi.githubfavoriteuser.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogxi.githubfavoriteuser.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private static final String BASE_IMAGE_URL = "https://avatars1.githubusercontent.com/u/" ;
    private Cursor cursor;
    private Context context;

    public void refill(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteAdapter.ViewHolder holder, int position) {
        if (cursor.moveToPosition(position)){
            holder.txtUsername.setText(cursor.getString(cursor.getColumnIndexOrThrow("login")));
            Glide.with(context)
                    .load(BASE_IMAGE_URL + cursor.getInt(cursor.getColumnIndexOrThrow("user_id")))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(holder.imgAvatar);
            holder.view.setOnClickListener(v -> {
                Toast.makeText(context,"Click : " + holder.txtUsername.getText() , Toast.LENGTH_LONG).show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgAvatar;
        final TextView txtUsername;
        final View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgvw_avatar);
            txtUsername = itemView.findViewById(R.id.txtvw_username);
            view = itemView;
        }

    }
}
