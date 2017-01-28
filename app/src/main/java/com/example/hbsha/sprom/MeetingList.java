package com.example.hbsha.sprom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by mahesh on 11/18/2016.
 */

public class MeetingList extends AppCompatActivity {
    final static String MESSAGE = "Message_MeetingList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetingreqlist);

        ListView meetReqList = (ListView) findViewById(R.id.meetingList);
        DBMgr DBInstance= DBMgr.GetInstance();
        ArrayList<String> meetList =DBInstance.getMeetingList();
        if(meetList!=null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meetList);
            meetReqList.setAdapter(adapter);

            meetReqList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent meetReqCreate = new Intent(getApplicationContext(), MeetingReq.class);
                    String meetDes = (String) adapterView.getItemAtPosition(i);
                    meetReqCreate.putExtra(MESSAGE, meetDes);
                    startActivity(meetReqCreate);
                }
            });
        }
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuinflater=getMenuInflater();
        menuinflater.inflate(R.menu.activity_main_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.hme:
                if (LoginActivity.userType.equals("C")) {
                    Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
                    startActivity(clientHomeScreen);
                } else if (LoginActivity.userType.equals("TM")) {
                    Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
                    startActivity(clientHomeScreen);
                } else if (LoginActivity.userType.equals("TL")) {
                    Intent leadScreen = new Intent(this, LeadHomeScreen.class);
                    startActivity(leadScreen);
                }

                return true;
            case R.id.logout:
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Are you sure you want to logout?");
                dlgAlert.setTitle("Confirmation");
                dlgAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getBaseContext(),LoginActivity.class));
                    }
                });
                dlgAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                //startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return false;
        }
    }

    public void meetingCreateReq(View view){
        Intent meetReqCreate = new Intent(getApplicationContext(),MeetingReq.class);
        meetReqCreate.putExtra(MESSAGE,"new");
        startActivity(meetReqCreate);
    }

    @Override
    public void onBackPressed() {
        if (LoginActivity.userType.equals("C")) {
            Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
            startActivity(clientHomeScreen);
        } else if (LoginActivity.userType.equals("TM")) {
            Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
            startActivity(clientHomeScreen);
        } else if (LoginActivity.userType.equals("TL")) {
            Intent leadScreen = new Intent(this, LeadHomeScreen.class);
            startActivity(leadScreen);
        }
    }
}

