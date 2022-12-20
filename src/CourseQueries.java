/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dxie1
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;
     
    
public static ArrayList<CourseEntry> getAllCourses(String semester){
    connection = DBConnection.getConnection();
    ArrayList<CourseEntry> course = new ArrayList<CourseEntry>();
    try{                                            //reading data
        getAllCourses = connection.prepareStatement("select semester,coursecode, description, seats from app.course where semester = ?");
        getAllCourses.setString(1, semester);
        resultSet = getAllCourses.executeQuery();
    
    while(resultSet.next()){
        CourseEntry obj = new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), Integer.parseInt(resultSet.getString(4)));
        course.add(obj);
        
    }
    }
    catch(SQLException sqlException){
            sqlException.printStackTrace();
     }
    return course;
    
}
public static void addCourse(CourseEntry Course){
    connection = DBConnection.getConnection();
    try{                                        //inserting data
        addCourse = connection.prepareStatement("insert into app.course (semester,coursecode, description, seats) values (? ,? , ? , ?)");
        addCourse.setString(1 , Course.getSemester());
        addCourse.setString(2 , Course.getCourseCode());
        addCourse.setString(3 , Course.getCourseDescription());
        addCourse.setString(4 , String.valueOf(Course.getSeats()));
        addCourse.executeUpdate();
    }
    catch(SQLException sqlExecption){
        sqlExecption.printStackTrace();
    }
   }


public static ArrayList <String> getAllCourseCodes(String semester){
    connection = DBConnection.getConnection();
    ArrayList <String> code = new ArrayList<String>();
    try{
        getAllCourseCodes = connection.prepareStatement("select coursecode from app.course where semester = ?");
        getAllCourseCodes.setString(1, semester);
        resultSet = getAllCourseCodes.executeQuery();
        
    while(resultSet.next()){
        code.add(resultSet.getString(1));
    }
    }
    catch(SQLException sqlException){
        sqlException.printStackTrace();
    }
    return code;
}

public static int getCourseSeats(String courseCode, String semester){
    connection = DBConnection.getConnection();
    int seats = 0;
    try{
        getCourseSeats = connection.prepareStatement("select seats from app.course where coursecode = (?) and semester = (?)");
        getCourseSeats.setString(1, courseCode);
        getCourseSeats.setString(2, semester);
        resultSet = getCourseSeats.executeQuery();
        resultSet.next();
        seats = resultSet.getInt(1);
    }
    
    catch(SQLException sqlExecption){
    sqlExecption.printStackTrace();
    
    }
    return seats;
}
public static void dropCourse(String semester, String courseCode){
    connection = DBConnection.getConnection();
    try{
        dropCourse = connection.prepareStatement("delete from app.course where semester = (?) and coursecode = (?)");
        dropCourse.setString(1 , semester);
        dropCourse.setString(2, courseCode);
        dropCourse.executeUpdate();
    }
    catch(SQLException sqlException){
    sqlException.printStackTrace();
    
    }    
}


}
