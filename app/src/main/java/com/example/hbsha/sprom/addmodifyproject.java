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

import static com.example.hbsha.sprom.LoginActivity.userType;

public class addmodifyproject extends AppCompatActivity {
    private DBMgr DBInstance = DBMgr.GetInstance();

    String message,m2,m3,m4,up1,up2,up3,up4,r1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmodifyproject);
        Bundle extr = getIntent().getExtras();
        message = extr.getString("msg1");
        m2 = extr.getString("msg2");
        m3 = extr.getString("msg3");
        m4 = extr.getString("msg4");
        TextView anstex=(TextView) findViewById(R.id.updatetxtproname);
        anstex.setText(message);
        EditText ans=(EditText)findViewById(R.id.updatetxtteammem);
        ans.setText(m2);
        EditText at=(EditText)findViewById(R.id.updatetxtdesc);
        at.setText(m3);
        EditText anst=(EditText)findViewById(R.id.updatetxtduedate);
        anst.setText(m4);
        if(userType.equals("TM"))
        {
            Button addtsk=(Button)findViewById(R.id.updateprobutton);
            addtsk.setVisibility(View.INVISIBLE);
            Button addtsk1=(Button)findViewById(R.id.updateproremobutton);
            addtsk1.setVisibility(View.INVISIBLE);
            Button addtsk2=(Button)findViewById(R.id.button2);
            addtsk2.setVisibility(View.INVISIBLE);
        }

    }
    public void updateprojects(View view)
    {
        TextView upname=(TextView) findViewById(R.id.updatetxtproname);
        EditText updesc=(EditText)findViewById(R.id.updatetxtteammem);
        EditText update=(EditText)findViewById(R.id.updatetxtdesc);
        EditText upteam=(EditText)findViewById(R.id.updatetxtduedate);
        up1 = upname.getText().toString();
        up2 = updesc.getText().toString();
        up3 = update.getText().toString();
        up4 = upteam.getText().toString();

        boolean modifystatus = DBInstance.modifyProjects(up1,up2,up3,up4);
        if(modifystatus) {
            Toast.makeText(this, "Project modified succesfully", Toast.LENGTH_SHORT).show();
            Intent viewpro = new Intent(this, viewpro.class);
            startActivity(viewpro);
        }
        else
            Toast.makeText(this,"Unable to modify project",Toast.LENGTH_SHORT).show();


    }
    public void removeproject (View view)
    {
        EditText rname=(EditText)findViewById(R.id.updatetxtproname);

        r1 = rname.getText().toString();

        boolean removestatus = DBInstance.removeProjects(r1);
        if(removestatus) {
            Toast.makeText(this, "Project removed succesfully", Toast.LENGTH_SHORT).show();
            Intent viewpro = new Intent(this, viewpro.class);
            startActivity(viewpro);
        }
        else
            Toast.makeText(this,"Unable to remove project",Toast.LENGTH_SHORT).show();


    }
    public void goback(View view)
    {
        Intent back = new Intent(this, viewpro.class);
        startActivity(back);
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
