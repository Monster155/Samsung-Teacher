package ru.itlab.cashcontroller.cash;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cash.class}, version = 1, exportSchema = false)
//@TypeConverters({Converters.class})
public abstract class CashDatabase extends RoomDatabase {
    public abstract CashDao cashDao();
}