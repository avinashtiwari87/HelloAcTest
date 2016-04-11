package com.project.valdoc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.controler.ValdocControler;
import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.PartnerUser;
import com.project.valdoc.intity.User;
import com.project.valdoc.task.HttpConnection;
import com.project.valdoc.task.HttpPostConnection;
import com.project.valdoc.task.UserLoginTask;
import com.project.valdoc.utility.Utilityies;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements HttpConnection.HttpUrlConnectionResponce, ValdocControler.ControlerResponse, HttpPostConnection.HttpUrlConnectionPostResponce {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mLoginTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(LoginActivity.this);
    private String loginUserName = "";
    private String loginUserType = "";
    private int userPartnerId;
    private int userId;
    SharedPreferences sharedpreferences;
    ValdocControler mValdocControler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mValdocControler = new ValdocControler();
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                Log.d("valdoc", "login :" + "password editor action click");
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    Log.d("valdoc", "login :" + "password editor action");
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });

        ImageView mEmailSignInButton = (ImageView) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("valdoc", "login :" + "button click action");
                attemptLogin();

            }
        });


        findViewById(R.id.sync_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInternetConenction()) {
                    mValdocControler.getHttpConectionforSync(LoginActivity.this, "GET");
                    mValdocControler.httpPostSyncData(LoginActivity.this, "POST");
                } else {
                    aleartDialog("Please check your internate connection !");
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(LoginActivity.this, mActionBar, "");

        // Sending side
//        byte[] data = text.getBytes("UTF-8");
//        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

// Receiving side
//        try {
//            byte[] data = Base64.decode("UmFqZWV2MTIz", Base64.DEFAULT);
//            String text = new String(data, "UTF-8");
//            Log.d("Avinash","decrypted password"+text);
//        }catch(Exception e){
//
//        }
//create db and insert details
//        insertDataInTable();
    }


    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
       // Removed previous code. Because it is crashing. If device supports only wiFi, no 3G or 2G.
        //in that case it will give NPE
        // for method connec.getNetworkInfo(0).getState().
        final NetworkInfo activeNetwork = connec.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // notify user you are online
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else {
            // notify user you are not online
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mLoginTask != null) {
            return;
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
//        else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            mLoginTask = new UserLoginTask(this,email, password);
//            mLoginTask.execute();//        execute(LoginActivity.this);
            if (login(email, password)) {
                showProgress(false);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("login", true);
                editor.putString("USERNAME", loginUserName);
                editor.putString("USERTYPE", loginUserType);
                editor.putInt("APPUSERID", userId);
                editor.putInt("PARTNERID", userPartnerId);
                editor.putString("CLIENTORG", "M/s Kem Well Biopharma Private Limited");
                if (loginUserType.equalsIgnoreCase("PARTNER")) {
                    String pName=mValdocDatabaseHandler.getPartnerNameInfo(userPartnerId);
                    editor.putString("PARTNERORG", pName);
                }
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, AfterLoginActivity.class);
                intent.putExtra("USERNAME", loginUserName);
                intent.putExtra("USERTYPE", loginUserType);
                intent.putExtra("APPUSERID", userId);
                intent.putExtra("PARTNERID", userPartnerId);
                startActivity(intent);
                finish();
            } else {
                showProgress(false);
//               Toast toast=Toast.makeText(this,"Invalid user details",Toast.LENGTH_LONG);
//               toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
//               toast.show();
                aleartDialog("Invalid user details,Please sync the app to update the user details");
            }
        }
    }

    public void aleartDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
//                finish();
            }
        });

//        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean login(String name, String password) {
        boolean login = false;
        loginUserName = "";
        loginUserType = "";
        for (User user : mValdocDatabaseHandler.getUserInfo()) {
            Log.d("valdoc", "Login method :" + user.getEmail() + "\n" + user.getPassword());
            if (user.getEmail().equals(name) && user.getPassword().equals(password)) {
                Log.d("valdoc", "Login method inside if :" + user.getName() + "\n" + user.getPassword());
                login = true;
                loginUserName = user.getName();
                loginUserType = user.getType();
                userId = user.getId();
                userPartnerId = user.getPartnerId();
                break;
            }
        }
        return login;
    }

    private void insertDataInTable() {
        mValdocDatabaseHandler.insertUser(ValdocDatabaseHandler.USER_TABLE_NAME, createUserData());
        mValdocDatabaseHandler.insertPartnerUser(ValdocDatabaseHandler.PARTNERUSER_TABLE_NAME, createPartnerUserData());
//        if (tableExist(ValdocDatabaseHandler.USER_TABLE_NAME,valdocDatabaseHandler)==0) {
//
//        }
        for (User user : mValdocDatabaseHandler.getUserInfo())
            Log.d("valdoc", "Login :" + "user details" + user.getId() + "\n" + user.getName() + "\n" + user.getPassword() + "\n" + user.getType());
    }

    private List<User> createUserData() {
        ArrayList<User> userArrayList = new ArrayList<User>();
        User user = new User();
        user.setId(1);
        user.setName("Avinash");
        user.setType("CLIENT");
        user.setPassword("avi123");
        userArrayList.add(user);
        // 2nd user
        User user1 = new User();
        user1.setId(2);
        user1.setName("rajeev");
        user1.setType("VANDER");
        user1.setPassword("raj123");
        userArrayList.add(user1);
        return userArrayList;

    }

    //create partner user table
    private List<PartnerUser> createPartnerUserData() {
        ArrayList<PartnerUser> partnerUserArrayList = new ArrayList<PartnerUser>();
        PartnerUser partnerUser = new PartnerUser();
        partnerUser.setPartner_user_id(1);
        partnerUser.setApp_user_id(1);
        partnerUser.setPartnerId(1);
        partnerUserArrayList.add(partnerUser);
//        2nd data
        PartnerUser partnerUser1 = new PartnerUser();
        partnerUser1.setPartner_user_id(2);
        partnerUser1.setApp_user_id(2);
        partnerUser1.setPartnerId(2);
        partnerUserArrayList.add(partnerUser1);
        return partnerUserArrayList;
    }

    private int tableExist(String tableName, ValdocDatabaseHandler valdocDatabaseHandler) {
        SQLiteDatabase sqLiteDatabase = valdocDatabaseHandler.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM " + ValdocDatabaseHandler.DATABASE_NAME
                + " WHERE type='table' AND name=" + ValdocDatabaseHandler.USER_TABLE_NAME, null);
        return cursor.getCount();
    }

    @Override
    public void httpResponceResult(String resultData, int statusCode) {
        if (statusCode == HttpURLConnection.HTTP_OK) {
            Log.d("VALDOC", "response data=" + resultData);
//            generateNoteOnSD(AfterLoginActivity.this,resultData);
            mValdocControler.getAllDb(statusCode, resultData);
        }
    }

    @Override
    public void controlerResult(HashMap<String, ArrayList> arrayListHashMap, String message) {
        final String messageText = message;
        Log.d("VALDOC", "controler response data  message=" + message);
        final boolean isertFlag = insertDataInTable(arrayListHashMap);
        Log.d("VALDOC", "controler response data  isertFlag=" + isertFlag);
        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isertFlag) {
                    aleartDialog(messageText);
                } else {
                    aleartDialog("Table is not created successfully,Please sync again !");
                }
            }
        });
    }

    private boolean insertDataInTable(HashMap<String, ArrayList> arrayListHashMap) {
        boolean isertFlag = true;
        Log.d("VALDOC", "controler response data  insert table hasmap size=" + arrayListHashMap.size());
        ArrayList userArrayList = null;
        try {
            userArrayList = (ArrayList) arrayListHashMap.get(ValdocDatabaseHandler.USER_TABLE_NAME);
            Log.d("VALDOC", "controler response data  userArrayList.size()=" + userArrayList.size());
            if (null != userArrayList || userArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  1");
                isertFlag = mValdocDatabaseHandler.insertUser(ValdocDatabaseHandler.USER_TABLE_NAME, userArrayList);
            }
        } catch (Exception e) {

        }
        try {
            ArrayList ahusArrayList = arrayListHashMap.get(ValdocDatabaseHandler.AHU_TABLE_NAME);
            if (null != ahusArrayList || ahusArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  2");
                isertFlag = mValdocDatabaseHandler.insertAhu(ValdocDatabaseHandler.AHU_TABLE_NAME, ahusArrayList);
            }
        } catch (Exception e) {

        }
//        ArrayList ahusArrayList = arrayListHashMap.get("ahus");
//        if (null != ahusArrayList || ahusArrayList.size() > 0)
//            isertFlag=mValdocDatabaseHandler.insertPartnerUser(ValdocDatabaseHandler.PARTNERUSER_TABLE_NAME, createPartnerUserData());
        try {

            ArrayList equipmentsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.EQUIPMENT_TABLE_NAME);
            if (null != equipmentsArrayList || equipmentsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  3");
                isertFlag = mValdocDatabaseHandler.insertEquipment(ValdocDatabaseHandler.EQUIPMENT_TABLE_NAME, equipmentsArrayList);
            }
        } catch (Exception e) {

        }
        try {
            ArrayList roomsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.ROOM_TABLE_NAME);
            if (null != roomsArrayList || roomsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  4");
                isertFlag = mValdocDatabaseHandler.insertRoom(ValdocDatabaseHandler.ROOM_TABLE_NAME, roomsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList applicableTestRoomsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.APLICABLE_TEST_ROOM_TABLE_NAME);
            if (null != applicableTestRoomsArrayList || applicableTestRoomsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  5");
                isertFlag = mValdocDatabaseHandler.insertApplicableTestRoom(ValdocDatabaseHandler.APLICABLE_TEST_ROOM_TABLE_NAME, applicableTestRoomsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList applicableTestEquipmentsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_TABLE_NAME);
            if (null != applicableTestEquipmentsArrayList || applicableTestEquipmentsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  6 hello");
                isertFlag = mValdocDatabaseHandler.insertApplicableTestEquipment(ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_TABLE_NAME, applicableTestEquipmentsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList clientInstrumentsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TABLE_NAME);
            if (null != clientInstrumentsArrayList || clientInstrumentsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  7");
                isertFlag = mValdocDatabaseHandler.insertClientInstrument(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TABLE_NAME, clientInstrumentsArrayList);
            }
        } catch (Exception e) {

        }
        try {
            ArrayList partnerInstrumentsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TABLE_NAME);
            if (null != partnerInstrumentsArrayList || partnerInstrumentsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  8");
                isertFlag = mValdocDatabaseHandler.insertPartnerInstrument(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TABLE_NAME, partnerInstrumentsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList testMasterArrayList = arrayListHashMap.get(ValdocDatabaseHandler.TESTMASTER_TABLE_NAME);
            if (null != testMasterArrayList || testMasterArrayList.size() > 0)
                isertFlag = mValdocDatabaseHandler.insertTestMaster(ValdocDatabaseHandler.TESTMASTER_TABLE_NAME, testMasterArrayList);
        } catch (Exception e) {

        }
        try {
            ArrayList areasArrayList = arrayListHashMap.get(ValdocDatabaseHandler.AREA_TABLE_NAME);
            if (null != areasArrayList || areasArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  9");
                isertFlag = mValdocDatabaseHandler.insertTestArea(ValdocDatabaseHandler.AREA_TABLE_NAME, areasArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList equipmentFiltersArrayList = arrayListHashMap.get(ValdocDatabaseHandler.EQUIPMENTFILTER_TABLE_NAME);
            if (null != equipmentFiltersArrayList || equipmentFiltersArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  10");
                isertFlag = mValdocDatabaseHandler.insertEquipmentfilter(ValdocDatabaseHandler.EQUIPMENTFILTER_TABLE_NAME, equipmentFiltersArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList grillsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.GRILL_TABLE_NAME);
            if (null != grillsArrayList || grillsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  11");
                isertFlag = mValdocDatabaseHandler.insertGrill(ValdocDatabaseHandler.GRILL_TABLE_NAME, grillsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList roomFiltersArrayList = arrayListHashMap.get(ValdocDatabaseHandler.ROOMFILTER_TABLE_NAME);
            if (null != roomFiltersArrayList || roomFiltersArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  12");
                isertFlag = mValdocDatabaseHandler.insertRoomFilter(ValdocDatabaseHandler.ROOMFILTER_TABLE_NAME, roomFiltersArrayList);
            }
        } catch (Exception e) {

        }
        try {
            ArrayList partnerArrayList = arrayListHashMap.get(ValdocDatabaseHandler.PARTNERS_TABLE_NAME);
            Log.d("VALDOC", "controler response data  13=partnerArrayList=" + partnerArrayList.size());
            if (null != partnerArrayList || partnerArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  13=partnerInstrumentArrayList=" + partnerArrayList.size());
                isertFlag = mValdocDatabaseHandler.insertPartners(ValdocDatabaseHandler.PARTNERS_TABLE_NAME, partnerArrayList);
            }
        } catch (Exception e) {

        }
        return isertFlag;
    }

    @Override
    public void httpPostResponceResult(String resultData, int statusCode) {
        final int statuscode = statusCode;
        Log.d("VALDOC", "controler httpPostResponceResult response data1  statusCode=" + statusCode);
        Log.d("VALDOC", "controler httpPostResponceResult response data1  resultData=" + resultData);
        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (statuscode == HttpURLConnection.HTTP_OK) {
                    aleartDialog("Data synked successfully");
                } else {
                    aleartDialog("Post Data not syncked successfully,Please sync again !");
                }
            }
        });
    }
}