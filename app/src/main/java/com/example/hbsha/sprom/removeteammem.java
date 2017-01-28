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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.hbsha.sprom.LoginActivity.userType;

public class removeteammem extends AppCompatActivity {
    String message,up1,up2,up3;
    private ArrayList<String> memberdetails;

    private DBMgr DBInstance = DBMgr.GetInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removeteammem);


        Bundle extr = getIntent().getExtras();
        message = extr.getString("msg1");
        TextView anstex=(TextView) findViewById(R.id.editText14);
        anstex.setText(message);
        memberdetails = DBInstance.getMemberdetails(message);
        String id =memberdetails.get(0);
        String ro =memberdetails.get(1);
        EditText ans=(EditText) findViewById(R.id.editText15);
        ans.setText(id);
        EditText tex=(EditText) findViewById(R.id.editText16);
        tex.setText(ro);
        if(userType.equals("TM"))
        {
            Button addtsk=(Button)findViewById(R.id.button4);
            addtsk.setVisibility(View.INVISIBLE);
            Button addtsk1=(Button)findViewById(R.id.button5);
            addtsk1.setVisibility(View.INVISIBLE);
            Button addtsk2=(Button)findViewById(R.id.button6);
            addtsk2.setVisibility(View.INVISIBLE);
        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuinflater=getMenuInflater();
        menuinflater.inflate(R.menu.activity_main_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void updateteammem (View view)
    {
        TextView upname=(TextView) findViewById(R.id.editText14);
        EditText updesc=(EditText)findViewById(R.id.editText15);
        EditText update=(EditText)findViewById(R.id.editText16);
        up1 = upname.getText().toString();
        up2 = updesc.getText().toString();
        up3 = update.getText().toString();

        boolean modifystatus = DBInstance.updateTeammember(up1,up2,up3);
        if(modifystatus)
            Toast.makeText(this,"Team member updated succesfully",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Unable to update team member",Toast.LENGTH_SHORT).show();



    }
    public void removeteammem (View view)
    {
        TextView upname=(TextView) findViewById(R.id.editText14);

        up1 = upname.getText().toString();

        boolean modifystatus = DBInstance.removeTeammember(up1);
        if(modifystatus)
            Toast.makeText(this,"Team member removed succesfully",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Unable to remove team member",Toast.LENGTH_SHORT).show();

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
