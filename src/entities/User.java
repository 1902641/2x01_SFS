package entities;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class User {
	protected String id;
	protected String fname;
	protected String lname;
	protected String email;
	protected String mobile;
	public String getStu_id() {
		return null;
	};
	public ObservableList<Student> retrieveAllStudentForTable(){
		return null;
	}
	public User getUser(String id) {
		return null;
	}
	public String getNameByID(String id) {
		return null;
	}
	public ArrayList<User> getAllUser() {
		return null;
	}
	public boolean login(String id) {
		return false;
	}
	public String getId() {
		return id;
	}
	public String getFname() {
		return fname;
	}
	public String getLname() {
		return lname;
	}
	public String getEmail() {
		return email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public User(String id, String fname, String lname, String email, String mobile) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.mobile = mobile;
	}
	public User() {
		super();
	}
	
}
