package com.project.valdoc;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.project.valdoc.db.ValdocDatabaseHandler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.d("valdoc", "main oncreat start");
//        ValdocDatabaseHandler valdocDatabaseHandler = new ValdocDatabaseHandler(MainActivity1.this);
//        SQLiteDatabase sqLiteDatabase = valdocDatabaseHandler.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
    }


}
