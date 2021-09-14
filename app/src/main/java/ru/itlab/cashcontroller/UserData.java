package ru.itlab.cashcontroller;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class UserData {
    ArrayList<DayData> daysData;

    public UserData() {
        daysData = new ArrayList<>();
    }

    public void addNewDay(DayData data) {
        daysData.add(data);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DayData d : daysData) {
            sb.append(d.toString()).append("; ");
        }
        return sb.toString();

    }
}