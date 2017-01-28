package com.example.hbsha.sprom;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    static  JSONObject validateJson = new JSONObject();

    public static JSONObject userInfo;
    public static String userType;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void onClickLogin(View v) throws JSONException {

        EditText emailId = (EditText) findViewById(R.id.email);
        String emailId_value = emailId.getText().toString();
        EditText pwd = (EditText) findViewById(R.id.password);
        String pwd_value = pwd.getText().toString();

        validateJson.put("mail",emailId_value);
        validateJson.put("pwd",pwd_value);

        DBMgr DBInstance = DBMgr.GetInstance();
        userInfo = DBInstance.validateUser(validateJson);

        if(userInfo!=null && userInfo.getString("userflag").equals("C")){
            Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
            startActivity(clientHomeScreen);
            userType=userInfo.getString("userflag");
        }else if(userInfo!=null && userInfo.getString("userflag").equals("TL")){
            Intent leadScreen = new Intent(this, LeadHomeScreen.class);
            startActivity(leadScreen);
            userType=userInfo.getString("userflag");
        }else if(userInfo!=null && userInfo.getString("userflag").equals("TM")){
            Intent MemberHomeScreen = new Intent(this, MemberHomeScreen.class);
            startActivity(MemberHomeScreen);
            userType=userInfo.getString("userflag");
        }
        else {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Incorrect username or password!");
            dlgAlert.setTitle("Invalid Credentials");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
    }

    public void onClickRegister(View v){
        Intent RegisterActivity = new Intent(this, RegisterActivity.class);
        startActivity(RegisterActivity);
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

