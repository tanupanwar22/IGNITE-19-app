package com.example.ignite19.Admin.SeeLeaderBoardAdmin;

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

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.ignite19.R;
import com.example.ignite19.ui.LeaderBoard.showTeamsAdapter;

import java.util.List;

public class AdminShowTeamsAdapter extends RecyclerView.Adapter<AdminShowTeamsAdapter.MyOwnHolder> {

    List<String> team_name;
    Context context;
    private  int lastPosition = -1;

    public AdminShowTeamsAdapter(List<String> team_name, Context context) {
        this.team_name = team_name;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.selected_team_cardview,parent,false);
        return new MyOwnHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOwnHolder holder, int position) {
        String teamname=team_name.get(position);
        holder.victory.setVisibility(View.INVISIBLE);
        if (!teamname.equalsIgnoreCase("No data available yet")){
           // Glide.with(context).load(R.drawable.victory).into(holder.victory);
            holder.victory.setVisibility(View.VISIBLE);
        }
        setAnimation(holder.itemView,position);
        holder.team_name_tv.setText(teamname);
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
    @Override
    public int getItemCount() {
        return team_name.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MyOwnHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    public class MyOwnHolder extends RecyclerView.ViewHolder {
        TextView team_name_tv;
        LottieAnimationView victory;
        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            team_name_tv=itemView.findViewById(R.id.team_name_tv);
            victory=itemView.findViewById(R.id.victory_xx);
        }
        public void clearAnimation() {
            itemView.clearAnimation();
        }
    }
}
