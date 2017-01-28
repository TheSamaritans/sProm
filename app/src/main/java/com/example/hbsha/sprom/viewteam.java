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
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.hbsha.sprom.LoginActivity.userType;

public class viewteam extends AppCompatActivity {
    private ArrayList<String> teams;
    private ListView teamlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewteam);


        DBMgr DBInstance = DBMgr.GetInstance();
        teams = DBInstance.getTeams();
        teamlist = (ListView) findViewById(R.id.teamlist);
        if(teams!=null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams);
            teamlist.setAdapter(adapter);
            teamlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent viewtm = new Intent(getApplicationContext(), viewTeammem.class);

                    String teamname = (String) adapterView.getItemAtPosition(i);

                    viewtm.putExtra("msg1", teamname);


                    startActivity(viewtm);
                    if (userType.equals("TM")) {
                        Button addtsk = (Button) findViewById(R.id.addTeam);
                        addtsk.setVisibility(View.INVISIBLE);
                    }


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
    public void addteam(View view)
    {
        Intent crtpro = new Intent(getApplicationContext(), createTeam.class);
        startActivity(crtpro);

    }
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
