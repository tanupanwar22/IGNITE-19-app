package com.NITK.ignite.ui.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.NITK.ignite.R;

import java.util.ArrayList;

public class NavigationRecyclerAdapter extends RecyclerView.Adapter<NavigationRecyclerAdapter.MyOwnHolder> {

private ArrayList<VenueList > venue_list = new ArrayList<>();
private Context ct;
private int lastPosition = -1;


public NavigationRecyclerAdapter(){

}

public NavigationRecyclerAdapter(Context ct, ArrayList<VenueList> venue_list){
    this.ct = ct;
    this.venue_list = venue_list;
}

    @Override
    public MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v;
        v= LayoutInflater.from(ct).inflate(R.layout.venue_list_recycler_card,parent,false);
        MyOwnHolder vHolder = new MyOwnHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOwnHolder holder, final int position) {
        holder.venue_name.setText(venue_list.get(position).getVenue_name());
       // setAnimation(holder.itemView,position);
       // Glide.with(ct).load(R.drawable.robot).into(holder.gif);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = venue_list.get(position).getVenue_latitude();
                double longitude = venue_list.get(position).getVenue_longitude();
                String myUri = String.format ("geo:%f,%f?q=%f,%f(%s)",latitude,longitude,latitude,longitude,"Mylocation");
                Uri gmmIntentUri = Uri.parse(myUri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(ct.getPackageManager()) != null) {
                    ct.startActivity(mapIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return venue_list.size();
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
            Animation animation = AnimationUtils.loadAnimation(ct, android.R.anim.slide_in_left);

            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class MyOwnHolder extends RecyclerView.ViewHolder {
    TextView venue_name;
    CardView mCardView;
    //ImageView gif;
        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            venue_name = (TextView) itemView.findViewById(R.id.venue_name_recycler_card);
            mCardView = (CardView) itemView.findViewById(R.id.m_card_view);
      //      gif=itemView.findViewById(R.id.navigation_cardview_gif);
        }

        public void clearAnimation() {
            itemView.clearAnimation();
        }
    }
}
