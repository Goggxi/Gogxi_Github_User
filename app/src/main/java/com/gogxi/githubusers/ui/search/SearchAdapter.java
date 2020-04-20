package com.gogxi.githubusers.ui.search;

import android.content.Intent;
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
import com.gogxi.githubusers.R;
import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Users> listUsers = new ArrayList<>();

    public void setUsers(List<Users> users){
        if (listUsers == null) return;
        this.listUsers.clear();
        this.listUsers.addAll(users);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = listUsers.get(position);
        holder.bind(users);
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvUsername;
        final ImageView imgAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.txtvw_username);
            imgAvatar = itemView.findViewById(R.id.imgvw_avatar);
        }

        void bind(Users users) {
            tvUsername.setText(users.getLogin());
            itemView.setOnClickListener(v -> {
//                Toast.makeText(v.getContext(), "You clicked " + users.getLogin(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_USER, users);
                itemView.getContext().startActivity(intent);
            });
            Glide.with(itemView.getContext())
                    .load(users.getAvatarUrl())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imgAvatar);
        }
    }
}
