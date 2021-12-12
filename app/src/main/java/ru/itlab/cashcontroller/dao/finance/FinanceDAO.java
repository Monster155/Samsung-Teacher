package ru.itlab.cashcontroller.dao.finance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FinanceDAO {
//    @Query("SELECT uid, date, SUM(value) as value FROM Cash WHERE date BETWEEN :startDate AND :endDate GROUP BY date/(1000*60*60*24) ORDER BY date/(1000*60*60*24) DESC")
//    LiveData<List<Finance>> getAllBetweenDatesGroupByDay(long startDate, long endDate);

    @Query("SELECT SUM(value) FROM Finance WHERE value > 0 AND creationTimeInMillis >= (1000 * strftime('%s', datetime('now', '-30 day')))")
    LiveData<Integer> getLastMonthIncome();

    @Query("SELECT SUM(value) FROM Finance WHERE value > 0 AND creationTimeInMillis >= (1000 * strftime('%s', datetime('now', '-7 day')))")
    LiveData<Integer> getLastWeekIncome();

    @Query("SELECT SUM(value) FROM Finance WHERE value > 0 AND creationTimeInMillis >= (1000 * strftime('%s', datetime('now', '-1 day')))")
    LiveData<Integer> getLastDayIncome();

    @Query("SELECT SUM(value) FROM Finance WHERE value < 0 AND creationTimeInMillis >= (1000 * strftime('%s', datetime('now', '-30 day')))")
    LiveData<Integer> getLastMonthOutgo();

    @Query("SELECT SUM(value) FROM Finance WHERE value < 0 AND creationTimeInMillis >= (1000 * strftime('%s', datetime('now', '-7 day')))")
    LiveData<Integer> getLastWeekOutgo();

    @Query("SELECT SUM(value) FROM Finance WHERE value < 0 AND creationTimeInMillis >= (1000 * strftime('%s', datetime('now', '-1 day')))")
    LiveData<Integer> getLastDayOutgo();

    @Query("SELECT SUM(value) FROM Finance")
    LiveData<Integer> getTotal();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Finance finance);

//    @Query("SELECT 0 as uid, :todayNoon as date, SUM(value) as value FROM Cash WHERE date BETWEEN :todayNoon AND :todayNoon+(1000*60*60*24)")
//    LiveData<Cash> getTodaySum(long todayNoon);

    //    @Query("SELECT SUM(value) as value FROM `Cash` UNION ALL SELECT SUM(current_value) as value FROM Target")
//    @Query("SELECT SUM(value) FROM `Cash`")
//    LiveData<Long> getTotal();

//    @Query("SELECT SUM(value) FROM `Cash` WHERE date BETWEEN :monthStart AND :monthEnd AND value > 0")
//    LiveData<Long> getMonthPlus(long monthStart, long monthEnd);

//    @Query("SELECT SUM(value) FROM `Cash` WHERE date BETWEEN :monthStart AND :monthEnd AND value < 0")
//    LiveData<Long> getMonthMinus(long monthStart, long monthEnd);

//    @Query("SELECT * FROM Cash WHERE date BETWEEN :now AND :now-(1000*60*60*24)")
//    LiveData<List<Cash>> getAllForLastDay(long now);
}
