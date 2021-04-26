package com.example.corona;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import android.content.Intent;


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Corona> data;

    public static final String ENTRY = "com.example.corona.ENTRY";


    // create constructor to initialize context and data sent from SearchActivity
    public Adapter(Context context, List<Corona> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_corona, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }


    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Corona current = data.get(position);
        myHolder.textKeyId.setText(current.getKeyId());
        myHolder.textLastUpdate.setText("Last update: " + current.getLastUpdate());
        myHolder.textConfirmed.setText("Confirmed: " + current.getConfirmed());
        myHolder.textDeaths.setText("Deaths: " + current.getDeaths());
    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textKeyId;
        TextView textLastUpdate;
        TextView textConfirmed;
        TextView textDeaths;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textKeyId = (TextView) itemView.findViewById(R.id.textKeyId);
            textLastUpdate = (TextView) itemView.findViewById(R.id.textLastUpdate);
            textConfirmed = (TextView) itemView.findViewById(R.id.textConfirmed);
            textDeaths = (TextView) itemView.findViewById(R.id.textDeaths);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {
            //  Intent goToNewEntryActivity = new Intent(context, NewEntryActivity.class);
            //   int itemPosition = getAdapterPosition();
            //   Corona corona = data.get(itemPosition);

            //    goToNewEntryActivity.putExtra(ENTRY,corona);
            //     context.startActivity(goToNewEntryActivity);

            Toast.makeText(context, "You clicked an item", Toast.LENGTH_SHORT).show();
        }
    }
}










