package edu.epam.fop.jdbc.create;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

	private DbManager() {
		throw new UnsupportedOperationException();
	}

	public static List<Group> fetchGroups(Connection connection) throws SQLException {
		List<Group> res = new ArrayList<>();
		String statement = "SELECT id, group_name FROM groups";
		try(Statement stmt = connection
					.createStatement();
			ResultSet rs = stmt.executeQuery(statement)){
			while(rs.next()){
				int groupId = rs.getInt("id");
				String groupName = rs.getString("group_name");
				res.add(new Group(groupId, groupName));
			}
		}
		return res;
	}

	public static List<Student> fetchStudents(Connection connection) throws SQLException {
		List<Student> res = new ArrayList<>();
		String statement =
				"SELECT s.id AS student_id, s.first_name, s.last_name, s.group_id, g.group_name FROM students s LEFT JOIN groups g ON s.group_id = g.id";
		try(Statement stmt = connection
				.createStatement();
			ResultSet rs = stmt.executeQuery(statement)){
			while(rs.next()) {
				int studentId = rs.getInt("student_id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Group group = null;
				int groupId = rs.getInt("group_id");
				if (!rs.wasNull()) {
					String groupName = rs.getString("group_name");
					group = new Group(groupId, groupName);
				}
				res.add(new Student(studentId, firstName, lastName, group));
			}
		}
		return res;
	}
}