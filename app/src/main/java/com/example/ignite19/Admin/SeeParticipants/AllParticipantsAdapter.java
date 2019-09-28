package com.example.ignite19.Admin.SeeParticipants;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ignite19.R;

import java.util.ArrayList;


public class AllParticipantsAdapter extends RecyclerView.Adapter<AllParticipantsAdapter.MyOwnHolder> {


    Context ctx;
    ArrayList<EventWiseParticipantDetail> participantDetailsList;




    AllParticipantsAdapter(){

    }


    public AllParticipantsAdapter(Context ctx, ArrayList<EventWiseParticipantDetail> participantDetailsList){
        this.ctx = ctx;
        this.participantDetailsList = participantDetailsList;

    }
    @NonNull
    @Override
    public MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(ctx).inflate(R.layout.all_participants_card,parent,false);

        return new MyOwnHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOwnHolder holder, int position) {
        holder.collegeName.setText(participantDetailsList.get(position).getCollegeName().toString());
        String p1 = participantDetailsList.get(position).getParticipant1();
        String p2 = participantDetailsList.get(position).getParticipant2();
        String p3 = participantDetailsList.get(position).getParticipant3();
        String p4 = participantDetailsList.get(position).getParticipant4();
        String p5 = participantDetailsList.get(position).getParticipant5();
        if(p1 != null){
            holder.participant1.setText("1. " + p1);
        }
        else {
            holder.participant1.setVisibility(TextView.GONE);

        }


        if(p2 != null){

            holder.participant2.setText("2. " + p2);
        }
        else {
            holder.participant2.setVisibility(TextView.GONE);

        }


        if(p3 !=null){
            holder.participant3.setText("3. " + p3);
        }
        else {
            holder.participant3.setVisibility(TextView.GONE);

        }


        if(p4 != null){
            holder.participant4.setText("4. " + p4);
        }
        else {
            holder.participant4.setVisibility(TextView.GONE);

        }


        if(p5 != null){
            holder.participant5.setText("5. " + p5);
        }
        else {
            holder.participant5.setVisibility(TextView.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return participantDetailsList.size();
    }

    public class MyOwnHolder extends RecyclerView.ViewHolder {

        TextView collegeName;
        TextView participant1;
        TextView participant2;
        TextView participant3;
        TextView participant4;
        TextView participant5;

        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            collegeName = itemView.findViewById(R.id.college_namexx);
            participant1 = itemView.findViewById(R.id.participant1xx);
            participant2  = itemView.findViewById(R.id.participant2xx);
            participant3 = itemView.findViewById(R.id.participant3xx);
            participant4 = itemView.findViewById(R.id.participant4xx);
            participant5 = itemView.findViewById(R.id.participant5xx);
        }
    }



}
