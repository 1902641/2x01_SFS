package entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Controllers.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Student extends User{
	private String stu_id;
	private String stu_fname;
	private String stu_lname;
	private String stu_email;
	private String stu_mobile;
	
	public String getStu_id() {
		return stu_id;
	}

	public String getStu_fname() {
		return stu_fname;
	}

	public String getStu_lname() {
		return stu_lname;
	}

	public String getStu_email() {
		return stu_email;
	}

	public String getStu_mobile() {
		return stu_mobile;
	}

	public Student(String stu_id, String stu_fname, String stu_lname, String stu_email, String stu_mobile) {
		super();
		this.stu_id = stu_id;
		this.stu_fname = stu_fname;
		this.stu_lname = stu_lname;
		this.stu_email = stu_email;
		this.stu_mobile = stu_mobile;
	}
	
	public Student() {
		super();
	}
	
	protected Student convertToStudent(ResultSet rs) throws SQLException {
		Student st;
		String stu_id = rs.getString("stu_id");
		String stu_fname = rs.getString("stu_fname");
		String stu_lname = rs.getString("stu_lname");
		String stu_mobile = rs.getString("stu_mobile");
		String stu_email = rs.getString("stu_email");
		st = new Student(stu_id, stu_fname, stu_lname, stu_mobile, stu_email);
		return st;
	}
	
	@Override
	public Student getUser(String id) {
		//retrieve Student where id = ?
		Student student = new Student();
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT * FROM 2x01_db.student WHERE stud_id = " + id + ";";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				Student s = new Student();
				student = s.convertToStudent(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		//end of retrieve
		return student;
	}
	
	public void setUser(Student student) {
		id = student.stu_id;
		fname = student.stu_fname;
		lname = student.stu_lname;
		email = student.stu_email;
		mobile = student.stu_mobile;
	}
	
	@Override
	public boolean login(String id) {
		boolean exist = false;
		ResultSet rs = null;
		PreparedStatement pstmt;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT * FROM 2x01_db.student WHERE stu_id= '"+id+ "';";
		// step 3 - using DBControlle readRequest method
		pstmt = db.getPreparedStatement(dbQuery);
		try {
			rs = pstmt.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		} catch (Exception e) {
		}
		// step 4 - close connection
		db.terminate();
		
		return exist;
	}
	
	@Override
	public ArrayList<User> getAllUser() {
		ArrayList<User> als = new ArrayList<User>();
		//retrieve Student where id = ?
		Student student = new Student();
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT * FROM 2x01_db.student;";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				Student s = new Student();
				student = s.convertToStudent(rs);
				als.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		//end of retrieve
		return als;
	}
	
	@Override
	public String getNameByID(String id) {
		String name = "";
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT stu_fname, stu_lname FROM 2x01_db.student where stu_id='"+id+";";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				name = rs.getString("stu_fname")+" " + rs.getString("stu_lname");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		return name;
	}
	public ObservableList<Student> retrieveAllStudentForTable(){
		ObservableList<Student> obs = FXCollections.observableArrayList();
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT * FROM 2x01_db.student;";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				obs.add(new Student(rs.getString("stu_id"), rs.getString("stu_fname"), rs.getString("stu_lname")
						, rs.getString("stu_email"), rs.getString("stu_mobile")));
				System.out.println(rs.getString("stu_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		return obs;
	}
}
