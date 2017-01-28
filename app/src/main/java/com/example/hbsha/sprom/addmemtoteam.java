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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addmemtoteam extends AppCompatActivity {
    private DBMgr DBInstance = DBMgr.GetInstance();
    String teamName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmemtoteam);

        Bundle extr = getIntent().getExtras();
        teamName = extr.getString("teamname");
        TextView anstex=(TextView) findViewById(R.id.editText8);
        anstex.setText(teamName);

    }



    public void addmem (View view)
    {


        EditText txtempname = (EditText)findViewById(R.id.editText11);
        String employeeName = txtempname.getText().toString();

        EditText txtempid = (EditText)findViewById(R.id.editText12);
        String employeeId = txtempid.getText().toString();

        EditText txtemprole = (EditText)findViewById(R.id.editText13);
        String employeeRole = txtemprole.getText().toString();



        boolean createstatus = DBInstance.AddmemtoTeam(teamName,employeeName,employeeId,employeeRole);
        if(createstatus) {
            Toast.makeText(this, "Member added succesfully", Toast.LENGTH_SHORT).show();
            Intent viewteam = new Intent(this, viewteam.class);
            startActivity(viewteam);
        }
        else
            Toast.makeText(this,"Unable to create Team",Toast.LENGTH_SHORT).show();

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
