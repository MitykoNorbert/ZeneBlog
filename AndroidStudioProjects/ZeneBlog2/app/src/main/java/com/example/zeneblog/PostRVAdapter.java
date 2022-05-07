package com.example.zeneblog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostRVAdapter extends RecyclerView.Adapter<PostRVAdapter.ViewHolder> {
    private ArrayList<PostRVModal> postRVModalArrayList;
    private Context context;
    int lastPos = -1;
    private PostClickInterface postClickInterface;

    public PostRVAdapter(ArrayList<PostRVModal> postRVModalArrayList, Context context, PostClickInterface postClickInterface) {
        this.postRVModalArrayList = postRVModalArrayList;
        this.context = context;
        this.postClickInterface = postClickInterface;
    }

    @NonNull
    @Override
    public PostRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostRVAdapter.ViewHolder holder, int position) {
        PostRVModal postRVModal = postRVModalArrayList.get(position);
        holder.postNameTV.setText(postRVModal.getPostName());
        holder.postAuthorTV.setText(postRVModal.getPostAuthor());


        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int temppos=holder.getAdapterPosition();
                postClickInterface.onPostClick(temppos);
            }
        });
    }
    private void setAnimation(View itemView, int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos=position;
        }
    }
    @Override
    public int getItemCount() {
        return postRVModalArrayList.size();
    }

    public interface PostClickInterface {
        void onPostClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView postNameTV;
        private TextView postAuthorTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postNameTV = itemView.findViewById(R.id.idTVPostName);
            postAuthorTV = itemView.findViewById(R.id.idTVPostAuthor);
        }
    }


}
