package ru.itlab.cashcontroller.dao.finance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Finance {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "creationTimeInMillis")
    public long creationTimeInMillis;

    @ColumnInfo(name = "value")
    public int value;

    public Finance(int uid, long creationTimeInMillis, int value) {
        this.uid = uid;
        this.creationTimeInMillis = creationTimeInMillis;
        this.value = value;
    }

    @Ignore
    public Finance(int value) {
        this.value = value;
        creationTimeInMillis = System.currentTimeMillis();
    }

    @Ignore
    public Finance(long creationTimeInMillis, int value) {
        this.creationTimeInMillis = creationTimeInMillis;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "uid=" + uid +
                ", date=" + creationTimeInMillis +
                ", value=" + value +
                '}';
    }
}
