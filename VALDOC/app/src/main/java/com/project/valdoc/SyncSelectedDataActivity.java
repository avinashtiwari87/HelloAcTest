package com.project.valdoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SyncSelectedDataActivity extends AppCompatActivity {
    TableLayout table_layout1, table_layout2, table_layout3, table_layout4, table_layout5,
            table_layout6, table_layout7, table_layout8, table_layout9, table_layout10;
    int rows =5, colos =5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_selected_data);
        if(getIntent().hasExtra("rows")){
           rows = getIntent().getIntExtra("rows",6);
            colos = getIntent().getIntExtra("cols",6);
        }


        initRes();
        BuildSyncDataTable(rows,colos);
    }





    private void BuildSyncDataTable(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("SI # "));
                } else {
                    row.addView(addTextView(i-1+""));
                }

            }

            table_layout1.addView(row);
        }

        //Second section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("Select "));
                } else {
                    row.addView(addCheckBox(1+i));
                }

            }
            table_layout2.addView(row);
        }
        //Third section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("    RD NO     "));
                } else {
                    row.addView(addTextView(""));
                }

            }

            table_layout3.addView(row);
        }
        //Fourth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("   Date       "));
                } else {
                    row.addView(addTextView(""));
                }

            }

            table_layout4.addView(row);
        }
        //Fifth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("   Area Name    "));
                } else {
                    row.addView(addTextView(""));
                }

            }

            table_layout5.addView(row);
        }
        //Sixth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("   AHU No   "));
                } else {
                    row.addView(addTextView(1+i+""));
                }

            }

            table_layout6.addView(row);
        }
        //Seventh section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("   Room Name    "));
                } else {
                    row.addView(addTextView(""));
                }

            }

            table_layout7.addView(row);
        }
        //Eight section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("   Room No   "));
                } else {
                    row.addView(addTextView(""));
                }

            }

            table_layout8.addView(row);
        }
        //Ninth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("  Results  "));
                } else {
                    row.addView(addTextView(""));
                }

            }

            table_layout9.addView(row);
        }
        //Tenth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("  Action   "));
                } else {
                    row.addView(addTextView(""));
                }

            }

            table_layout10.addView(row);
        }



    }

    private void initRes() {
        table_layout1 = (TableLayout) findViewById(R.id.syncData_tableLayout1);
        table_layout2 = (TableLayout) findViewById(R.id.syncData_tableLayout2);
        table_layout3 = (TableLayout) findViewById(R.id.syncData_tableLayout3);
        table_layout4 = (TableLayout) findViewById(R.id.syncData_tableLayout4);
        table_layout5 = (TableLayout) findViewById(R.id.syncData_tableLayout5);
        table_layout6 = (TableLayout) findViewById(R.id.syncData_tableLayout6);
        table_layout7 = (TableLayout) findViewById(R.id.syncData_tableLayout7);
        table_layout8 = (TableLayout) findViewById(R.id.syncData_tableLayout8);
        table_layout9 = (TableLayout) findViewById(R.id.syncData_tableLayout9);
        table_layout10 = (TableLayout) findViewById(R.id.syncData_tableLayout10);

    }
    private TextView addTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        //tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size1));
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        return tv;
    }

    private CheckBox  addCheckBox(int row){
        CheckBox cb = new CheckBox(this);
        cb.setHeight(58);
        cb.setBackgroundResource(R.drawable.border1);
        cb.setId(row);
        cb.setTag(row+1);


        return  cb;
    }
}
