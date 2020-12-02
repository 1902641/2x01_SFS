package entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Controllers.MySQLConnection;

public class Lecturer extends User{
	String lect_id;
	String lect_fname;
	String lect_lname;
	String lect_mobile;
	String lect_email;
	
	protected String getLect_id() {
		return lect_id;
	}
	protected String getLect_fname() {
		return lect_fname;
	}
	protected String getLect_lname() {
		return lect_lname;
	}
	protected String getLect_mobile() {
		return lect_mobile;
	}
	protected String getLect_email() {
		return lect_email;
	}
	
	public Lecturer(String lect_id, String lect_fname, String lect_lname, String lect_mobile, String lect_email) {
		super();
		this.lect_id = lect_id;
		this.lect_fname = lect_fname;
		this.lect_lname = lect_lname;
		this.lect_mobile = lect_mobile;
		this.lect_email = lect_email;
	}
	public Lecturer() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected Lecturer convertToLecturer(ResultSet rs) throws SQLException {
		Lecturer lr;
		String lect_id = rs.getString("lect_id");
		String lect_fname = rs.getString("lect_fname");
		String lect_lname = rs.getString("lect_lname");
		String lect_mobile = rs.getString("lect_mobile");
		String lect_email = rs.getString("lect_email");
		lr = new Lecturer(lect_id, lect_fname, lect_lname, lect_mobile, lect_email);
		return lr;
	}
	
	@Override
	public Lecturer getUser(String id) {
		//retrieve Student where id = ?
		Lecturer lecturer = new Lecturer();
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT * FROM 2x01_db.lecturer WHERE lect_id = '" + id + "';";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				Lecturer l = new Lecturer();
				lecturer = l.convertToLecturer(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		//end of retrieve
		return lecturer;
	}
	
	public void setUser(Lecturer lecturer) {
		id = lecturer.lect_id;
		fname = lecturer.lect_fname;
		lname = lecturer.lect_lname;
		email = lecturer.lect_email;
		mobile = lecturer.lect_mobile;
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
		dbQuery = "SELECT * FROM 2x01_db.lecturer WHERE lect_id= '"+id+ "';";
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
	public String getNameByID(String id) {
		String name = "";
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT lect_fname, lect_lname FROM 2x01_db.lecturer where lect_id='"+id+"';";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				name = rs.getString("lect_fname")+ " " + rs.getString("lect_lname");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		return name;
	}
}
