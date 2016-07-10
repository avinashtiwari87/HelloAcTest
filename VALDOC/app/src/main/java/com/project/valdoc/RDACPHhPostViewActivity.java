package com.project.valdoc;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.project.valdoc.db.ValdocDatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class RDACPHhPostViewActivity extends AppCompatActivity {
    private static final String TAG = "RDACPHhPostView";
    TextView headerText;
    TableLayout test3_table_layout, test3_table_layout2, test3_table_layout3, test3_table_layout4,
            test3_table_layout5;
    ProgressDialog pr;
    private TextView TFRTxtv;
    private TextView TFTAVTxtv;
    ArrayList<TextView> txtViewList;
    ArrayList<TextView>avgTxtViewList;
    ArrayList<TextView> roomVolumeTxtList;
    ArrayList<TextView> totalAirFlowRateTxtList;
    ArrayList<TextView> airChangeTxtList;;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdacphh_post_view);

        initRes();

        txtViewList = new ArrayList<TextView>();
        avgTxtViewList = new ArrayList<TextView>();
        roomVolumeTxtList = new ArrayList<TextView>();
        totalAirFlowRateTxtList = new ArrayList<TextView>();
        airChangeTxtList = new ArrayList<TextView>();

    }


    private void BuildTableTest3(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Grille/Filter ID No\n "));
                } else {
/*                    if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                        HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
                        Log.d("valdoc", "DynamicTableActivity grillAndSizeFromGrill=" + grillAndSizeFromGrill.size() + "i=" + i);
                        row.addView(addTextView(grill.get(ValdocDatabaseHandler.GRILL_GRILLCODE).toString()));
                    } else {*/
                        row.addView(addTextView("grillAndSizeFromGrill"));
                    //}
//                    row.addView(addTextView(" Filter No " + i));
                }
            }
            test3_table_layout.addView(row);
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
                    row.addView(addTextView("Measured Air Flow Quantity\n Q1 (in cfm) "));
                } else {
                    //row.addView(addTextView(" "));
                    row.addView(addInputDataTextView());
                }
            }
            test3_table_layout2.addView(row);
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
                    row.addView(addTextView("Air Flow Rate\n(Average) cfm "));
                } else {
                    //row.addView(addTextViewWithoutBorder("0"));
                    //row.addView(addTextViewWithIdsNoBorder(i, totalAirFlowRateIds, totalAirFlowRateTxtList));
                    row.addView(addAverageInputDataTextView());
                }
            }
            test3_table_layout3.addView(row);
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
                    row.addView(addTextView(" Room Volume in\n ft3(RV)"));
                } else {
                    //row.addView(addTextViewWithoutBorder(""+room.getVolume()));
                    row.addView(addTextViewWithIdsNoBorder(i, 0, roomVolumeTxtList));
                }
            }
            test3_table_layout4.addView(row);
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
                    row.addView(addTextView("No. of Air Changes/Hr\n ((TFR/RV)x60))"));
                } else {
                    //row.addView(addTextViewWithoutBorder("490"));
                    row.addView(addTextViewWithIdsNoBorder(i, 0, airChangeTxtList));
                }
            }
            test3_table_layout5.addView(row);
        }

        //dismiss progressbar
        if (pr.isShowing())
            pr.dismiss();


    }


    private TextView addTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        return tv;
    }

    int idCountEtv = 200;

    private TextView addInputDataTextView() {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setId(idCountEtv);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        idCountEtv++;
        txtViewList.add(tv);
        return tv;
    }

    int idAvgtv = 600;
    private TextView addAverageInputDataTextView() {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setId(idAvgtv);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        idAvgtv++;
        avgTxtViewList.add(tv);
        return tv;
    }

    private TextView addTextViewWithIdsNoBorder(int Tag, int ids, ArrayList<TextView> txtViewList) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        //tv.setBackgroundResource(R.drawable.border);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        Log.d(TAG, "No Border idCountTv " + ids);
        if (ids > 0)
            tv.setId(ids);
        tv.setTag(Tag);
        ids++;
        txtViewList.add(tv);
        return tv;
    }

    private void initRes() {
        headerText = (TextView) findViewById(R.id.common_header_tv);
//        headerText.setText("* Air Flow Velocity, Volume Testing and Determination of Air Changes per Hour Rates *");
        //Test3
        test3_table_layout = (TableLayout) findViewById(R.id.test3_tableLayout1);
        test3_table_layout2 = (TableLayout) findViewById(R.id.test3_tableLayout2);
        test3_table_layout3 = (TableLayout) findViewById(R.id.test3_tableLayout3);
        test3_table_layout4 = (TableLayout) findViewById(R.id.test3_tableLayout4);
        test3_table_layout4.setVisibility(View.GONE);
        test3_table_layout5 = (TableLayout) findViewById(R.id.test3_tableLayout5);
        test3_table_layout5.setVisibility(View.GONE);
        TFRTxtv = (TextView)findViewById(R.id.acph_h_tfr_value_tv);
        TFTAVTxtv = (TextView)findViewById(R.id.acph_h_tfrby_av_value_tv);
        findViewById(R.id.test_table_3_header_l_ll).setVisibility(View.GONE);
        findViewById(R.id.test_table_3_header_2_ll).setVisibility(View.VISIBLE);
        findViewById(R.id.test_interference).setVisibility(View.GONE);
        findViewById(R.id.acph_h_final_calc_ll).setVisibility(View.VISIBLE);
        TextView TestHeader = (TextView)findViewById(R.id.common_header_tv);
        TestHeader.setText("TEST RAW DATA (ACPH_H)\n(Air Flow Velocity, Volume Testing and Determination of Air Changes per Hour Rates)");
    }
}
