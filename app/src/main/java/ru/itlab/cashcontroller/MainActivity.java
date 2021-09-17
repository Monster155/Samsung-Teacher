package ru.itlab.cashcontroller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Log";
    private List<Cash> cashList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CashDatabase db = Room.databaseBuilder(getApplicationContext(),
                CashDatabase.class, "Finance").build();
        CashDao cashDao = db.cashDao();

        long dayInMillis = 1000 * 60 * 60 * 24;
        long weekInMillis = dayInMillis * 7;
        long monthInMillis = dayInMillis * 30;
        long now = System.currentTimeMillis();
        long time = TimeZone.getDefault().getOffset(now);
        long todayDayStartTime = now - now % dayInMillis - time;

        LinearLayout targetContainer = findViewById(R.id.targetContainer);

        TextView monthPlus = findViewById(R.id.monthPlus);
        cashDao.getMonthPlus(todayDayStartTime - monthInMillis, todayDayStartTime).observe(this, total -> monthPlus.setText(total == null ? "0" : String.valueOf(total)));
        TextView monthMinus = findViewById(R.id.monthMinus);
        cashDao.getMonthMinus(todayDayStartTime - monthInMillis, todayDayStartTime).observe(this, total -> monthMinus.setText(total == null ? "0" : String.valueOf(total)));

        TextView totalLeft = findViewById(R.id.left);
        cashDao.getTotal().observe(this, total -> totalLeft.setText(total == null ? "0" : String.valueOf(total)));

        TextView[] weekSums = new TextView[]{
                findViewById(R.id.sum1),
                findViewById(R.id.sum2),
                findViewById(R.id.sum3),
                findViewById(R.id.sum4),
                findViewById(R.id.sum5),
                findViewById(R.id.sum6),
                findViewById(R.id.sum7),
        };

        LayoutInflater inflater = getLayoutInflater();
        View childLayout = inflater.inflate(R.layout.target, null, false);
        View childLayout2 = inflater.inflate(R.layout.target, null, false);
        targetContainer.addView(childLayout);
        targetContainer.addView(childLayout2);

        for (int i = 0; i < weekSums.length; i++) {
            int index = i;
            cashDao.getTodaySum(todayDayStartTime - i * dayInMillis).observe(this, cash -> {
                System.out.println(cash);
                weekSums[index].setText(cash.value + "");
            });
        }

        findViewById(R.id.addChange).setOnClickListener(view -> findViewById(R.id.changeWindow).setVisibility(View.VISIBLE));
        findViewById(R.id.exitButton).setOnClickListener(view -> findViewById(R.id.changeWindow).setVisibility(View.GONE));

//        findViewById(R.id.changePlus).setOnClickListener(view -> findViewById(R.id.changeMinus).);
        findViewById(R.id.changeAdd).setOnClickListener(view -> {
            DatePicker datePicker = findViewById(R.id.datePicker);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            int value = Integer.parseInt(((EditText) findViewById(R.id.changeValue)).getText().toString());

            if (((Switch) findViewById(R.id.changeSwitch)).isChecked()) {
                if (value > 0) {
                    value = -value;
                }
            } else {
                if (value < 0) {
                    value = -value;
                }
            }

            InsertDataFromChangeWindow(cashDao, calendar.getTimeInMillis(), value);

            findViewById(R.id.changeWindow).setVisibility(View.GONE);
        });
    }

    private void InsertDataFromChangeWindow(CashDao cashDao, long dateInMillis, int value) {
        new Thread() {
            @Override
            public void run() {
                Cash cash = new Cash();
                // date
                cash.date = dateInMillis;

                // value
                cash.value = value;

                // insert
                cashDao.insertAll(cash);
            }
        }.start();
    }
}