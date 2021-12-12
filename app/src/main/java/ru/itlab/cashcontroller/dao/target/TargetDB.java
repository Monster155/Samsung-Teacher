package ru.itlab.cashcontroller.dao.target;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.itlab.cashcontroller.dao.finance.Finance;
import ru.itlab.cashcontroller.dao.finance.FinanceDAO;

@Database(entities = {Target.class}, version = 1, exportSchema = false)
//@TypeConverters({Converters.class})
public abstract class TargetDB extends RoomDatabase {
    public abstract TargetDAO targetDAO();
}
