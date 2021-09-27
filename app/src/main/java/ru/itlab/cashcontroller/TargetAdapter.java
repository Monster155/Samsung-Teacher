package ru.itlab.cashcontroller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TargetAdapter extends ArrayAdapter<Target> {
    public TargetAdapter(Context context, ArrayList<Target> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Target target = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.target, parent, false);
        }
        // Lookup view for data population
        TextView textIcon = convertView.findViewById(R.id.targetTextIcon);
        TextView name = convertView.findViewById(R.id.targetName);
        TextView max = convertView.findViewById(R.id.targetMaxText);
        TextView now = convertView.findViewById(R.id.targetNowText);

        Log.d("Target", target.toString());
        // Populate the data into the template view using the data object
        textIcon.setText(target.iconText);
        name.setText(target.name);
        max.setText(String.valueOf(target.max));
        now.setText(String.valueOf(target.now));
        // Return the completed view to render on screen
        return convertView;
    }
}
