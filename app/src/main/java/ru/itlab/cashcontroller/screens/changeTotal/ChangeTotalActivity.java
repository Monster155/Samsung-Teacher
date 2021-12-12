package ru.itlab.cashcontroller.screens.changeTotal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Calendar;

import ru.itlab.cashcontroller.R;
import ru.itlab.cashcontroller.dao.finance.Finance;
import ru.itlab.cashcontroller.dao.finance.FinanceDAO;
import ru.itlab.cashcontroller.dao.finance.FinanceDB;
import ru.itlab.cashcontroller.screens.main.MainActivity;

public class ChangeTotalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_total);

        TextView emptyTextErrorTV = findViewById(R.id.emptyTextError);
        EditText sumET = findViewById(R.id.sumET);
        Button insertBtn = findViewById(R.id.addBtn);

        FinanceDB db = Room.databaseBuilder(getApplicationContext(),
                FinanceDB.class, "Finance").build();
        FinanceDAO financeDao = db.financeDao();

        insertBtn.setOnClickListener(view -> {
            int sum = 0;
            try {
                sum = Integer.parseInt(sumET.getText().toString());
            } catch (NumberFormatException ex) {
                emptyTextErrorTV.setVisibility(View.VISIBLE);
                return;
            }

            DatePicker datePicker = findViewById(R.id.datePicker);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            insert(financeDao, new Finance(calendar.getTimeInMillis(), sum));
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void insert(FinanceDAO dao, Finance finance) {
        new Thread() {
            @Override
            public void run() {
                dao.insert(finance);
            }
        }.start();
    }
}
