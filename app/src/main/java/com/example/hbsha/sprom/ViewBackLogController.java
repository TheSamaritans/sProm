package com.example.hbsha.sprom;

import java.util.ArrayList;

/**
 * Created by Swati on 11/5/2016.
 */

public class ViewBackLogController {
    private DBMgr DBInstance;
    ViewBackLogController(){
        DBInstance = DBMgr.GetInstance();
    }
    public ArrayList<String> GetBackLogTask(){
        ArrayList<String> BackLogTaskList = DBInstance.GetBackLogTask();
        return BackLogTaskList;
    }
    public boolean AddTaskToDatabase(String ProjectName,String TaskName,String TaskDescription,String TaskWeightage) {
        boolean Addstatus = DBInstance.AddTaskToDatabase(ProjectName,TaskName,TaskDescription,TaskWeightage);
        return Addstatus;
    }

    public boolean DeleteTask(String TaskName) {
        boolean DeleteStatus = DBInstance.DeleteTask(TaskName);
        return DeleteStatus;
    }

    public ArrayList<String> GetTskDetain(String TaskName){
        ArrayList<String> TaskDetail = DBInstance.GetTskDetain(TaskName);
        return TaskDetail;
    }
    public boolean UpdateTask(String ProjectName,String TaskName,String TaskDescription,String TaskWeightage,String status) {
        boolean UpdateStatus = DBInstance.UpdateTask(ProjectName,TaskName,TaskDescription,TaskWeightage,status);
        return UpdateStatus;
    }

    public ArrayList<String> getEmpNames(){
        ArrayList<String> empNameList = DBInstance.getEmpNameList();
        return empNameList;
    }

    public boolean assignTask(String sprintid,String empname,String taskname){
        Boolean assignStatus = DBInstance.assignTask(sprintid,empname,taskname);
        return assignStatus;
    }
}
