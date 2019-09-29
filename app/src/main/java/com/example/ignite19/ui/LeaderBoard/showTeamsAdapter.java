package com.example.ignite19.ui.LeaderBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ignite19.R;

import java.util.List;

public class showTeamsAdapter extends RecyclerView.Adapter<showTeamsAdapter.teamsViewHolder> {

List<String> team_name;
Context context;
private  int lastPosition = -1;
    public showTeamsAdapter(List<String> team_name, Context context) {

        this.team_name = team_name;
        this.context=context;
    }

    @NonNull
    @Override
    public teamsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.selected_team_cardview,parent,false);
        return new showTeamsAdapter.teamsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull teamsViewHolder holder, int position) {
        String teamname=team_name.get(position);
        setAnimation(holder.itemView,position);
        holder.team_name_tv.setText(teamname);
        Glide.with(context).load(R.drawable.victory).into(holder.victory);

    }

    @Override
    public int getItemCount() {
        return team_name.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull teamsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class teamsViewHolder extends RecyclerView.ViewHolder{

        TextView team_name_tv;
        ImageView victory;
        public teamsViewHolder(@NonNull View itemView) {
            super(itemView);
            team_name_tv=itemView.findViewById(R.id.team_name_tv);
            victory=itemView.findViewById(R.id.victory);
        }

        public void clearAnimation() {
            itemView.clearAnimation();
        }
    }

}




