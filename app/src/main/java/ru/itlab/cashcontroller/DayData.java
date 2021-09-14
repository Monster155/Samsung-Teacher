package ru.itlab.cashcontroller;

import androidx.annotation.NonNull;

import java.util.Date;

public class DayData {
    Date date;
    int value;

    public DayData(Date date, int value) {
        this.date = date;
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return date + " = " + value;
    }
}
