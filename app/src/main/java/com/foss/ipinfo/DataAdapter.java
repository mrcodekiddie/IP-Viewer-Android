package com.foss.ipinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Data> data;
    private Context context;

    public DataAdapter(List<Data> data, Context context) {

        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_card, viewGroup, false);
        return new DataAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Data mdata = data.get(i);
        Log.v("key val ", mdata.key + " " + mdata.value);
        viewHolder.keyTextView.setText(mdata.key);
        viewHolder.valueTextView.setText(mdata.value);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView keyTextView, valueTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            keyTextView = itemView.findViewById(R.id.tv_key);
            valueTextView = itemView.findViewById(R.id.tv_value);
        }
    }
}
