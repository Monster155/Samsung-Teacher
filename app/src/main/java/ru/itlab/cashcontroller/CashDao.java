package ru.itlab.cashcontroller;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CashDao {
    @Query("SELECT * FROM `Cash`")
    LiveData<List<Cash>> getAll();

    @Query("SELECT * FROM `Cash` WHERE uid IN (:userIds)")
    List<Cash> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM `Cash` WHERE date LIKE :date AND " +
            "value LIKE :value LIMIT 1")
    Cash findByName(long date, int value);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Cash... cashes);

    @Delete
    void delete(Cash cash);

    @Query("SELECT uid, date, SUM(value) as value FROM Cash WHERE date BETWEEN :startDate AND :endDate GROUP BY date/(1000*60*60*24) ORDER BY date/(1000*60*60*24) DESC")
    LiveData<List<Cash>> getAllBetweenDatesGroupByDay(long startDate, long endDate);

    @Query("SELECT 0 as uid, :todayNoon as date, SUM(value) as value FROM Cash WHERE date BETWEEN :todayNoon AND :todayNoon+(1000*60*60*24)")
    LiveData<Cash> getTodaySum(long todayNoon);

    @Query("SELECT SUM(value) FROM `Cash`")
    LiveData<Long> getTotal();

    @Query("SELECT SUM(value) FROM `Cash` WHERE date BETWEEN :monthStart AND :monthEnd AND value > 0")
    LiveData<Long> getMonthPlus(long monthStart, long monthEnd);

    @Query("SELECT SUM(value) FROM `Cash` WHERE date BETWEEN :monthStart AND :monthEnd AND value < 0")
    LiveData<Long> getMonthMinus(long monthStart, long monthEnd);

    @Query("SELECT * FROM Cash WHERE date BETWEEN :now AND :now-(1000*60*60*24)")
    LiveData<List<Cash>> getAllForLastDay(long now);
}
