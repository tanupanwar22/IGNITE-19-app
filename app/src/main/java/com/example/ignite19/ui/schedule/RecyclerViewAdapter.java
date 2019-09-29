package com.example.ignite19.ui.schedule;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ignite19.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    Context mContext;
    ArrayList<eventSchedule> mdata = new ArrayList<>();
    private int lastPosition = -1;

    public RecyclerViewAdapter(Context mContext, ArrayList<eventSchedule> mdata) {
        this.mContext = mContext;
        this.mdata = mdata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(mContext).inflate(R.layout.item_day0,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.eventVenue.setText("Venue: " + mdata.get(position).getEvent_venue());
        holder.eventDuration.setText( mdata.get(position).getEvent_type());
        holder.eventName.setText(mdata.get(position).getEvent_name());
        holder.eventDateAndTime.setText(mdata.get(position).getStart_time());
        holder.eventDate.setText(mdata.get(position).getEvent_date());
        holder.eventDay.setText(mdata.get(position).getEvent_day());
        setAnimation(holder.itemView,position);
    }
    @Override
    public int getItemCount() {
        return mdata.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView eventDateAndTime;
        private TextView eventName;
        private TextView eventDuration;
        private TextView eventVenue;
        private TextView eventDay;
        private TextView eventDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventDateAndTime = itemView.findViewById(R.id.date_and_time_text_view);
            eventName = itemView.findViewById(R.id.event_name_text_view);
            eventDuration = itemView.findViewById(R.id.event_type_text_view);
            eventVenue = itemView.findViewById(R.id.venue_text_view);
            eventDate = itemView.findViewById(R.id.event_date_textview);
            eventDay = itemView.findViewById(R.id.event_day_textview);
        }


        public void clearAnimation() {
            itemView.clearAnimation();
        }
    }
}
