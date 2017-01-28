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

public class CreateTask extends AppCompatActivity {

    private ViewBackLogController BackLogController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        BackLogController = new ViewBackLogController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuinflater=getMenuInflater();
        menuinflater.inflate(R.menu.activity_main_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickSubmit(View v) {

        EditText ProjectNameEdit = (EditText)findViewById(R.id.EDTPROJNAME);
        String ProjectName = ProjectNameEdit.getText().toString();

        EditText TaskNameEdit = (EditText)findViewById(R.id.EDTTMNAME);
        String TaskName = TaskNameEdit.getText().toString();

        EditText TaskDesEdit = (EditText)findViewById(R.id.EDTMEETDES);
        String TaskDesc = TaskDesEdit.getText().toString();

        EditText TaskWeitageEdit = (EditText)findViewById(R.id.EDTWEIGHTAGE);
        String TaskWeightage = TaskWeitageEdit.getText().toString();

        boolean InsertStatus = BackLogController.AddTaskToDatabase(ProjectName,TaskName,TaskDesc,TaskWeightage);

        CharSequence text;
        if(InsertStatus) {
            text = "Task Added to Database";
        }
        else{
            text = "Unable to add Task to DataBase";
        }
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
        Intent Intent = new Intent(getApplicationContext(), ViewBackLogScreen.class);
        startActivity(Intent );

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
