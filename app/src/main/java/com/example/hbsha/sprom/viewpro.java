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

import org.json.JSONException;

import java.util.ArrayList;

import static com.example.hbsha.sprom.DBMgr.prodet;

public class viewpro extends AppCompatActivity {
    private ArrayList<String> projects;
    private ListView mylist;
    final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpro);

        DBMgr DBInstance= DBMgr.GetInstance();
         projects=DBInstance.GetProjects();
        mylist = (ListView) findViewById(R.id.projectlist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,projects);
        mylist.setAdapter(adapter);


        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent addmodpro = new Intent(getApplicationContext(), addmodifyproject.class);

                String proname = (String) adapterView.getItemAtPosition(i);
                String d = null;
                try {
                    d = prodet.getString("descrip");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String da = null;
                try
                {
                    da = prodet.getString("prodate");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String t = null;
                try {
                    t = prodet.getString("teamnam");
                }
                catch (JSONException e1) {
                    e1.printStackTrace();
                }
                addmodpro.putExtra("msg1", proname);
                addmodpro.putExtra("msg2", d);
                addmodpro.putExtra("msg3", da);
                addmodpro.putExtra("msg4", t);
                startActivity(addmodpro);

            }
        });






        //TextView rl=(TextView) findViewById(R.id.textView4);
        //rl.setText(pro.toString());*/
    }












    public void onClickaddmodproject(View v)
    {
        Intent addmodproIntent = new Intent(this,addmodifyproject.class);
        startActivity(addmodproIntent);

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
