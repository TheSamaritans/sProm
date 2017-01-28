package com.example.hbsha.sprom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class createProject extends AppCompatActivity {

    private DBMgr DBInstance = DBMgr.GetInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
    }

         public void insertProject (View view)
         {
             EditText pronameCreate = (EditText)findViewById(R.id.editText2);
             String ProjectName = pronameCreate.getText().toString();

             EditText projectdescCreate = (EditText)findViewById(R.id.editText3);
             String descriptionName = projectdescCreate.getText().toString();

             EditText prodateCreate = (EditText)findViewById(R.id.editText4);
             String pdate = prodateCreate.getText().toString();

             EditText teamnameCreate = (EditText)findViewById(R.id.editText5);
             String teamName = teamnameCreate.getText().toString();



             boolean insertstatus = DBInstance.AddProjectToDatabase(ProjectName,descriptionName,pdate,teamName);
             if(insertstatus)
                 Toast.makeText(this,"Project added succesfully",Toast.LENGTH_SHORT).show();
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

             else
                 Toast.makeText(this,"Unable to add project",Toast.LENGTH_SHORT).show();

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
                        //createProject.this.finish();
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
