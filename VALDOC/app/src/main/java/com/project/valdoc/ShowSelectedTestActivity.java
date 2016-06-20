package com.project.valdoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.valdoc.utility.Utilityies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowSelectedTestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "ShowSelectedTest";
    private String userName = "";
    SharedPreferences sharedpreferences;
    Spinner selectTestSpinner;
    ListView listview;
    String[] roomsValues, AHUValues, equipmentValues;
    ArrayList<String> list = null;
    StableArrayAdapter listViewAdapter = null;
    String selectedItem="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_selected_test);

        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        userName = sharedpreferences.getString("USERNAME", "");


        findViewById(R.id.go_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("Airvelocity Test (ERD_AV)".equals(selectedItem)) {
                    Intent intent = new Intent(ShowSelectedTestActivity.this, SyncSelectedDataActivity.class);
                    intent.putExtra("TestType", selectedItem);
                    intent.putExtra("rows", 11);
                    intent.putExtra("cols", 11);
                    startActivity(intent);
                    Log.d(TAG, "CodeFlow selectedItem " + selectedItem);
                }else {
                    //Toast.makeText(ShowSelectedTestActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
                    Utilityies.showAlert(ShowSelectedTestActivity.this,"Under Development");
                }

            }
        });

        // Spinner element
        selectTestSpinner = (Spinner) findViewById(R.id.spinner_select_test);
        // Spinner click listener
        selectTestSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.select_test_option, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        selectTestSpinner.setAdapter(adapter);

        //Temporary List View
        listview = listview = (ListView) findViewById(R.id.listView);
        roomsValues = new String[] {"Airchanges/hr Test (ACPH_AV)",
                "Airchanges/hr Test (ACPH_H)", "Filter Integrity Test (FIT)",
                "Particle Count Test (PCT)", "Recovery Test (RCT)"};

        AHUValues = new String[] {"Airflow Test (ARD_AF_AHU)" ,
                "Filter Integrity Test (ARD_FIT_AHU)"};

        equipmentValues = new String[] {"Airvelocity Test (ERD_AV)",
                "Filter Integrity Test (ERD_FIT)", "Particle Count Test (ERD_PCT)",
                "Recovery Test (ERD_RCT)"};

        list = new ArrayList<String>();
        listViewAdapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_single_choice, list);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setAdapter(listViewAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(ShowSelectedTestActivity.this, selectedItem, Toast.LENGTH_SHORT).show();

            }

        });



        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(ShowSelectedTestActivity.this, mActionBar, userName);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        String selection = parent.getItemAtPosition(position).toString();
        Toast.makeText(ShowSelectedTestActivity.this, selection, Toast.LENGTH_SHORT).show();
        if("AHU".equals(selection)){
            updateListViewOnTestSelection(AHUValues);
        }else if("Equipment".equals(selection)){
            updateListViewOnTestSelection(equipmentValues);
        }else if("Rooms".equals(selection)){
            updateListViewOnTestSelection(roomsValues);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private void updateListViewOnTestSelection(String[] values){
        ProgressDialog progress = null;
        progress = ProgressDialog.show(ShowSelectedTestActivity.this, null, "Loading...");
        listViewAdapter.clear();
        list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        listViewAdapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_single_choice, list);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setAdapter(listViewAdapter);
        progress.dismiss();

    }
}
