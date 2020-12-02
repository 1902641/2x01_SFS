package entities;

import entities.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import Controllers.MySQLConnection;

public class SubComponent{
	String subcomp_id;
	String comp_id;
	String subcomp_name;
	double subcomp_weight;
	String subcomp_desc;
	
	
	public String getSubcomp_id() {
		return subcomp_id;
	}
	public void setSubcomp_id(String subcomp_id) {
		this.subcomp_id = subcomp_id;
	}
	public String getComp_id() {
		return comp_id;
	}
	public void setComp_id(String comp_id) {
		this.comp_id = comp_id;
	}
	public String getSubcomp_name() {
		return subcomp_name;
	}
	public void setSubcomp_name(String subcomp_name) {
		this.subcomp_name = subcomp_name;
	}
	public double getSubcomp_weight() {
		return subcomp_weight;
	}
	public void setSubcomp_weight(double subcomp_weight) {
		this.subcomp_weight = subcomp_weight;
	}
	public String getSubcomp_desc() {
		return subcomp_desc;
	}
	public void setSubcomp_desc(String subcomp_desc) {
		this.subcomp_desc = subcomp_desc;
	}
	public SubComponent(String subcomp_id, String comp_id, String subcomp_name, double subcomp_weight,
			String subcomp_desc) {
		super();
		this.subcomp_id = subcomp_id;
		this.comp_id = comp_id;
		this.subcomp_name = subcomp_name;
		this.subcomp_weight = subcomp_weight;
		this.subcomp_desc = subcomp_desc;
	}
	public SubComponent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SubComponent convertToSubComponent(ResultSet rs) throws SQLException {
		SubComponent sc;
		String subcomp_id = rs.getString("subcomp_id");
		String comp_id = rs.getString("comp_id");
		String subcomp_name = rs.getString("subcomp_name");
		double subcomp_weight = rs.getDouble("subcomp_weight");
		System.out.println("Convert Check"+subcomp_weight);
		String subcomp_desc = rs.getString("subcomp_desc");
		sc = new SubComponent(subcomp_id, comp_id, subcomp_name, subcomp_weight,subcomp_desc);
		return sc;
	}
	
	public ArrayList<SubComponent> retrieveSubComponentByComponentID(String comp_id){
		ArrayList<SubComponent> scc = new ArrayList<SubComponent>();
		SubComponent subcomp = new SubComponent();
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT * FROM 2x01_db.subcomponent Where comp_id ="+comp_id+";";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				SubComponent scp = new SubComponent();
				subcomp = scp.convertToSubComponent(rs);
				scc.add(subcomp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		// end of retrieve
		return scc;
	}
	
	public String retrieveHighestPKValue() {
		String pk="";
		ArrayList<String> listOfSubcompId = new ArrayList<String>();
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		PreparedStatement pstmt;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT subcomp_id FROM 2x01_db.subcomponent;";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				listOfSubcompId.add(rs.getString("subcomp_id"));
				System.out.println(rs.getString("subcomp_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		// end of retrieve
		ArrayList<Integer> listOfSubcompIdv2 = new ArrayList<Integer>();
		for(int i = 0; listOfSubcompId.size()>i; i++) {
			listOfSubcompIdv2.add(Integer.parseInt(listOfSubcompId.get(i))+1);
		}
		pk = Integer.toString(Collections.max(listOfSubcompIdv2));
		System.out.println(pk);
		return pk;
	}
	
	public boolean createSubComponent(String comp_id, String subcomp_name, String subcomp_desc) {
		boolean success = false;
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;	
		PreparedStatement pstmt;
		
		//step 1 - establish connection to database
		db.getConnection();
		//step 2 - declare the SQL statement
		dbQuery = "INSERT INTO 2x01_db.subcomponent ( subcomp_id,comp_id, subcomp_name, subcomp_weight, subcomp_desc) Values(?,?,?,?,?)";
		pstmt = db.getPreparedStatement(dbQuery);
		
		//step 3 - to update record using executeUpdate method
		try {
			pstmt.setString(1, retrieveHighestPKValue());
			pstmt.setString(2, comp_id);
			pstmt.setString(3, subcomp_name);
			pstmt.setDouble(4, 0);
			pstmt.setString(5, subcomp_desc);
			if (pstmt.executeUpdate() == 1)
				success = true;
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(success);
	    //step 4 - close connection
		db.terminate();
		return success;	
	}
	
	public boolean updateSubComponent(String subcomp_id, String subcomp_name, String subcomp_desc) {
		//declare local variables
		boolean success = false;
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;	
		PreparedStatement pstmt;
		//step 1 - establish connection to database
		db.getConnection();		
		//step 2 - declare the SQL statement
		dbQuery = "UPDATE 2x01_db.subcomponent SET subcomp_name = ?, subcomp_desc=?  WHERE subcomp_id=?";
		pstmt = db.getPreparedStatement(dbQuery);
		//step 3 - to update record using executeUpdate method
		try {
			pstmt.setString(1, subcomp_name);
			pstmt.setString(2, subcomp_desc);
			pstmt.setString(3, subcomp_id);
			if (pstmt.executeUpdate() == 1)
				success = true;
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(success);
	    //step 4 - close connection
		db.terminate();
		
		return success;		
	}
	
	public boolean updateSubComponentWeight(String subcomp_id, Double subcomp_weight) {
		//declare local variables
		boolean success = false;
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;	
		PreparedStatement pstmt;
		//step 1 - establish connection to database
		db.getConnection();		
		//step 2 - declare the SQL statement
		dbQuery = "UPDATE 2x01_db.subcomponent SET subcomp_weight=?  WHERE subcomp_id=?";
		pstmt = db.getPreparedStatement(dbQuery);
		//step 3 - to update record using executeUpdate method
		try {
			pstmt.setDouble(1, subcomp_weight);
			pstmt.setString(2, subcomp_id);
			if (pstmt.executeUpdate() == 1)
				success = true;
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    //step 4 - close connection
		db.terminate();
		return success;		
	}
	
	public boolean deleteSubComp(String subcomp_id) {
		//declare local variables
		boolean success = false;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;	
		PreparedStatement pstmt;
		//step 1 - establish connection to database
		db.getConnection();		
		//step 2 - declare the SQL statement
		dbQuery = "DELETE FROM 2x01_db.subcomponent WHERE subcomp_id = ?";
		pstmt = db.getPreparedStatement(dbQuery);
		
		//step 3 - to delete record using executeUpdate method
		try {
			pstmt.setString(1, subcomp_id);
			if (pstmt.executeUpdate() == 1)
				success = true;
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    //step 4 - close connection
		db.terminate();
		
		return success;	
	}
	
}
