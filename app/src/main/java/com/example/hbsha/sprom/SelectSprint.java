package com.example.hbsha.sprom;
/**
 * Created by Bhuvie.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import static com.example.hbsha.sprom.LoginActivity.userInfo;

public class SelectSprint extends AppCompatActivity {
        TextView txtproj;
        Spinner sssprintlist;
        DBMgr db=DBMgr.GetInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sprint);
        Intent intent = getIntent();
        String projname = intent.getStringExtra(ClientHomeScreen.EXTRA);
        txtproj=(TextView)findViewById(R.id.txt_ssproj);
        sssprintlist=(Spinner)findViewById(R.id.spinner_sssprint);
        try {
            txtproj.setText("Your Project: "+userInfo.getString("projectname"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> sprlist=db.getSprintList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,sprlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sssprintlist.setAdapter(dataAdapter);


    }

    @Override
    public void onBackPressed() {
        if (LoginActivity.userType.equals("C")) {
            Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
            startActivity(clientHomeScreen);
        } else if (LoginActivity.userType.equals("TM")) {
            Intent clientHomeScreen = new Intent(this,MemberHomeScreen.class);
            startActivity(clientHomeScreen);
        } else if (LoginActivity.userType.equals("TL")) {
            Intent leadScreen = new Intent(this, LeadHomeScreen.class);
            startActivity(leadScreen);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuinflater=getMenuInflater();
        menuinflater.inflate(R.menu.activity_main_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickSSViewKB(View v) {

        Bundle bundle = new Bundle();
        bundle.putString("sprintid", sssprintlist.getSelectedItem().toString());
//        todoFragment fragobj1 = new todoFragment();
//        fragobj1.setArguments(bundle);
//        inprogressFragment fragobj2 = new inprogressFragment();
//        fragobj2.setArguments(bundle);
//        doneFragment fragobj3 = new doneFragment();
//        fragobj3.setArguments(bundle);
        viewkbBoard viewkbobj = new viewkbBoard();
        viewkbobj.setArguments(bundle);
        RelativeLayout rl=(RelativeLayout) findViewById(R.id.activity_select_sprint);
        rl.setVisibility(View.GONE);
        FragmentManager mFragmentManager;
        FragmentTransaction mFragmentTransaction;
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, viewkbobj).commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.hme:
                if (LoginActivity.userType.equals("C")) {
                    Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
                    startActivity(clientHomeScreen);
                } else if (LoginActivity.userType.equals("TM")) {
                    Intent clientHomeScreen = new Intent(this, MemberHomeScreen.class);
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
}
