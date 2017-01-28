package com.example.hbsha.sprom;


import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import java.sql.PreparedStatement;

import static com.example.hbsha.sprom.LoginActivity.validateJson;

public class DBMgr {

    private static final String url = "jdbc:mysql://abhi22.cbqwdbpdiynx.us-west-1.rds.amazonaws.com:3306/abhidb";
    private static final String user = "abhi22";
    private static final String pass = "tutu2222";
    private static DBMgr Instance = null;
    private StrictMode.ThreadPolicy Policy;
    Statement DataBaseStatement;
    static JSONObject prodet = new JSONObject();


    protected DBMgr(){
        ConnectDB();
    }
    private void ConnectDB(){
        Policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(Policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            DataBaseStatement = con.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static DBMgr GetInstance(){
        if(Instance == null){
            Instance = new DBMgr();
        }
        return Instance;
    }

    public ArrayList<String> GetBackLogTask(){
        ArrayList<String> TaskList=new ArrayList<String>();
        try
        {

            ResultSet rs = DataBaseStatement.executeQuery("select taskname from abhidb.task where sprintid is null");
            ResultSetMetaData rmd=rs.getMetaData();
            while(rs.next())
            {
                TaskList.add(rs.getString("taskname"));
            }

        }

        catch(Exception e)
        {

        }
        return TaskList;
    }
    public boolean AddTaskToDatabase(String ProjectName,String TaskName,String TaskDescription,String TaskWeightage) {

        //Table need to modify for task status
        boolean InsertStatus = true;
        String insertTableTaskSQL = "INSERT INTO abhidb.task" + " (taskname,description,weightage, projectname) " + " VALUES " +
                "("+ "'" + TaskName + "'" + "," + "'"  + TaskDescription + "'" +","+ "'" + TaskWeightage + "'"+","  + "'" + ProjectName + "'"  +  ")";

        try {
            DataBaseStatement.executeUpdate(insertTableTaskSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            InsertStatus = false;
        }
        return InsertStatus;
    }

    public boolean DeleteTask(String TaskName){
        boolean DeleteStatus = true;
        String deleteTaskSql = "DELETE FROM abhidb.task WHERE taskname = " + "'"+ TaskName + "'";
        try {
            DataBaseStatement.executeUpdate(deleteTaskSql);
        } catch (SQLException e) {
            DeleteStatus = false;
            e.printStackTrace();
        }
        return DeleteStatus;
    }

    public ArrayList<String> GetTskDetain(String TaskName){
        ArrayList<String> TaskDetail=new ArrayList<String>();
        String TaskDetainSql = "SELECT taskname,description,weightage, projectname,sprintid,taskstatus FROM abhidb.task WHERE taskname = " + "'"+ TaskName+"'";
        try {
            ResultSet TaskDetailResult = DataBaseStatement.executeQuery(TaskDetainSql);
            while (TaskDetailResult.next()){
                //Retrieve by column name
                TaskDetail.add(TaskDetailResult.getString("taskname"));
                TaskDetail.add(TaskDetailResult.getString("description"));
                TaskDetail.add(TaskDetailResult.getString("weightage"));
                TaskDetail.add(TaskDetailResult.getString("projectname"));
                TaskDetail.add(TaskDetailResult.getString("sprintid"));
                TaskDetail.add(TaskDetailResult.getString("taskstatus"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TaskDetail;
    }

    public ArrayList<String> getEmpNameList(){
        ArrayList<String> empList=new ArrayList<String>();

        try {
            Connection con = DBMgr.GetInstance().getConnection();
            PreparedStatement  st = con.prepareStatement("SELECT empname FROM abhidb.user1 WHERE teamname =?");
            st.setString(1,LoginActivity.userInfo.getString("teamname"));

            ResultSet rs = st.executeQuery();
            while (rs.next()){
                empList.add(rs.getString("empname"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return empList;
    }

    public boolean assignTask(String sprintId, String empname,String taskname){
        try {
            Connection con = DBMgr.GetInstance().getConnection();
            PreparedStatement st = con.prepareStatement("UPDATE abhidb.task SET empname=?,sprintid=? WHERE taskname = ?");
            st.setString(1,empname);
            st.setString(2,sprintId);
            st.setString(3,taskname);
            st.executeUpdate();
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean UpdateTask(String ProjectName,String TaskName,String TaskDescription,String TaskWeightage,String status) {
        boolean UpdateStatus = true;
        String UpdateTaskWeightageSql = "UPDATE abhidb.task " + "SET weightage = " + "'" + TaskWeightage +"'"+ "WHERE TaskName = " +"'"+TaskName+"'";
        String UpdateTaskDescSql = "UPDATE abhidb.task " + "SET description = " + "'" + TaskDescription +"'"+ "WHERE TaskName = " +"'"+TaskName+"'";
        String UpdateProjNameSql = "UPDATE abhidb.task " + "SET projectname = " + "'" + ProjectName +"'"+ "WHERE TaskName = " +"'"+TaskName+"'";
        //String UpdateTaskSprintIDSql = "UPDATE abhidb.task " + "SET sprintid = " + "'" + TaskSprintID +"'"+ "WHERE TaskName = " +"'"+TaskName+"'";
        String UpdateTaskStatusSql = "UPDATE abhidb.task " + "SET taskstatus = " + "'" + status +"'"+ "WHERE TaskName = " +"'"+TaskName+"'";
        try {
            DataBaseStatement.executeUpdate(UpdateTaskWeightageSql);
            DataBaseStatement.executeUpdate(UpdateTaskDescSql);
            DataBaseStatement.executeUpdate(UpdateProjNameSql);
            // DataBaseStatement.executeUpdate(UpdateTaskSprintIDSql);
             DataBaseStatement.executeUpdate(UpdateTaskStatusSql);
        } catch (SQLException e) {
            e.printStackTrace();
            UpdateStatus = false;
        }
        return UpdateStatus;
    }

    public ArrayList<String> GetAssignedTasks(){
        ArrayList<String> TaskList=new ArrayList<String>();
        try
        {
            ResultSet rs = DataBaseStatement.executeQuery("select taskname from abhidb.task");
            ResultSetMetaData rmd=rs.getMetaData();
            while(rs.next())
            {
                TaskList.add(rs.getString("taskname"));
            }

        }

        catch(Exception e)
        {

        }
        return TaskList;
    }
    public List<Map<String,String>> GetSprintTask(String sprintid,String status,String empname){
        //ArrayList<String> TaskList=new ArrayList<String>();
        List<Map<String,String>> TaskList = new ArrayList<Map<String,String>>();
        try
        {
            String tn,w;
            Connection con = getConnection();
            PreparedStatement  st = con.prepareStatement("select taskname,weightage from abhidb.task where sprintid=? and taskstatus=?");
            st.setString(1,sprintid);
            st.setString(2,status);
           // st.setString(3,empname);
            ResultSet rs =st.executeQuery();
            while(rs.next())
            {
                tn=rs.getString("taskname");
                w=rs.getString("weightage");
                TaskList.add(createRecord(tn,w));

            }
        }
        catch(Exception e)
        {

        }
        return TaskList;
    }
    public HashMap<String, String> createRecord(String name, String weightage)
    {
        HashMap<String, String> record = new HashMap<String, String>();

        record.put( "taskname", name);
        record.put("weightage",weightage);
        return record;
    }


    public Connection getConnection(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            return con;
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.out.print("Exception in getConnection!1"+ex.getMessage());
            return null;
        }
    }

    public JSONObject validateUser(JSONObject validateJson){
        String a;
        try {
            JSONObject userDetails = new JSONObject();
            Connection con = getConnection();
            PreparedStatement  st = con.prepareStatement("select userflag,teamname,projectname,empname from abhidb.user1 where emailid=? and pass=?");
            st.setString(1,validateJson.getString("mail"));
            st.setString(2,validateJson.getString("pwd"));
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                userDetails.put("teamname",rs.getString("teamname"));
                userDetails.put("projectname",rs.getString("projectname"));
                userDetails.put("userflag",rs.getString("userflag"));
                userDetails.put("empname",rs.getString("empname"));
            }else{
                return null;
            }
            return userDetails;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        //return a;
    }
    public ArrayList<String> GetProjects()
    {
        ArrayList<String> projectList=new ArrayList<String>();
        try
        {
            String name="/0",desc="/0",date="/0",tmem="/0";
            Connection ge = getConnection();
            //PreparedStatement ps = ge.prepareStatement("Select * from project");
            PreparedStatement  ps = ge.prepareStatement("select * from abhidb.project p join abhidb.user1 u  " +
                    "on p.projectname=u.projectname where u.emailid =?" );
            ps.setString(1,validateJson.getString("mail"));
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                name=rs.getString("projectname");
                desc=rs.getString("description");
                date=rs.getString("date");
                tmem=rs.getString("teamname");
                prodet.put("proname",name);
                prodet.put("descrip",desc);
                prodet.put("prodate",date);
                prodet.put("teamnam",tmem);
                projectList.add(name);
            }
        }
        catch(Exception e)
        {

        }
        return projectList;
    }

    public boolean modifyProjects(String name,String des,String date,String team)
    {
        boolean mod=true;


        try
        {
            Connection get = getConnection();
            PreparedStatement p = get.prepareStatement("UPDATE abhidb.project SET description = " + "'" + des +"'"+
                    " WHERE projectname=?");
            p.setString(1,name);
            PreparedStatement p2 = get.prepareStatement("UPDATE abhidb.project SET   date=" + "'" + date +"'"
                    + " WHERE projectname=?");
            p2.setString(1,name);
            PreparedStatement p3 = get.prepareStatement("UPDATE abhidb.project SET teamname = " + "'" + team +"'"
                    +" WHERE projectname=?");
            p3.setString(1,name);
            p.executeUpdate();
            p2.executeUpdate();
            p3.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            mod =false;
        }

        return mod;

    }
    public boolean removeProjects(String rname)
    {
        boolean remo =true;
        try
        {
            Connection get =getConnection();
            PreparedStatement p = get.prepareStatement("DELETE  FROM abhidb.project where projectname=?");
            p.setString(1,rname);
            p.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            remo=false;
        }
        return remo;
    }

    public ArrayList<String> getTeams()
    {
        ArrayList<String> teamlist =new ArrayList<String>();
        try
        {
            Connection ge = getConnection();
            PreparedStatement  ps = ge.prepareStatement("select distinct teamname from abhidb.user1 WHERE  emailid=?" );
            ps.setString(1,validateJson.getString("mail"));
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                String tname=rs.getString("teamname");
                teamlist.add(tname);
            }
        }
        catch(Exception e)
        {

        }
        return teamlist;
    }
    public ArrayList<String> getTeammems(String tname)
    {
        ArrayList<String> teammemlist = new ArrayList<String>();
        try
        {
            Connection ge = getConnection();
            PreparedStatement  ps = ge.prepareStatement("select distinct empname from abhidb.user1 where teamname=? and userflag!=?" );
            ps.setString(1,tname);
            ps.setString(2,"C");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                String ename=rs.getString("empname");
                teammemlist.add(ename);
            }
        }
        catch(Exception e)
        {

        }
        return teammemlist;
    }



    public boolean AddProjectToDatabase(String projectName,String projectDescription,String projectDate,String teammem) {

        boolean InsertStatus = true;
        String insertTableProjectSQL = "INSERT INTO abhidb.project" +  " VALUES " +
                "("+ "'" + projectName + "'" + "," + "'"  + projectDescription + "'" +","+ "'"
                + projectDate + "'" +"," +"'" + teammem +"'"  + ")";

        try {
            DataBaseStatement.executeUpdate(insertTableProjectSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            InsertStatus = false;
        }
        return InsertStatus;
    }

    public ArrayList<String> getSprintList(){
        ArrayList<String> sprintlist=new ArrayList<String>();
        try {
            Connection con = getConnection();
            PreparedStatement  st = con.prepareStatement("select distinct(sprintid) from abhidb.sprint where sprintid IS NOT NULL");
            //st.setString(1,"mail");
            //st.setString(2,"pwd");
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                sprintlist.add(rs.getString("sprintid"));


            }
        }
        catch (Exception ex){

        }
        return sprintlist;
    }
    public boolean createteam (String tname)
    {
        boolean InsertStatus = true;
        String insertTableProjectSQL = "INSERT INTO abhidb.user1" +
                " (teamname) " +
                " VALUES " +
                "("+ "'" + tname +"'"  + ")";

        try {
            DataBaseStatement.executeUpdate(insertTableProjectSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            InsertStatus = false;
        }
        return InsertStatus;

    }
    public boolean createsprint (String sprintid)
    {
        boolean re= true;
        try
        {
            Connection get =getConnection();
            PreparedStatement p = get.prepareStatement("insert into abhidb.sprint (sprintid) values(?)");
            p.setString(1,sprintid);
            p.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            re=false;
        }
        return re;

    }
    public boolean AddmemtoTeam(String teamName,String ename,String eid,String emprole) {

        boolean InsertStatus = true;
        String insertTableProjectSQL = "INSERT INTO abhidb.user1" +
                " (empname,empid,role,teamname) " +
                " VALUES " +
                "("+ "'" + ename + "'" + "," + "'"  + eid + "'" +","+ "'"
                + emprole + "'" +"," +"'" + teamName +"'"  + ")";

        try {
            DataBaseStatement.executeUpdate(insertTableProjectSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            InsertStatus = false;
        }
        return InsertStatus;
    }
    public ArrayList<String> getMemberdetails(String name)
    {
        ArrayList<String> mem=new ArrayList<String>();
        try
        {
            Connection con = getConnection();
            PreparedStatement  st = con.prepareStatement("select empid,role from abhidb.user1 where empname=?");
            st.setString(1,name);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                String eid=rs.getString("empid");
                String erole=rs.getString("role");

                mem.add(eid);
                mem.add(erole);
            }


        }
        catch(SQLException s)
        {

        }
        return mem;


    }
    public boolean updateTeammember(String u1,String u2,String u3)
    {
        boolean update =true;
        try
        {
            Connection get = getConnection();
            PreparedStatement p = get.prepareStatement("UPDATE abhidb.user1 SET empid = " + "'" + u2 +"'"+
                    " WHERE empname=?");
            p.setString(1,u1);
            PreparedStatement p2 = get.prepareStatement("UPDATE abhidb.user1 SET   role=" + "'" + u3 +"'"
                    + " WHERE empname=?");
            p2.setString(1,u1);

            p.executeUpdate();
            p2.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            update=false;
        }

        return update;
    }

    public boolean removeTeammember(String r1)
    {
        boolean re= true;
        try
        {
            Connection get =getConnection();
            PreparedStatement p = get.prepareStatement("DELETE  FROM abhidb.user1 where empname=?");
            p.setString(1,r1);
            p.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            re=false;
        }
        return re;
    }
    public int RetrieveProgress(String sprintid)
    {

        double tottasks,donetasks,progress=0;
        try {
            Connection con = getConnection();
            PreparedStatement  st = con.prepareStatement("select count(taskname) as CT from abhidb.task where sprintid=?");
            st.setString(1,sprintid);
            PreparedStatement  st1 = con.prepareStatement("select count(taskname) as CDT from abhidb.task where sprintid=? and taskstatus=?");
            st1.setString(1,sprintid);
            st1.setString(2,"done");
            ResultSet rs = st.executeQuery();
            ResultSet rs1=st1.executeQuery();
            String tt="",dt="";
            while(rs.next()){tt=rs.getString("CT");}
            while(rs1.next()){dt=rs1.getString("CDT");}
            tottasks=Double.parseDouble(tt);
            donetasks=Double.parseDouble(dt);
            progress=(donetasks/tottasks)*100;
        }
        catch (Exception ex){

        }
        return (int) progress;
    }

    public Boolean addMeetReq(JSONObject mailToJson) {
        try {
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement("INSERT INTO abhidb.meeting" +
                    "(venue,meettime,meetingdes,teamname) values"+
                    "(?,?,?,?)");
            st.setString(1,mailToJson.getString("venue"));
            st.setString(2,mailToJson.getString("time"));
            st.setString(3,mailToJson.getString("meetDesc"));
            st.setString(4,mailToJson.getString("teamName"));
            st.executeUpdate();
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public  ArrayList<String> getMailToList(JSONObject mailToJson){
        ArrayList<String> mailToList = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement  st = con.prepareStatement("select emailid from abhidb.user1 where teamname=? and projectname=?" +
                    "and userFlag=?");
            st.setString(1,mailToJson.getString("projName"));
            st.setString(2,mailToJson.getString("teamName"));
            st.setString(3,"TM");
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                mailToList.add(rs.getString("emailid"));
            }else{
                return null;
            }
        }
        catch (Exception ex){
            System.out.print("Exception in validate user!!"+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return mailToList;
    }

    public ArrayList<String> getMeetingList(){
        ArrayList<String> meetingList = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement  st = con.prepareStatement("select meetingdes from abhidb.meeting where teamname=?");
            st.setString(1,LoginActivity.userInfo.getString("teamname"));
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                meetingList.add(rs.getString("meetingdes"));
            }else{
                return null;
            }
        }
        catch (Exception ex){
            System.out.print("Exception in validate user!!"+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return meetingList;
    }

    public JSONObject getMeetingReqValue(String value){
        JSONObject meetingJson = new JSONObject();
        try {
            Connection con = getConnection();
            PreparedStatement  st = con.prepareStatement("select venue,meettime from abhidb.meeting where meetingdes=?");
            st.setString(1,value);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                meetingJson.put("venue",rs.getString("venue"));
                meetingJson.put("meettime",rs.getString("meettime"));
            }else{
                return null;
            }
        }
        catch (Exception ex){
            System.out.print("Exception in validate user!!"+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return meetingJson;
    }

    public boolean registerUser(JSONObject userDetails){
        try {
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement("INSERT INTO abhidb.user1" +
                    "(user,pass,empname,empid,role,emailid,userflag) values" +
                    "(?,?,?,?,?,?,?)");
            st.setString(1, userDetails.getString("userID"));
            st.setString(2, userDetails.getString("pwd"));
            st.setString(3, userDetails.getString("empname"));
            st.setString(4, userDetails.getString("empid"));
            st.setString(5, userDetails.getString("role"));
            st.setString(6, userDetails.getString("email"));
            if(userDetails.getString("role").contains("Client"))
                st.setString(7, "C");
            if(userDetails.getString("role").contains("Lead"))
                st.setString(7, "TL");
            if(userDetails.getString("role").contains("Member"))
                st.setString(7,"TM");
            st.executeUpdate();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
