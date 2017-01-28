import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbsha.sprom.DBMgr;
import com.example.hbsha.sprom.R;

import java.util.ArrayList;

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




}
