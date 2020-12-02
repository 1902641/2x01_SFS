package entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Controllers.MySQLConnection;

public class Component {
	String comp_id;
	String comp_name;
	String comp_desc;
	double comp_weight;
	boolean comp_visible;

	public boolean getComp_visible() {
		return comp_visible;
	}

	public void setComp_visible(boolean comp_visible) {
		this.comp_visible = comp_visible;
	}

	public double getComp_weight() {
		return comp_weight;
	}

	public void setComp_weight(double comp_weight) {
		this.comp_weight = comp_weight;
	}

	public String getComp_id() {
		return comp_id;
	}

	public void setComp_id(String comp_id) {
		this.comp_id = comp_id;
	}

	public String getComp_name() {
		return comp_name;
	}

	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}

	public String getComp_desc() {
		return comp_desc;
	}

	public void setComp_desc(String comp_desc) {
		this.comp_desc = comp_desc;
	}

	public Component(String comp_id, String comp_name, String comp_desc, double comp_weight, boolean comp_visible) {
		super();
		this.comp_id = comp_id;
		this.comp_name = comp_name;
		this.comp_desc = comp_desc;
		this.comp_weight = comp_weight;
		this.comp_visible = comp_visible;
	}

	public Component() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Component convertToComponent(ResultSet rs) throws SQLException {
		Component cp;
		String id = rs.getString("comp_id");
		String name = rs.getString("comp_name");
		String desc = rs.getString("comp_desc");
		double weight = rs.getDouble("comp_weight");
		boolean visible = rs.getBoolean("comp_visible");
		cp = new Component(id, name, desc, weight, visible);
		return cp;
	}

	public ArrayList<Component> retrieveAllComponents() {
		ArrayList<Component> alcomp = new ArrayList<Component>();
		Component comp = new Component();
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "SELECT * FROM 2x01_db.component;";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				Component cp = new Component();
				comp = cp.convertToComponent(rs);
				alcomp.add(comp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		// end of retrieve
		return alcomp;
	}

	public boolean setVisibility(String comp_id, boolean visibility) {
		// declare local variables
		boolean success = false;
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		PreparedStatement pstmt;

		// step 1 - establish connection to database
		db.getConnection();

		// step 2 - declare the SQL statement
		dbQuery = "UPDATE 2x01_db.component SET comp_visible = ? WHERE comp_id = ?";
		pstmt = db.getPreparedStatement(dbQuery);

		// step 3 - to update record using executeUpdate method
		try {
			pstmt.setBoolean(1, visibility);
			pstmt.setString(2, comp_id);

			if (pstmt.executeUpdate() == 1)
				success = true;
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(success);
		// step 4 - close connection
		db.terminate();

		return success;
	}

	public String retrieveCompNameByID(String comp_id) {
		String comp_name="";
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		PreparedStatement pstmt;
		// step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement
		dbQuery = "select comp_name FROM 2x01_db.component WHERE comp_id =? ";
		pstmt = db.getPreparedStatement(dbQuery);
		// step 3 - execute query
		try {
			pstmt.setString(1,comp_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				comp_name = rs.getString("comp_name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		return comp_name;
	}
}
