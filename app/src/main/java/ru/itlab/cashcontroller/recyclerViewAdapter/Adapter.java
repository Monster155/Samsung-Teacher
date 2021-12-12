package ru.itlab.cashcontroller.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.itlab.cashcontroller.R;
import ru.itlab.cashcontroller.dao.target.Target;
import ru.itlab.cashcontroller.screens.targetInfo.TargetInfoActivity;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Target> list;
    private Context ctx;

    public Adapter(Context ctx, List<Target> list) {
        this.list = list;
        this.ctx = ctx;
    }

    public void updateList(List<Target> items) {
        if (items != null && items.size() > 0) {
            list.clear();
            list.addAll(items);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_target, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Target target = list.get(position);
        holder.textMax.setText(String.valueOf(target.maxValue));
        holder.textNow.setText(String.valueOf(target.nowValue));
        holder.textName.setText(target.name);
        holder.layout.setOnClickListener(view -> {
            Intent intent = new Intent(ctx, TargetInfoActivity.class);
            intent.putExtra("id", target.uid);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textMax, textNow, textName;
        public ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMax = itemView.findViewById(R.id.textViewMax);
            textNow = itemView.findViewById(R.id.textViewNow);
            textName = itemView.findViewById(R.id.textViewName);
            layout = itemView.findViewById(R.id.mainLayout);
        }
    }

}
