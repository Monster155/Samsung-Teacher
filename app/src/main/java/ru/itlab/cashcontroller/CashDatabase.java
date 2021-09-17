package ru.itlab.cashcontroller;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Cash.class}, version = 1, exportSchema = false)
//@TypeConverters({Converters.class})
public abstract class CashDatabase extends RoomDatabase {
    public abstract CashDao cashDao();
}