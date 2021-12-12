package ru.itlab.cashcontroller.dao.finance;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Finance.class}, version = 1, exportSchema = false)
//@TypeConverters({Converters.class})
public abstract class FinanceDB extends RoomDatabase {
    public abstract FinanceDAO financeDao();
}
