
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
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudent;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
  
   
public static void addStudent(StudentEntry student){
    connection = DBConnection.getConnection();
    try{
        addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?, ?, ?)");
        addStudent.setString(1 , student.getStudentID());
        addStudent.setString(2 , student.getFirstname());
        addStudent.setString(3 , student.getLastname());
        addStudent.executeUpdate();
    }
    catch(SQLException sqlException){
        sqlException.printStackTrace();
    }
}

public static ArrayList<StudentEntry> getAllStudent(){
    connection = DBConnection.getConnection();
    ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
    try{
       getAllStudent = connection.prepareStatement("select studentid, firstname, lastname from app.student order by lastname");
       resultSet = getAllStudent.executeQuery();
       
       while(resultSet.next()){
           StudentEntry obj = new StudentEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
           student.add(obj);
           
       }
    }
    catch(SQLException sqlException){
            sqlException.printStackTrace();
     }
    return student;
}
public static StudentEntry getStudent(String studentID){
    StudentEntry student = null;
    connection = DBConnection.getConnection();
    try{
        getStudent = connection.prepareStatement("select * from app.student where studentid = (?)");
        getStudent.setString(1 , studentID);
        resultSet = getStudent.executeQuery();
        
    while (resultSet.next()){
        student = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
    }
    }
    catch(SQLException sqlException){
        sqlException.printStackTrace();
    }
    return student;
}

public static void dropStudent(String studentID){
    connection = DBConnection.getConnection();
    try{
        dropStudent = connection.prepareStatement("delete from app.student where studentid = (?)");
        dropStudent.setString(1 , studentID);
        dropStudent.executeUpdate();
    }
    catch(SQLException sqlException){
        sqlException.printStackTrace();
    }
}
}

    
