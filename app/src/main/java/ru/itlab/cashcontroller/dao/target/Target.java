package ru.itlab.cashcontroller.dao.target;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Target {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "maxValue")
    public int maxValue;

    @ColumnInfo(name = "nowValue")
    public int nowValue;

    public Target(int uid, String name, int maxValue, int nowValue) {
        this.uid = uid;
        this.name = name;
        this.maxValue = maxValue;
        this.nowValue = nowValue;
    }

    @Ignore
    public Target(String name, int maxValue) {
        this.name = name;
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return "Target{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", maxValue=" + maxValue +
                ", nowValue=" + nowValue +
                '}';
    }
}
