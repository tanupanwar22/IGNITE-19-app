package com.example.ignite19.ui.events;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ignite19.DateTimeConverter;
import com.example.ignite19.R;
import com.example.ignite19.UserDataAndEventList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.MyOwnHolder> {


    private Context ctx;
    private int lastPosition = -1;
    private ArrayList<UserDataAndEventList> mEventList = new ArrayList<>();

    EventsListAdapter(){

    }

    EventsListAdapter(Context ctx, ArrayList<UserDataAndEventList> mEventList){
        this.ctx = ctx;
        this.mEventList = mEventList;
    }

    @NonNull
    @Override
    public MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(ctx).inflate(R.layout.events_card_for_eventlist,parent,false);
        MyOwnHolder vHolder = new MyOwnHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOwnHolder holder, final int position) {
        holder.eventListVenue.setText(mEventList.get(position).getEvent_venue());
        setAnimation(holder.itemView,position);
        holder.eventListImage.setImageResource(R.drawable.aaa);
        holder.mEventName.setText(mEventList.get(position).getEvent_name());
        holder.eventDateTime.setText(DateTimeConverter.changeDateFormat(mEventList.get(position).getEvent_date()));
        holder.mEventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String name=mEventList.get(position).getEvent_name();

                String desctext="",rulestext="";
                String desc=name+" desc.TXT";
                String rules=name+" rules.TXT";
                try  {
                    InputStream is = ctx.getAssets().open(desc);
                    int size=is.available();
                    byte[] buffer=new byte[size];
                    is.read(buffer);
                    is.close();
                    desctext=new String(buffer);
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
                try  {
                    InputStream is = ctx.getAssets().open(rules);
                    int size=is.available();
                    byte[] buffer=new byte[size];
                    is.read(buffer);
                    is.close();
                    rulestext=new String(buffer);
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }

                bundle.putString("eventName",name);
                //bundle.putString("eventName",text);
                bundle.putString("eventdesc",desctext);
                bundle.putString("eventrules",rulestext);
                Navigation.findNavController(view).navigate(R.id.action_nav_events_to_aboutEvent,bundle);
            }
        });

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MyOwnHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);

            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public class MyOwnHolder extends RecyclerView.ViewHolder {

        private CardView mEventCard;
        private TextView mEventName;
        private TextView eventListVenue;
        private ImageView eventListImage;
        private TextView eventDateTime;
        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            mEventCard = (CardView)itemView.findViewById(R.id.event_list_cardview);
            mEventName = (TextView)itemView.findViewById(R.id.event_name_textview_eventlist);
            eventListVenue= (TextView)itemView.findViewById(R.id.venue_textview_eventlist);
            eventListImage = itemView.findViewById(R.id.event_list_imageview);
            eventDateTime = itemView.findViewById(R.id.date_time_textview_events_card);
        }


        public void clearAnimation() {
            itemView.clearAnimation();
        }
    }
}
