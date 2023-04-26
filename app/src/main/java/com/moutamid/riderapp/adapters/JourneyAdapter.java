package com.moutamid.riderapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.riderapp.R;
import com.moutamid.riderapp.models.JourneysModel;

import java.util.ArrayList;
import java.util.List;


public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.JourneyVH> {

    Context context;
    List<JourneysModel> list;

    public JourneyAdapter(Context context, List<JourneysModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public JourneyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.journey_item, parent, false);
        return new JourneyVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JourneyVH holder, int position) {
        JourneysModel model = list.get(holder.getAdapterPosition());
        holder.start.setText(model.getStartJourney());
        holder.end.setText(model.getEndJourney());
        holder.spent.setText(model.getSpent()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class JourneyVH extends RecyclerView.ViewHolder{
        TextView spent, start, end;
        public JourneyVH(@NonNull View itemView) {
            super(itemView);
            spent = itemView.findViewById(R.id.spent);
            start = itemView.findViewById(R.id.started);
            end = itemView.findViewById(R.id.ended);
        }
    }
}
