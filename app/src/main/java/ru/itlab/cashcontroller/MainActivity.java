package ru.itlab.cashcontroller;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

import ru.itlab.cashcontroller.cash.Cash;
import ru.itlab.cashcontroller.cash.CashDao;
import ru.itlab.cashcontroller.cash.CashDatabase;
import ru.itlab.cashcontroller.listView.TargetAdapter;
import ru.itlab.cashcontroller.target.Target;
import ru.itlab.cashcontroller.target.TargetDao;
import ru.itlab.cashcontroller.target.TargetDatabase;

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

        TargetDatabase tdb = Room.databaseBuilder(getApplicationContext(),
                TargetDatabase.class, "Targets").build();
        TargetDao targetDao = tdb.targetDao();

        long dayInMillis = 1000 * 60 * 60 * 24;
        long weekInMillis = dayInMillis * 7;
        long monthInMillis = dayInMillis * 30;
        long now = System.currentTimeMillis();
        long time = TimeZone.getDefault().getOffset(now);
        long todayDayStartTime = now - now % dayInMillis - time;

//        LinearLayout targetContainer = findViewById(R.id.targetContainer);

        TextView monthPlus = findViewById(R.id.monthPlus);
        cashDao.getMonthPlus(todayDayStartTime - monthInMillis, todayDayStartTime).observe(this, total -> monthPlus.setText(total == null ? "0" : String.valueOf(total)));
        TextView monthMinus = findViewById(R.id.monthMinus);
        cashDao.getMonthMinus(todayDayStartTime - monthInMillis, todayDayStartTime).observe(this, total -> monthMinus.setText(total == null ? "0" : String.valueOf(total)));

        TextView totalLeft = findViewById(R.id.left);
        AtomicLong cashTotal = new AtomicLong(0);
        AtomicLong targetTotal = new AtomicLong(0);
        cashDao.getTotal().observe(this, total -> {
            cashTotal.set(total == null ? 0 : total);
            totalLeft.setText(total == null ? String.valueOf(-targetTotal.get()) : String.valueOf(cashTotal.get() - targetTotal.get()));
        });
        targetDao.getTotal().observe(this, total -> {
            targetTotal.set(total == null ? 0 : total);
            totalLeft.setText(total == null ? String.valueOf(cashTotal.get()) : String.valueOf(cashTotal.get() - targetTotal.get()));
        });

        TextView[] weekSums = new TextView[]{
                findViewById(R.id.sum1),
                findViewById(R.id.sum2),
                findViewById(R.id.sum3),
                findViewById(R.id.sum4),
                findViewById(R.id.sum5),
                findViewById(R.id.sum6),
                findViewById(R.id.sum7),
        };

//        LayoutInflater inflater = getLayoutInflater();
//        View childLayout = inflater.inflate(R.layout.target, null, false);
//        View childLayout2 = inflater.inflate(R.layout.target, null, false);
//        targetContainer.addView(childLayout);
//        targetContainer.addView(childLayout2);

        for (int i = 0; i < weekSums.length; i++) {
            int index = i;
            cashDao.getTodaySum(todayDayStartTime - i * dayInMillis).observe(this, cash -> {
                System.out.println(cash);
                weekSums[index].setText(cash.value + "");
            });
        }

        findViewById(R.id.addChange).setOnClickListener(view -> {
            ((EditText) findViewById(R.id.changeValue)).setText("");
            findViewById(R.id.changeWindow).setVisibility(View.VISIBLE);
        });
        findViewById(R.id.changeExitButton).setOnClickListener(view -> findViewById(R.id.changeWindow).setVisibility(View.GONE));

        findViewById(R.id.addNewTargetBtn).setOnClickListener(view -> findViewById(R.id.newTargetWindow).setVisibility(View.VISIBLE));
        findViewById(R.id.newTargetExitButton).setOnClickListener(view -> findViewById(R.id.newTargetWindow).setVisibility(View.GONE));

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


        // Construct the data source
        ArrayList<Target> arrayOfUsers = new ArrayList<>();
        // Create the adapter to convert the array to views
        TargetAdapter adapter = new TargetAdapter(this, arrayOfUsers, findViewById(R.id.editTargetWindow));
        // Attach the adapter to a ListView
        ListView lastDayBillsLV = findViewById(R.id.lastDayBills);
        lastDayBillsLV.setAdapter(adapter);


        targetDao.getAllOpen().observe(this, targetList -> {
            adapter.clear();
            adapter.addAll(targetList);
        });


        findViewById(R.id.editTargetAdd).setOnClickListener(view -> {
            EditText editTargetValue = findViewById(R.id.editTargetValue);
            int changeValue = Integer.parseInt(editTargetValue.getText().toString());
            editTargetValue.setText("");
            if (changeValue < 0) {
                findViewById(R.id.editTargetError).setVisibility(View.VISIBLE);
                return;
            }

            UpdateDataFromEditTargetWindow(targetDao, adapter.getCurrentId(), changeValue);

            findViewById(R.id.editTargetWindow).setVisibility(View.GONE);
        });

        findViewById(R.id.editTargetExitButton).setOnClickListener(view -> findViewById(R.id.editTargetWindow).setVisibility(View.GONE));
        findViewById(R.id.targetComplete).setOnClickListener(view -> {
            findViewById(R.id.editTargetWindow).setVisibility(View.GONE);
            findViewById(R.id.targetAchievedWindow).setVisibility(View.VISIBLE);
        });

        findViewById(R.id.targetAdd).setOnClickListener(view -> {
            EditText targetMax = findViewById(R.id.targetMax);
            int max = Integer.parseInt(targetMax.getText().toString());
            targetMax.setText("");

            int selectedId = ((RadioGroup) findViewById(R.id.radio)).getCheckedRadioButtonId();
            String iconText = "$";
            switch (selectedId) {
                case R.id.radioButton:
                    iconText = "\uD83C\uDFD6";
                    break;
                case R.id.radioButton2:
                    iconText = "\uD83D\uDE97";
                    break;
                case R.id.radioButton3:
                    iconText = "\uD83C\uDFE0";
                    break;
                case R.id.radioButton4:
                    iconText = "⭐";
                    break;
            }

            Target target = new Target(System.currentTimeMillis(),
                    iconText,
                    ((EditText) findViewById(R.id.newTargetName)).getText().toString(),
                    max, 0);

            InsertDataFromNewTargetWindow(targetDao, target);

            findViewById(R.id.newTargetWindow).setVisibility(View.GONE);
        });

        findViewById(R.id.closeExitButton).setOnClickListener(view -> findViewById(R.id.targetAchievedWindow).setVisibility(View.GONE));
        findViewById(R.id.closeFinishBtn).setOnClickListener(view -> {
            findViewById(R.id.editTargetWindow).setVisibility(View.GONE);

            int selectedId = ((RadioGroup) findViewById(R.id.closeRadio)).getCheckedRadioButtonId();
            switch (selectedId) {
                case R.id.closeRadioButton:
                    CloseAndReturnTarget(targetDao, adapter.getCurrentId());
                    break;
                case R.id.closeRadioButton2:
                    CloseTarget(targetDao, adapter.getCurrentId());
                    break;
            }
        });
    }

    private void InsertDataFromChangeWindow(CashDao cashDao, long dateInMillis, int value) {
        new Thread() {
            @Override
            public void run() {
                Cash cash = new Cash();
                // set date
                cash.date = dateInMillis;
                // set value
                cash.value = value;
                // insert
                cashDao.insertAll(cash);
            }
        }.start();
    }

    private void InsertDataFromNewTargetWindow(TargetDao targetDao, Target target) {
        new Thread() {
            @Override
            public void run() {
                // insert
                targetDao.insertAll(target);
            }
        }.start();
    }

    private void CloseTarget(TargetDao targetDao, int targetId) {
        new Thread() {
            @Override
            public void run() {
                // get target
                Target target = targetDao.findById(targetId);
                // change is_achieved
                target.isAchieved = true;
                // insert
                targetDao.insertAll(target);
            }
        }.start();
    }

    private void CloseAndReturnTarget(TargetDao targetDao, int targetId) {
        new Thread() {
            @Override
            public void run() {
                // get target
                Target target = targetDao.findById(targetId);
                // change is_achieved
                target.isAchieved = true;
                // make money back to total
                target.now = 0;
                // insert
                targetDao.insertAll(target);
            }
        }.start();
    }

    private void UpdateDataFromEditTargetWindow(TargetDao targetDao, int targetId,
                                                int changeValue) {
        new Thread() {
            @Override
            public void run() {
                // get target
                Target target = targetDao.findById(targetId);
                // update value
                target.now += changeValue;
                // insert
                targetDao.insertAll(target);
            }
        }.start();
    }
}