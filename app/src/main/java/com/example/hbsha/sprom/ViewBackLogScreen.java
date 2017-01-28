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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Arrays;

import static com.example.hbsha.sprom.LoginActivity.userType;
import static java.lang.Class.*;

public class ViewBackLogScreen extends AppCompatActivity {


    private ArrayList<String> TaskList;
    private ArrayAdapter<String> TaskAdapter;
    public final static String EXTRA_MESSAGE = "com.example.hbsha.sprom";


    ViewBackLogScreen()
    {
        TaskList = new ArrayList<String>();
    }
    protected void onCreate(Bundle savedInstanceState) {
        TaskAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,TaskList);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_back_log_screen);
        ViewBacklogTask();
        if(userType.equals("TM"))
        {
            Button addtsk=(Button)findViewById(R.id.BTNADDTASK);
            addtsk.setVisibility(View.INVISIBLE);
        }
        ListView BacklogListView = (ListView)findViewById(R.id.LSTVEWBACKLOG);
        BacklogListView.setAdapter(TaskAdapter);
        BacklogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   @Override
                                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                       Context context = getApplicationContext();
                                                        String TaskName = (String)parent.getItemAtPosition(position);
                                                       Intent ModifyTaskIntent = new Intent(context,ViewAndModifyTask.class);
                                                       ModifyTaskIntent.putExtra(EXTRA_MESSAGE,TaskName);
                                                       startActivity(ModifyTaskIntent);

                                                   }
                                               }
        );
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

    private void ViewBacklogTask(){
     //   TaskList.add()
        ViewBackLogController BackLogController = new ViewBackLogController();
        ArrayList<String> StringTaskList = BackLogController.GetBackLogTask();

        for (String Task : StringTaskList) {
            TaskList.add(Task);
        }
    }

    public void onClickBackLogAddTask(View v) {

        Intent Intent = new Intent(this, CreateTask.class);
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
                    Intent clientHomeScreen = new Intent(this,MemberHomeScreen.class);
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
