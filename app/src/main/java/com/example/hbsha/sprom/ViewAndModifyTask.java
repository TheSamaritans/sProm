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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.hbsha.sprom.LoginActivity.userType;

public class ViewAndModifyTask extends AppCompatActivity {

    private ViewBackLogController BackLogController;
    EditText EditProjectName;
    EditText EditTaskName;
    EditText EditTaskDesc;
    EditText EditTaskStatus;
    EditText EditTaskWeightage;
    EditText EditTaskSprint;
    String TaskName;
    String ProjectName;
    String TaskDesc;
    String TaskStatus;
    String TaskWeightage;
    String TaskSprint;
    Spinner sprintlist,empnamelist,statuslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBMgr dbmgr = new DBMgr();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_modify_task);
        if(userType.equals("TM"))
        {
            Button addtsk=(Button)findViewById(R.id.BTNASSIGN);
            addtsk.setVisibility(View.INVISIBLE);
            Button addtsk1=(Button)findViewById(R.id.updateproremobutton);
            addtsk1.setVisibility(View.INVISIBLE);
        }
        Intent TaskIntent = getIntent();
        TaskName = TaskIntent.getStringExtra(ViewBackLogScreen.EXTRA_MESSAGE);

        EditTaskName = (EditText)findViewById(R.id.updatetxtteammem);
        EditTaskName.setText(TaskName);

        EditProjectName = (EditText)findViewById(R.id.updatetxtproname);

        EditTaskDesc = (EditText)findViewById(R.id.updatetxttskname);

        EditTaskStatus = (EditText)findViewById(R.id.updatetxtduedate);

        EditTaskWeightage = (EditText)findViewById(R.id.updatetxtdesc);
        EditTaskName.setText(TaskName);

       /* EditTaskSprint = (EditText)findViewById(R.id.EDTTEXTMODIFYSCRSPRINTID);
        EditTaskName.setText(TaskName);*/

        BackLogController = new ViewBackLogController();
        ArrayList<String> TaskDetail = BackLogController.GetTskDetain(TaskName);
        ArrayList<String> empNames = BackLogController.getEmpNames();
        ArrayList<String> sprlist=dbmgr.getSprintList();

        sprintlist=(Spinner)findViewById(R.id.sprintId_spinner);
        empnamelist=(Spinner)findViewById(R.id.empname_spinner);

        if(!TaskDetail.isEmpty()){
            EditProjectName.setText(TaskDetail.get(3));
            EditTaskDesc.setText(TaskDetail.get(1));
            EditTaskWeightage.setText(TaskDetail.get(2));
           // EditTaskSprint.setText(TaskDetail.get(4));
            EditTaskStatus.setText(TaskDetail.get(5));

            //sprint id list drop down
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item,sprlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sprintlist.setAdapter(dataAdapter);

            //empname list drop down
            ArrayAdapter<String> dataAdapteremp = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item,empNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            empnamelist.setAdapter(dataAdapteremp);
        }


    }

    @Override
    public void onBackPressed() {
        Intent ViewBackLogScreen = new Intent(getApplicationContext(),ViewBackLogScreen.class);
        startActivity(ViewBackLogScreen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuinflater=getMenuInflater();
        menuinflater.inflate(R.menu.activity_main_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickUpdate(View v) {

        ProjectName = EditProjectName.getText().toString();
        TaskName = EditTaskName.getText().toString();
        TaskDesc = EditTaskDesc.getText().toString();
       //TaskSprint = EditTaskSprint.getText().toString();
        TaskStatus = EditTaskStatus.getText().toString();
        TaskWeightage = EditTaskWeightage.getText().toString();

        boolean UpdateStatus = BackLogController.UpdateTask(ProjectName,TaskName,TaskDesc,TaskWeightage,TaskStatus);

        CharSequence text;
        if (UpdateStatus){
            text = "Task Successfully Updated!!!";
        }
        else{
            text = "Unable to Update Task!!!";
        }
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent Intent = new Intent(this, ViewBackLogScreen.class);
        startActivity(Intent);
    }

    public void onClickRemove(View v) {
        // Need to add code to update database
        boolean DeleteStatus = BackLogController.DeleteTask(TaskName);
        CharSequence text = "Task Removed!!!";
        if(DeleteStatus) {
            text = "Task Successfully Removed!!!";
        }
        else {
            text = "Unable to Remove Task!!!";
        }

        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent Intent = new Intent(this, ViewBackLogScreen.class);
        startActivity(Intent );
    }

    public void onClickAssign(View v) {

        Boolean status = BackLogController.assignTask(sprintlist.getSelectedItem().toString(),empnamelist.getSelectedItem().toString(), EditTaskName.getText().toString());

        if(status){
            Toast toast = Toast.makeText(getApplicationContext(), "Task has been assigned!", Toast.LENGTH_SHORT);
            toast.show();
            Intent Intent = new Intent(getApplicationContext(), ViewBackLogScreen.class);
            startActivity(Intent );
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Unable to Assign Task!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.hme:
                if (LoginActivity.userType.equals("C")) {
                    Intent clientHomeScreen = new Intent(this, ClientHomeScreen.class);
                    startActivity(clientHomeScreen);
                } else if (LoginActivity.userType.equals("TM")) {
                    Intent MemberHomeScreen = new Intent(this, MemberHomeScreen.class);
                    startActivity(MemberHomeScreen);
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
