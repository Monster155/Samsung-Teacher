package ru.itlab.cashcontroller;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Target {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "open_date")
    public long openDate;

    @ColumnInfo(name = "icon_text")
    public String iconText;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "max_value")
    public int max;

    @ColumnInfo(name = "current_value")
    public int now;

    @ColumnInfo(name = "is_achieved")
    public boolean isAchieved;

    public Target(long openDate, String iconText, String name, int max, int now) {
        this.openDate = openDate;
        this.iconText = iconText;
        this.name = name;
        this.max = max;
        this.now = now;
        isAchieved = false;
    }

    @Override
    public String toString() {
        return "Target{" +
                "id=" + id +
                ", openDate=" + openDate +
                ", iconText='" + iconText + '\'' +
                ", name='" + name + '\'' +
                ", max=" + max +
                ", now=" + now +
                ", isAchieved=" + isAchieved +
                '}';
    }
}
