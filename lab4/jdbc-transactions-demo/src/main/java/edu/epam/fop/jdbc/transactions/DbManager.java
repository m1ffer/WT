package edu.epam.fop.jdbc.transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DbManager {

	private DbManager() {
		throw new UnsupportedOperationException();
	}

	public static boolean setGroupForStudents(Connection connection, Group group, List<Student> students)
			throws SQLException {
		String query = "UPDATE students " +
				"SET group_id = ? " +
				"WHERE id = ?";
		boolean original = connection.getAutoCommit();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement(query)) {
				for (var i : students) {
					stmt.setInt(1, group.getId());
					stmt.setInt(2, i.getId());
					if (stmt.executeUpdate() == 0){
						connection.rollback();
						return false;
					}
				}
			}
			connection.commit();
			return true;
		}
		catch(SQLException e){
			try {
				connection.rollback();
			}
			catch(SQLException rollE){
				e.addSuppressed(rollE);
			}
			throw e;
		}
		finally{
			connection.setAutoCommit(original);
		}
	}

	public static boolean deleteStudents(Connection connection, List<Student> students) throws SQLException {
		String query = "DELETE FROM students " +
				"WHERE id = ?";
		boolean original = connection.getAutoCommit();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement(query)) {
				for (var i : students){
					stmt.setInt(1, i.getId());
					if (stmt.executeUpdate() == 0){
						connection.rollback();
						return false;
					}
				}
			}
			connection.commit();
			return true;
		}
		catch(SQLException e){
			try{
				connection.rollback();
			}
			catch(SQLException rollE){
				e.addSuppressed(rollE);
			}
			throw e;
		}
		finally{
			connection.setAutoCommit(original);
		}
    }
}