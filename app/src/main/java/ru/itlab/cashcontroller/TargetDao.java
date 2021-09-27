package ru.itlab.cashcontroller;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TargetDao {
    @Query("SELECT * FROM `Target` WHERE NOT is_achieved")
    LiveData<List<Target>> getAllOpen();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Target... targets);
}
