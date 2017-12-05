package com.example.user.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MAIN";

    private CalendarView mCalendarView;
    Button Start;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    String SQLiteDataBaseQueryHolder;
    String StartDateHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Start = (Button) findViewById(R.id.start);

        mCalendarView = findViewById(R.id.calendarView);

        sqLiteHelper = new SQLiteHelper(this);

        // Adding click listener to start button.
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating SQLite database if doesn't exists
                SQLiteDataBaseBuild();

                // Creating SQLite table if doesn't exists.
                SQLiteTableBuild();

                //Insert data into SQLite
                InsertDataIntoSQLiteDatabase();


            }
        });

    }

    // SQLite database build method.
    public void SQLiteDataBaseBuild() {

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_StartDate + " VARCHAR);");

    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase() {


            mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String selectedDateString = day + "/" + month + "/" + year;
                    Date selectedDate = null;

                    try {

                        selectedDate = sdf.parse(selectedDateString);

                    } catch (ParseException e) {
                        Log.d(TAG, e.getLocalizedMessage());
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(selectedDate);

                    {
                        // SQLite query to insert data into table.
                        SQLiteDataBaseQueryHolder = "INSERT INTO " + SQLiteHelper.TABLE_NAME + " (startDate) VALUES('" + StartDateHolder + "');";

                        // Executing query.
                        sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

                        // Closing SQLite database object.
                        sqLiteDatabaseObj.close();

                        // Printing toast message after done inserting.
                        Toast.makeText(MainActivity.this, "Date Recorded Successfully", Toast.LENGTH_LONG).show();

                    }

                }}
            );


    }
}

