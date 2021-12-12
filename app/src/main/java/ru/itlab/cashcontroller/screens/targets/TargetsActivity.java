package ru.itlab.cashcontroller.screens.targets;

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

public class TargetsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_targets);

        TargetDB db = Room.databaseBuilder(getApplicationContext(),
                TargetDB.class, "Target").build();
        TargetDAO targetDao = db.targetDAO();

        EditText nameET = findViewById(R.id.nameET);
        EditText sumET = findViewById(R.id.sumET);

        TextView emptyTextError2 = findViewById(R.id.emptyTextError2);
        TextView emptyTextError = findViewById(R.id.emptyTextError);

        Button createBtn = findViewById(R.id.addBtn);
        createBtn.setOnClickListener(view -> {
            boolean isReturn = false;

            int max = 0;
            try {
                max = Integer.parseInt(sumET.getText().toString());
            } catch (NumberFormatException ex) {
                emptyTextError.setVisibility(View.VISIBLE);
                isReturn = true;
            }

            String name = "";
            try {
                name = nameET.getText().toString();
            } catch (NumberFormatException ex) {
                emptyTextError2.setVisibility(View.VISIBLE);
                isReturn = true;
            }

            if (isReturn) return;

            create(targetDao, new Target(name, max));

            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void create(TargetDAO dao, Target target) {
        new Thread() {
            @Override
            public void run() {
                dao.create(target);
            }
        }.start();
    }
}
