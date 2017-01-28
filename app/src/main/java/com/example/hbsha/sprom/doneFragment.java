package com.example.hbsha.sprom;
/**
 * Created by Bhuvie.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import static com.example.hbsha.sprom.LoginActivity.userInfo;


public class doneFragment extends Fragment {
    private List<Map<String,String>> TaskList;public final static String EXTRA_MESSAGE = "com.example.hbsha.sprom";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_done,null);
        Bundle bundle = this.getArguments();
        String sprintid = bundle.getString("sprintid");
        String empname= null;
        try {
            empname = userInfo.getString("empname");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBMgr DBInstance=DBMgr.GetInstance();
        TaskList=DBInstance.GetSprintTask(sprintid,"Done",empname);
        String from[]={"taskname","weightage"};
        int to[]={R.id.text_taskname,R.id.text_weightage};
        ListView todoListView=(ListView) v.findViewById(R.id.list_done);

        SimpleAdapter adapter = new SimpleAdapter(this.getContext(),TaskList,R.layout.activity_customlistitems,from,to);
        todoListView.setAdapter(adapter);
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String taskname = ((TextView) view.findViewById(R.id.text_taskname)).getText() .toString();
                String status = ((TextView) view.findViewById(R.id.text_weightage)).getText() .toString();
                Intent ModifyTaskIntent = new Intent(view.getContext(),ViewAndModifyTask.class);
                ModifyTaskIntent.putExtra(EXTRA_MESSAGE,taskname);
                startActivity(ModifyTaskIntent );

            }
        });


        return v;
    }

}
