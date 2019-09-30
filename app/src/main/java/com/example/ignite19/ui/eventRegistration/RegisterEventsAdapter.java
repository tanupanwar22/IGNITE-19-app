package com.example.ignite19.ui.eventRegistration;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ignite19.BuildConfig;
import com.example.ignite19.R;
import com.example.ignite19.UserDataAndEventList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RegisterEventsAdapter extends RecyclerView.Adapter<RegisterEventsAdapter.MyOwnHolder> {


    private Context ct;
    private ArrayList<UserDataAndEventList> userDataAndEventLists = new ArrayList<>();

    RegisterEventsAdapter(){

    }

    public RegisterEventsAdapter(Context ct, ArrayList<UserDataAndEventList> userDataAndEventLists) {
        this.ct = ct;
        this.userDataAndEventLists = userDataAndEventLists;
    }

    @NonNull
    @Override
    public MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(ct).inflate(R.layout.card_for_registered_events,parent,false);
        return new MyOwnHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOwnHolder holder, final int position) {
        holder.event_image.setImageResource(userDataAndEventLists.get(position).getEvent_icon_uri());
        holder.event_name.setText(userDataAndEventLists.get(position).getEvent_name());
        holder.mCardVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String name=userDataAndEventLists.get(position).getEvent_name();

                String desctext="",rulestext="";
                String desc=name+" desc.TXT";
                String rules=name+" rules.TXT";
                try  {
                    InputStream is = ct.getAssets().open(desc);
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
                    InputStream is = ct.getAssets().open(rules);
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
                bundle.putString("eventdesc",desctext);
                bundle.putString("eventrules",rulestext);
              Navigation.findNavController(view).navigate(R.id.action_nav_event_registration_to_aboutEvent,bundle);
            }
        });

        }

    @Override
    public int getItemCount() {
        return userDataAndEventLists.size();
    }

    public class MyOwnHolder extends RecyclerView.ViewHolder {
        TextView event_name;
        ImageView event_image;
        CardView mCardVIew;
        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            event_name = (TextView) itemView.findViewById(R.id.text_view_event_name_regitstered_events);
            event_image = (ImageView) itemView.findViewById(R.id.imageView_user_event_image);
            mCardVIew = (CardView) itemView.findViewById(R.id.mycardxx);
        }
    }
}
