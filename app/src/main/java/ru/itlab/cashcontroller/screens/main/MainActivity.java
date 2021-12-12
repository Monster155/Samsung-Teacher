package ru.itlab.cashcontroller.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import ru.itlab.cashcontroller.R;
import ru.itlab.cashcontroller.dao.finance.FinanceDAO;
import ru.itlab.cashcontroller.dao.finance.FinanceDB;
import ru.itlab.cashcontroller.dao.target.Target;
import ru.itlab.cashcontroller.dao.target.TargetDAO;
import ru.itlab.cashcontroller.dao.target.TargetDB;
import ru.itlab.cashcontroller.net.MoneyJson;
import ru.itlab.cashcontroller.recyclerViewAdapter.Adapter;
import ru.itlab.cashcontroller.screens.changeTotal.ChangeTotalActivity;
import ru.itlab.cashcontroller.screens.targets.TargetsActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views from activity
        TextView totalTV = findViewById(R.id.totalTV);

        TextView totalDollarTV = findViewById(R.id.totalDollarTV);
        TextView totalEuroTV = findViewById(R.id.totalEuroTV);

        TextView incomeMonthTV = findViewById(R.id.incomeMTV);
        TextView outgoMonthTV = findViewById(R.id.outgoMTV);
        TextView incomeWeekTV = findViewById(R.id.incomeWTV);
        TextView outgoWeekTV = findViewById(R.id.outgoWTV);
        TextView incomeDayTV = findViewById(R.id.incomeDTV);
        TextView outgoDayTV = findViewById(R.id.outgoDTV);

        ImageButton changeTotalBtn = findViewById(R.id.changeTotalBtn);
        ImageButton targetsBtn = findViewById(R.id.targetsBtn);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        //
        ArrayList<Target> ts = new ArrayList<>();
        Adapter adapter = new Adapter(this, ts);
        recyclerView.setAdapter(adapter);

        changeTotalBtn.setOnClickListener(view -> startActivity(new Intent(this, ChangeTotalActivity.class)));
        targetsBtn.setOnClickListener(view -> startActivity(new Intent(this, TargetsActivity.class)));


        // set observers for database queries
        FinanceDB financeDB = Room.databaseBuilder(getApplicationContext(),
                FinanceDB.class, "Finance").build();
        FinanceDAO financeDao = financeDB.financeDao();

        financeDao.getLastMonthIncome().observe(this, value -> incomeMonthTV.setText(String.valueOf(value == null ? 0 : value)));
        financeDao.getLastMonthOutgo().observe(this, value -> outgoMonthTV.setText(String.valueOf(value == null ? 0 : value)));
        financeDao.getLastWeekIncome().observe(this, value -> incomeWeekTV.setText(String.valueOf(value == null ? 0 : value)));
        financeDao.getLastWeekOutgo().observe(this, value -> outgoWeekTV.setText(String.valueOf(value == null ? 0 : value)));
        financeDao.getLastDayIncome().observe(this, value -> incomeDayTV.setText(String.valueOf(value == null ? 0 : value)));
        financeDao.getLastDayOutgo().observe(this, value -> outgoDayTV.setText(String.valueOf(value == null ? 0 : value)));


        //
        TargetDB targetDB = Room.databaseBuilder(getApplicationContext(),
                TargetDB.class, "Target").build();
        TargetDAO targetDao = targetDB.targetDAO();

        targetDao.getAllTargets().observe(this, targets -> {
            System.out.println("Targets:" + Arrays.toString(targets.toArray()));
            adapter.updateList(targets);
        });


        //
        final MoneyJson[] moneyJson = new MoneyJson[1];
        final Integer[] total = new Integer[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URLConnection connection = new URL("https://www.cbr-xml-daily.ru/latest.js").openConnection();
                    connection.setRequestProperty("Accept-Charset", String.valueOf(StandardCharsets.UTF_8));
                    InputStream response = connection.getInputStream();

                    Thread.sleep(1000);

                    try (Scanner scanner = new Scanner(response)) {
                        String responseBody = scanner.useDelimiter("\\A").next();

                        Gson gson = new Gson();
                        moneyJson[0] = gson.fromJson(responseBody, MoneyJson.class);
                        System.out.println("Test: " + moneyJson[0]);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        //
        financeDao.getTotal().observe(this, value -> {
            total[0] = value == null ? 0 : value;

            totalTV.setText(String.valueOf(total[0]));
        });


        //
        totalDollarTV.post(() -> {
            while (thread.isAlive()) {
            }
            totalDollarTV.setText(String.format("%.2f", moneyJson[0].rates.USD * total[0]));
            totalEuroTV.setText(String.format("%.2f", moneyJson[0].rates.EUR * total[0]));
        });
    }
}
