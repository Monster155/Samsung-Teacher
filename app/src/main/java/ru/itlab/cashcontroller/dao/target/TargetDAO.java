package ru.itlab.cashcontroller.dao.target;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TargetDAO {
    @Query("SELECT * FROM Target")
    LiveData<List<Target>> getAllTargets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Target target);

    @Query("SELECT * FROM Target WHERE uid = :id LIMIT 1")
    LiveData<Target> getTarget(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(Target target);

    @Delete
    void remove(Target target);
}
