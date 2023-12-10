package com.example.mymap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymap.R;

import com.example.mymap.adapters.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> comments;
    private String userName;
    private String commentText;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        // 使用正确的方法名获取评论文本
        holder.textViewComment.setText(comment.getCommentText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUserAvatar;
        TextView textViewComment;

        CommentViewHolder(View itemView) {
            super(itemView);
            imageViewUserAvatar = itemView.findViewById(R.id.imageViewUserAvatar);
            textViewComment = itemView.findViewById(R.id.textViewComment);
        }
    }


}