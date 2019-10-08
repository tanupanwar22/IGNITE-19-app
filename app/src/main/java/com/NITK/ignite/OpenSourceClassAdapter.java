package com.NITK.ignite;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OpenSourceClassAdapter extends RecyclerView.Adapter<OpenSourceClassAdapter.MyOwnHolder> {

    ArrayList<OpenSourcePOJO > mList;
    Context context;

    OpenSourceClassAdapter(){

    }

    public OpenSourceClassAdapter(ArrayList<OpenSourcePOJO> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }



    @NonNull
    @Override
    public MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.opensource_card,parent,false);
        return new MyOwnHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOwnHolder holder, int position) {
        holder.projectName.setText(mList.get(position).getProjectName());
        holder.projectLink.setText(mList.get(position).getProjectCode());
        holder.projectLicense.setText(mList.get(position).getProjectLicense());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyOwnHolder extends RecyclerView.ViewHolder {

        TextView projectName,projectLink,projectLicense;
        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            projectLicense = itemView.findViewById(R.id.xx_license_link);
            projectName = itemView.findViewById(R.id.xx_project_name);
            projectLink = itemView.findViewById(R.id.xx_project_link);
            projectLicense.setMovementMethod(LinkMovementMethod.getInstance());
            projectLink.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
