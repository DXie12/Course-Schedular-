import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dxie1
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduledByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateSchdeuleEntry;
    private static ResultSet resultSet;
    
public static void addScheduleEntry(ScheduleEntry entry){
    connection = DBConnection.getConnection();
    try{
        addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, studentid, coursecode, status, timestamp) values (?,?,?,?,?)");
        addScheduleEntry.setString(1 , entry.getSemester());
        addScheduleEntry.setString(2 , entry.getCoursecode());
        addScheduleEntry.setString(3 , entry.getStudentID());
        addScheduleEntry.setString(4 , entry.getStatus());
        addScheduleEntry.setTimestamp(5 , entry.getTimestamp());
        addScheduleEntry.executeUpdate();
    }
    catch(SQLException sqlException){
        sqlException.printStackTrace();
    }
}

public static ArrayList<ScheduleEntry> getScheduledByStudent(String semester, String studentID){
    connection = DBConnection.getConnection();
    ArrayList<ScheduleEntry> entry = new ArrayList<ScheduleEntry>();
    try{
        getScheduledByStudent = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and studentid = ?");
        getScheduledByStudent.setString (1, semester);
        getScheduledByStudent.setString(2, studentID);
        resultSet = getScheduledByStudent.executeQuery();
        
    while(resultSet.next()){
        ScheduleEntry obj = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
        entry.add(obj);
    }
    }
    catch (SQLException sqlException){
        sqlException.printStackTrace();
    }
    return entry;
}
public static int getScheduledStudentCount(String currentSemester, String courseCode){
    connection = DBConnection.getConnection();
    
    int count = 1;
    try{
        getScheduledStudentCount = connection.prepareStatement("select semester, studentid, coursecode, status, timestamp from app.schedule where semester = (?) and coursecode = (?)");
        getScheduledStudentCount.setString(1 , currentSemester);
        getScheduledStudentCount.setString(2 , courseCode);
        
        resultSet = getScheduledStudentCount.executeQuery();
       
    while(resultSet.next()){
        count ++;
    }
    }
    catch (SQLException sqlException){
        sqlException.printStackTrace();
    }
    return count;
}


public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
    connection = DBConnection.getConnection();
    ArrayList<ScheduleEntry> scheduled = new ArrayList<ScheduleEntry>();
    try{
    getScheduledStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = (?) and coursecode = (?) and status = (?) order by timestamp" );
    getScheduledStudentsByCourse.setString(1, semester);
    getScheduledStudentsByCourse.setString(2 , courseCode);
    getScheduledStudentsByCourse.setString(3 , "S");
    resultSet = getScheduledStudentsByCourse.executeQuery();
    
    while(resultSet.next()){
        ScheduleEntry obj = new ScheduleEntry(resultSet.getString(1), resultSet.getString(3), resultSet.getString(2), resultSet.getString(4), resultSet.getTimestamp(5));
        scheduled.add(obj);
    }
    
    }
    catch (SQLException sqlException){
        sqlException.printStackTrace();
    }
    return scheduled;
}

public static ArrayList<ScheduleEntry> getWaitlistedStudentByCourse(String semester, String courseCode){
    connection = DBConnection.getConnection();
    ArrayList<ScheduleEntry> waitlisted = new ArrayList<ScheduleEntry>();
    try{
    getScheduledStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = (?) and coursecode = (?) and status = (?) order by timestamp" );
    getScheduledStudentsByCourse.setString(1, semester);
    getScheduledStudentsByCourse.setString(2 , courseCode);
    getScheduledStudentsByCourse.setString(3 , "W");
    resultSet = getScheduledStudentsByCourse.executeQuery();
    
    while(resultSet.next()){
        ScheduleEntry obj = new ScheduleEntry(resultSet.getString(1), resultSet.getString(3), resultSet.getString(2), resultSet.getString(4), resultSet.getTimestamp(5));
        waitlisted.add(obj);
    }
    
    }
    catch (SQLException sqlException){
        sqlException.printStackTrace();
    }
    return waitlisted;
}

public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
    connection = DBConnection.getConnection();
    try{
        dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = (?) and studentid = (?) and coursecode = (?)");
        dropStudentScheduleByCourse.setString(1, semester);
        dropStudentScheduleByCourse.setString(2, studentID);
        dropStudentScheduleByCourse.setString(3, courseCode);
        dropStudentScheduleByCourse.executeUpdate();
    }
    catch(SQLException sqlException){
    sqlException.printStackTrace();
    
    }    
}

public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
    try{
        dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = (?) and coursecode = (?)");
        dropScheduleByCourse.setString(1 , semester);
        dropScheduleByCourse.setString(2 , courseCode);
        dropScheduleByCourse.executeUpdate();
    }
    catch(SQLException sqlException){
    sqlException.printStackTrace();
    
    } 
}

public static void updateSchdeuleEntry(String semester , ScheduleEntry entry){
    connection = DBConnection.getConnection();
    try{
        addScheduleEntry = connection.prepareStatement("update app.schedule set status = 'S' where semester = (?) and studentid = (?) and coursecode = (?)");
        addScheduleEntry.setString(1 , semester);
        addScheduleEntry.setString(2 , entry.getStudentID());
        addScheduleEntry.setString(3 , entry.getCoursecode());

        addScheduleEntry.executeUpdate();
    }
    catch(SQLException sqlException){
        sqlException.printStackTrace();
    }
    }
}