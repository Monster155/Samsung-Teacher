package ru.itlab.cashcontroller;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM `Transaction`")
    List<Transaction> getAll();

    @Query("SELECT * FROM `Transaction` WHERE uid IN (:userIds)")
    List<Transaction> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM `Transaction` WHERE date LIKE :date AND " +
            "value LIKE :value LIMIT 1")
    Transaction findByName(long date, int value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Transaction... transactions);

    @Delete
    void delete(Transaction transaction);
}
