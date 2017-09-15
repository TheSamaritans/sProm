package com.example.hbsha.sprom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.net.Uri;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by mahesh on 11/17/2016.
 */

public class MeetingReq  extends AppCompatActivity{

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_meeting_req);

            Bundle extras = getIntent().getExtras();
            if(!extras.getString(MeetingList.MESSAGE).equals("new")){

            JSONObject jsonValue =new DBMgr().getMeetingReqValue(extras.getString(MeetingList.MESSAGE));

            EditText ProjectNameEdit = (EditText) findViewById(R.id.EDTPROJNAME);
            ProjectNameEdit.setText((String)LoginActivity.userInfo.get("projectname"));

            EditText TeamNameEdit = (EditText) findViewById(R.id.EDTTMNAME);
            TeamNameEdit.setText((String)LoginActivity.userInfo.get("teamname"));

            EditText MeetDesEdit = (EditText) findViewById(R.id.EDTMEETDES);
            MeetDesEdit.setText(extras.getString(new MeetingList().MESSAGE));

            EditText VenueEdit = (EditText) findViewById(R.id.EDTVENUE);
            VenueEdit.setText(jsonValue.getString("venue"));

            EditText TimeStampEdit = (EditText) findViewById(R.id.EDTTIME);
            TimeStampEdit.setText(jsonValue.getString("meettime"));
            }

            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent meetListCreate = new Intent(getApplicationContext(),MeetingList.class);
        startActivity(meetListCreate);
    }


      public void onClickSubmit(View v) {

        Context context = getApplicationContext();
        Toast toast;
        try {
            EditText ProjectNameEdit = (EditText) findViewById(R.id.EDTPROJNAME);
            EditText TeamNameEdit = (EditText) findViewById(R.id.EDTTMNAME);
            EditText MeetDesEdit = (EditText) findViewById(R.id.EDTMEETDES);
            EditText VenueEdit = (EditText) findViewById(R.id.EDTVENUE);
            EditText TimeStampEdit = (EditText) findViewById(R.id.EDTTIME);

            JSONObject mailToJson = new JSONObject();
            mailToJson.put("projName", ProjectNameEdit.getText().toString());
            mailToJson.put("teamName", TeamNameEdit.getText().toString());
            mailToJson.put("meetDesc",  MeetDesEdit.getText().toString());
            mailToJson.put("venue", VenueEdit.getText().toString());
            mailToJson.put("time", TimeStampEdit.getText().toString());
            ArrayList<String> mailToList = DBMgr.GetInstance().getMailToList(mailToJson);
            String mStringArray[]=mailToList.toArray(new String[mailToList.size()]);
            //String[] mStringArray = (String[])mailToList.toArray();
            StringBuilder nameBuilder = new StringBuilder();
            String mailToListStr=null;Boolean flag=true;
            if (mStringArray.length > 0) {
                for (String str : mStringArray) {
                    if(flag){
                    mailToListStr=str;
                    flag=false;
                    }
                    else{
                        mailToListStr=mailToListStr+","+str;
                    }
                }
            }

            Boolean mailSentBool = sendMail(mailToJson,mailToListStr);
            /*Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, mStringArray);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Meeting Request:"+TeamNameEdit.getText().toString());
            emailIntent.putExtra(Intent.EXTRA_TEXT,"Discussion About:"+MeetDesEdit.getText().toString()+"\n Venue:"+VenueEdit.getText().toString()
                    +"\n Time and Date:"+ TimeStampEdit.getText().toString());
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();*/

            boolean meeetReqStatus = DBMgr.GetInstance().addMeetReq(mailToJson);

            if(mailSentBool && meeetReqStatus){
                toast = Toast.makeText(context, "Request Sent to Team Members!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                toast = Toast.makeText(context, "Unable to send the mail!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            toast = Toast.makeText(context, "Unable to send the mail!", Toast.LENGTH_SHORT);
            toast.show();
        }
        Intent meetListCreate = new Intent(getApplicationContext(),MeetingList.class);
        startActivity(meetListCreate);
    }

    public boolean sendMail(JSONObject mailToJson,String mailToList){

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "587");
            props.put("mail.smtp.starttls.enable","true");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("awsid@gmail.com", "SomePassword");
                        }
                    });

            MimeMessage mm=new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress("mahesh9128@gmail.com"));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mailToList));
            //Adding subject
            mm.setSubject("Team meeting at :"+mailToJson.getString("venue"));
            //Adding message
            mm.setText(mailToJson.getString("meetDesc"));

            //Sending email
            Transport transport = session.getTransport("smtps");
            transport.send(mm);
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
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
