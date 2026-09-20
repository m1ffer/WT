package edu.epam.fop.jdbc.callable;

import java.sql.*;

public class DbManager {

	private DbManager() {
		throw new UnsupportedOperationException();
	}

	public static int callCountGroups(Connection connection) throws SQLException {
		String query = "{call COUNT_GROUPS(?)}";
		try(CallableStatement stmt = connection.prepareCall(query)){
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.execute();
			return stmt.getInt(1);
		}
	}

	public static int callCountStudents(Connection connection) throws SQLException {
		String callQuery = "{call COUNT_STUDENTS(?)}";
		try(CallableStatement callStmt = connection
					.prepareCall(callQuery)){
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.execute();
			return callStmt.getInt(1);
		}
	}

	public static int callCountStudentsByGroupId(Connection connection, int groupId) throws SQLException {
		String callQuery = "{call COUNT_STUDENTS_BY_GROUP_ID(?, ?)}";
		try(CallableStatement callStmt = connection
				.prepareCall(callQuery)){
			callStmt.setInt(1, groupId);
			callStmt.registerOutParameter(2, Types.INTEGER);
			callStmt.execute();
			return callStmt.getInt(2);
		}
	}
}