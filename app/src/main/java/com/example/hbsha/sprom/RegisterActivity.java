package com.example.hbsha.sprom;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    private GoogleApiClient client;
    Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);
        roleSpinner=(Spinner)findViewById(R.id.role_spinner);
        ArrayList<String> roleArrayList = new ArrayList<>();
        roleArrayList.add("Client");
        roleArrayList.add("Lead");
        roleArrayList.add("Member");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,roleArrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(dataAdapter);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        Intent LoginActivity = new Intent(this, com.example.hbsha.sprom.LoginActivity.class);
        startActivity(LoginActivity);
    }

    public void onClickRegister(View v) throws JSONException{
        JSONObject userDetails = new JSONObject();
        EditText userid = (EditText) findViewById(R.id.userID_text);
        userDetails.put("userID",userid.getText().toString());
        EditText empname = (EditText) findViewById(R.id.empname_text);
        userDetails.put("empname",empname.getText().toString());
        EditText empid = (EditText) findViewById(R.id.empid_text);
        userDetails.put("empid",empid.getText().toString());
        EditText email = (EditText) findViewById(R.id.email_text);
        userDetails.put("email",email.getText().toString());
        EditText password = (EditText) findViewById(R.id.pwd_text);
        userDetails.put("pwd",password.getText().toString());
        userDetails.put("role",roleSpinner.getSelectedItem().toString());

        DBMgr DBInstance = DBMgr.GetInstance();
        boolean reg_bool = DBInstance.registerUser(userDetails);
        if(reg_bool){
            Toast toast = Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_SHORT);
            toast.show();
            Intent LoginActivity = new Intent(this, com.example.hbsha.sprom.LoginActivity.class);
            startActivity(LoginActivity);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Unable to Regiester,Please try again!", Toast.LENGTH_SHORT);
            toast.show();
        }
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

