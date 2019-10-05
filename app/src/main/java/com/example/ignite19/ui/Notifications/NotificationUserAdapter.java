package com.example.ignite19.ui.Notifications;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ignite19.DateTimeConverter;
import com.example.ignite19.R;
import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NotificationUserAdapter extends RecyclerView.Adapter<NotificationUserAdapter.MyOwnAdapter> {

    Context context;
    ArrayList<NotificationPOJO> mList;
    public TimelineView mTimelineView;


    public NotificationUserAdapter(Context context, ArrayList<NotificationPOJO> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyOwnAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_card,parent,false);
        return  new MyOwnAdapter(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOwnAdapter holder, int position) {
        holder.body.setText(mList.get(position).getBody().toString());
        holder.title.setText(mList.get(position).getTitle().toString());
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(mList.get(position).timeStamp());
        String date = DateFormat.format("E, dd MMM KK:mm a", cal).toString();
        holder.timeStampTextView.setText(date);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    public class MyOwnAdapter extends RecyclerView.ViewHolder {

        FloatingActionButton fab;
        TextView title;
        TextView body;
        TextView timeStampTextView;
        TextView eventParticipants;
        public MyOwnAdapter(@NonNull View itemView,int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
            timeStampTextView = itemView.findViewById(R.id.timestamp_textview);
           // fab = itemView.findViewById(R.id.directions_notification);
            title  = itemView.findViewById(R.id.title_notification_user);
            body = itemView.findViewById(R.id.text_notification);
           // eventParticipants = itemView.findViewById(R.id.event_participants);
        }
    }
}
