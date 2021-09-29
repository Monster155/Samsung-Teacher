package ru.itlab.cashcontroller.listView;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.itlab.cashcontroller.R;
import ru.itlab.cashcontroller.target.Target;

public class TargetAdapter extends ArrayAdapter<Target> {
    private View editTargetWindow;
    private int currentId;

    public TargetAdapter(Context context, ArrayList<Target> users, View editTargetWindow) {
        super(context, 0, users);
        this.editTargetWindow = editTargetWindow;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Target target = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.target, parent, false);

            convertView.setOnClickListener(view -> {
                editTargetWindow.setVisibility(View.VISIBLE);
                currentId = target.id;
            });
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
        if (target.now >= target.max) {
            convertView.setBackgroundColor(Color.GREEN);
        }
        // Return the completed view to render on screen
        return convertView;
    }

    public int getCurrentId() {
        return currentId;
    }
}