package com.gogxi.githubusers.ui.favorite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogxi.githubusers.R;
import com.gogxi.githubusers.data.source.local.FavoriteEntity;
import com.gogxi.githubusers.ui.detail.DetailActivity;
import com.gogxi.githubusers.utils.NoteDiffCallback;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private static final String BASE_IMAGE_URL = "https://avatars1.githubusercontent.com/u/" ;
    private final Activity activity;
    private Context context;

    private final ArrayList<FavoriteEntity> listUsersFavorite = new ArrayList<>();

    FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setFavorite(List<FavoriteEntity> mEntities){
        final NoteDiffCallback diffCallback = new NoteDiffCallback(this.listUsersFavorite, mEntities);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.listUsersFavorite.clear();
        this.listUsersFavorite.addAll(mEntities);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        FavoriteEntity mFavoriteEntity = listUsersFavorite.get(position);
//        holder.bind(mFavoriteEntity);
        holder.tvUsername.setText(listUsersFavorite.get(position).getLogin());
//        Glide.with(context)
//                    .load(BASE_IMAGE_URL + listUsersFavorite.get(position).getUser_id())
//                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
//                    .into(holder.imgAvatar);

        holder.tvUsername.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_POSITION, holder.getAdapterPosition());
            intent.putExtra(DetailActivity.EXTRA_USER_FAVORITE, listUsersFavorite.get(holder.getAdapterPosition()));
            activity.startActivityForResult(intent, DetailActivity.REQUEST_UPDATE);
        });
    }

    @Override
    public int getItemCount() {
        return listUsersFavorite.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvUsername;
        final ImageView imgAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.txtvw_username);
            imgAvatar = itemView.findViewById(R.id.imgvw_avatar);
        }

//        void bind(FavoriteEntity mFavoriteEntity) {
//            tvUsername.setText(mFavoriteEntity.getLogin());
//            itemView.setOnClickListener(v -> {
//                Toast.makeText(v.getContext(), "You clicked " + mFavoriteEntity.getName(), Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
////                intent.putExtra(DetailActivity.EXTRA_USER, users);
//
////                itemView.getContext().startActivity(intent);
//            });
//            Glide.with(itemView.getContext())
//                    .load(BASE_IMAGE_URL + mFavoriteEntity.getUser_id())
//                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
//                    .into(imgAvatar);
//        }
    }
}
