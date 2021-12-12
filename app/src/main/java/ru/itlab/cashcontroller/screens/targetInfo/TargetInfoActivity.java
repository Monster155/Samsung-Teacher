package ru.itlab.cashcontroller.screens.targetInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import ru.itlab.cashcontroller.R;
import ru.itlab.cashcontroller.dao.target.Target;
import ru.itlab.cashcontroller.dao.target.TargetDAO;
import ru.itlab.cashcontroller.dao.target.TargetDB;
import ru.itlab.cashcontroller.screens.main.MainActivity;

public class TargetInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_info);

        int id = getIntent().getIntExtra("id", 0);
        final Target[] target = new Target[1];

        TextView nameTV = findViewById(R.id.targetName);
        TextView emptyTextError = findViewById(R.id.emptyTextError);
        EditText sumET = findViewById(R.id.sumET);
        TextView targetTotalTV = findViewById(R.id.targetTotal);
        TextView targetNowTV = findViewById(R.id.targetNow);
        Button addBtn = findViewById(R.id.addBtn);
        Button closeBtn = findViewById(R.id.closeBtn);

        TargetDB targetDB = Room.databaseBuilder(getApplicationContext(),
                TargetDB.class, "Target").build();
        TargetDAO targetDao = targetDB.targetDAO();

        targetDao.getTarget(id).observe(this, t -> {
            if (t == null) return;

            nameTV.setText(t.name);
            targetTotalTV.setText(String.valueOf(t.maxValue));
            targetNowTV.setText(String.valueOf(t.nowValue));
            target[0] = t;
        });

        addBtn.setOnClickListener(view -> {
            int sum = 0;
            try {
                sum = Integer.parseInt(sumET.getText().toString());
            } catch (NumberFormatException ex) {
                emptyTextError.setVisibility(View.VISIBLE);
            }

            if (target[0] == null) return;

            Target t = target[0];
            t.nowValue += sum;

            update(targetDao, t);
            sumET.setText("");
        });
        closeBtn.setOnClickListener(view -> {
            if (target[0] == null) return;

            remove(targetDao, target[0]);

            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void update(TargetDAO dao, Target newTarget) {
        new Thread() {
            @Override
            public void run() {
                dao.update(newTarget);
            }
        }.start();
    }

    private void remove(TargetDAO dao, Target target) {
        new Thread() {
            @Override
            public void run() {
                dao.remove(target);
            }
        }.start();
    }
}
