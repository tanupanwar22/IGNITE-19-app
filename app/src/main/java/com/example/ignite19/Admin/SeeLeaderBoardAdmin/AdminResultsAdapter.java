package com.example.ignite19.Admin.SeeLeaderBoardAdmin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ignite19.R;
import com.example.ignite19.ui.LeaderBoard.resultsAdapter;

import java.util.List;

public class AdminResultsAdapter extends RecyclerView.Adapter<AdminResultsAdapter.resultsViewHolder>{
    private List<String> event_name;
    Context context;
    private int lastPosition = -1;

    public AdminResultsAdapter(Context context, List<String> event_name) {
        this.event_name = event_name;
        this.context=context;
    }

    @NonNull
    @Override
    public resultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.results_list_card_view,parent,false);
        return  new resultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull resultsViewHolder holder, int position) {
        final String event_title=event_name.get(position);
        setAnimation(holder.itemView,position);
        holder.event_name_tv.setText(event_title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("event",event_title);
                Navigation.findNavController(view).navigate(R.id.action_seeLeaderBoardAdmin_to_adminEventResults,bundle);
            }
        });

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull resultsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimaiton();
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
        return event_name.size();
    }

    public class resultsViewHolder extends RecyclerView.ViewHolder {
        private TextView event_name_tv;
        public resultsViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name_tv=itemView.findViewById(R.id.results_card_event_name_textview);
        }
        public void clearAnimaiton() {
            itemView.clearAnimation();
        }
    }
}



