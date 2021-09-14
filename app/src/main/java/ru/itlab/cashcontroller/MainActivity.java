package ru.itlab.cashcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.room.Room;

import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout targetContainer = findViewById(R.id.targetContainer);

        TextView monthPlus = findViewById(R.id.monthPlus);
        TextView monthMinus = findViewById(R.id.monthMinus);

        TextView totalLeft = findViewById(R.id.left);

        TextView weekSums[] = new TextView[]{
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

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Finance").build();
        UserDao userDao = db.userDao();

        Insert(userDao);
        Show(userDao);
    }

    private void Insert(UserDao userDao) {
        new Thread() {
            @Override
            public void run() {
                Transaction t1 = new Transaction();
                t1.date = System.currentTimeMillis();
                t1.value = 100;

                userDao.insertAll(t1);
            }
        }.start();
    }

    private void Show(UserDao userDao) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Transaction> tr = userDao.getAll();
                System.out.println("Transactions: ");
                for (Transaction t : tr) {
                    System.out.println(t);
                }
            }
        }.start();
    }
}