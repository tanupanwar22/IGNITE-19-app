package com.NITK.ignite.Admin.AdminUpdateLeaderBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.NITK.ignite.R;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayCollegeNamesAdapter extends RecyclerView.Adapter<DisplayCollegeNamesAdapter.MyOwnHolder> implements  ItemMoveCallback.ItemTouchHelperContract {


    ArrayList<String > college_name = new ArrayList<>();
    Context ctx;
    String eventName;


    DisplayCollegeNamesAdapter(Context ctx,ArrayList<String> college_name,String eventName){
        this.eventName = eventName;
        this.college_name = college_name;
        this.ctx = ctx;

    }
    @NonNull
    @Override
    public MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(ctx).inflate(R.layout.college_name_display_card,parent,false);
        MyOwnHolder vHolder = new MyOwnHolder(v);
        return vHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyOwnHolder holder, int position) {
        holder.college_name.setText(college_name.get(position));
       // holder.itemPosition.setText(String.valueOf(position + 1));

    }

    @Override
    public int getItemCount() {
        return college_name.size();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(college_name, i, i + 1);

            }
        }
        else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(college_name, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(MyOwnHolder myViewHolder) {
        //myViewHolder.itemPosition.setText("");
    }

    public ArrayList<String> getCollege_name() {
        return college_name;
    }

    public String getEventName() {
        return eventName;
    }


    @Override
    public void onRowClear(MyOwnHolder myViewHolder) {

        myViewHolder.itemPosition.setText(String.valueOf(myViewHolder.getLayoutPosition() + 1));
    }


    public class MyOwnHolder extends RecyclerView.ViewHolder {
        TextView itemPosition;
        TextView college_name;
        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            college_name  = (TextView)itemView.findViewById(R.id.college_name_card_admin);
            itemPosition = (TextView)itemView.findViewById(R.id.textView1990);
        }
    }
}
