package br.edu.projeto.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtil {

	public static void closeResultSet (ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void closePreparedStatement (PreparedStatement ps) {
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void closeConnection (Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {e.printStackTrace();}
	}
}
