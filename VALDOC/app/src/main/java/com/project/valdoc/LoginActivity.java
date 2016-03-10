package com.project.valdoc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.PartnerUser;
import com.project.valdoc.intity.User;
import com.project.valdoc.task.UserLoginTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

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
    private String loginUserName="";
    private String loginUserType="";
    private int userId;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("valdoc", "login :" + "button click action");
                attemptLogin();

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
//create db and insert details
        insertDataInTable();
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
           if( login(email,password)){
               showProgress(false);
               SharedPreferences.Editor editor = sharedpreferences.edit();
               editor.putBoolean("login", true);
               editor.putString("USERNAME", loginUserName);
               editor.putString("USERTYPE", loginUserType);
               editor.putInt("APPUSERID", userId);
               editor.commit();

               Intent intent=new Intent(LoginActivity.this,AfterLoginActivity.class);
               intent.putExtra("USERNAME",loginUserName);
               intent.putExtra("USERTYPE",loginUserType);
               intent.putExtra("APPUSERID",userId);
               startActivity(intent);
               finish();
           }else{
               showProgress(false);
               Toast toast=Toast.makeText(this,"Invalid user details",Toast.LENGTH_LONG);
               toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
               toast.show();
           }
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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
        loginUserName="";
        loginUserType="";
        for (User user : mValdocDatabaseHandler.getUserInfo()) {
            Log.d("valdoc","Login method :"+user.getName()+"\n"+user.getPassword());
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                Log.d("valdoc","Login method inside if :"+user.getName()+"\n"+user.getPassword());
                login = true;
                loginUserName=user.getName();
                loginUserType=user.getType();
                userId=user.getId();
                break;
            }
        }
        return login;
    }

    private void insertDataInTable() {

        mValdocDatabaseHandler.insertUser(ValdocDatabaseHandler.USER_TABLE_NAME, createUserData());
        mValdocDatabaseHandler.insertPartnerUser(ValdocDatabaseHandler.PARTNERUSER_TABLE_NAME, createPartnerUserData());
//        if (tableExist(ValdocDatabaseHandler.USER_TABLE_NAME,valdocDatabaseHandler)==0) {

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
    private List<PartnerUser> createPartnerUserData(){
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

}