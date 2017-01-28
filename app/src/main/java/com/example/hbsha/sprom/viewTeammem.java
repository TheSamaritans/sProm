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

public class viewTeammem extends AppCompatActivity {
    private ArrayList<String> teammems;
    private ListView teammemlist;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teammem);


        Bundle extr = getIntent().getExtras();
        message = extr.getString("msg1");
        DBMgr DBInstance = DBMgr.GetInstance();
        teammems = DBInstance.getTeammems(message);
        teammemlist = (ListView) findViewById(R.id.teamembers);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teammems);
        teammemlist.setAdapter(adapter1);

        if(userType.equals("TM"))
        {
            Button addmem=(Button)findViewById(R.id.addmem);
            addmem.setVisibility(View.INVISIBLE);
        }


        teammemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent remointent = new Intent(getApplicationContext(), removeteammem.class);
                String memname = (String) adapterView.getItemAtPosition(i);
                remointent.putExtra("msg1", memname);



                startActivity(remointent);

            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuinflater=getMenuInflater();
        menuinflater.inflate(R.menu.activity_main_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void addmemtoTeam (View view)
    {
        Intent addmemintent = new Intent(this, addmemtoteam.class);
        addmemintent.putExtra("teamname", message);

        startActivity(addmemintent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.hme:
                if (userType.equals("C")) {
                    Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
                    startActivity(clientHomeScreen);
                } else if (userType.equals("TM")) {
                    Intent clientHomeScreen = new Intent(this, MemberHomeScreen.class);
                    startActivity(clientHomeScreen);
                } else if (userType.equals("TL")) {
                    Intent clientHomeScreen = new Intent(this, LeadHomeScreen.class);
                    startActivity(clientHomeScreen);
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
