package ru.itlab.cashcontroller.target;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Target.class}, version = 1, exportSchema = false)
public abstract class TargetDatabase extends RoomDatabase {
    public abstract TargetDao targetDao();
}