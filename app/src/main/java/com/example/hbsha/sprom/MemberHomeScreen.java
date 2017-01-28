package com.example.hbsha.sprom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;

import static com.example.hbsha.sprom.LoginActivity.userInfo;

public class MemberHomeScreen extends AppCompatActivity {
    public final static String EXTRA = "com.example.hbsha.sprom";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_member_home_screen);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuinflater = getMenuInflater();
            menuinflater.inflate(R.menu.activity_main_actions, menu);
            return super.onCreateOptionsMenu(menu);
        }

    public void onClickViewBackLog(View v) {

        Intent BackLogIntent = new Intent(this, ViewBackLogScreen.class);
        startActivity(BackLogIntent);
    }

    public void onClickViewkbBoard(View v) {

        Intent SSIntent = new Intent(this, SelectSprint.class);

        String message = "Project1";
        SSIntent.putExtra(EXTRA, message);
        startActivity(SSIntent);

    }

    public void onClickClientAddTask(View v) {

        Intent TaskIntent = new Intent(this, CreateTask.class);
        startActivity(TaskIntent);
    }

    public void onClickViewproject(View v) {
        Intent projectIntent = new Intent(this, viewpro.class);
        startActivity(projectIntent);

    }

    public void CreateProject(View v) {
        Intent addmodproIntent = new Intent(this, createProject.class);
        startActivity(addmodproIntent);

    }

    public void onClickCreateTeam(View view) {
        Intent crtproIntent = new Intent(this, viewTeammem.class);
        try {
            crtproIntent.putExtra("msg1",userInfo.getString("teamname"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(crtproIntent);
    }


    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.hme:
                if (LoginActivity.userType.equals("C")) {
                    Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
                    startActivity(clientHomeScreen);
                } else if (LoginActivity.userType.equals("TM")) {
                    Intent leadHomeScreen = new Intent(this, MemberHomeScreen.class);
                    startActivity(leadHomeScreen);
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


}
