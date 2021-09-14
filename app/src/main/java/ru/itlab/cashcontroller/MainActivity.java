package ru.itlab.cashcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.room.Room;

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

        DataBaseThread dataBaseThread = new DataBaseThread(userDao);
        dataBaseThread.start();

        /*
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://finances-142e2-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("UserData");

        Gson gson = new Gson();

        myRef.child(new Date(System.currentTimeMillis()).toString()).setValue(100);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                UserData userData = gson.fromJson(value, UserData.class);
                Log.d(TAG, "onDataChange: " + userData);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        */
    }
}