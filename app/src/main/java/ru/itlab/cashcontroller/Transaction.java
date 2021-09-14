package ru.itlab.cashcontroller;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "value")
    public int value;

    @Override
    public String toString() {
        return "Transaction{" +
                "uid=" + uid +
                ", date=" + date +
                ", value=" + value +
                '}';
    }
}