package edu.epam.fop.jdbc.prepared;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

	private DbManager() {
		throw new UnsupportedOperationException();
	}

	public static boolean insertGroup(Connection connection, Group group) throws SQLException {
		String statement = "INSERT INTO groups (group_name) " +
							"VALUES (?)";
		try(PreparedStatement stmt = connection
				.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){
			stmt.setString(1, group.getName());
			stmt.executeUpdate();
			try(ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next())
					group.setId(rs.getInt(1));
			}
			return true;
		}
	}

	public static boolean insertStudent(Connection connection, Student student) throws SQLException {
		String statement = "INSERT INTO students " +
				"(first_name, last_name, group_id) " +
				"VALUES (?, ?, ?)";
		try(PreparedStatement stmt = connection
				.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			if (student.getGroup() != null)
				stmt.setInt(3, student.getGroup().getId());
			else
				stmt.setNull(3, Types.INTEGER);
			stmt.executeUpdate();
			try(ResultSet rs = stmt.getGeneratedKeys()){
				if (rs.next())
					student.setId(rs.getInt(1));
			}
			return true;
		}
	}

	public static Group findFirstGroupByName(Connection connection, String name) throws SQLException {
		String statement = "SELECT id, group_name FROM groups " +
				"WHERE group_name = ? " +
				"ORDER BY id ASC";
		try(PreparedStatement stmt = connection
				.prepareStatement(statement)){
			stmt.setString(1, name);
			try(ResultSet rs = stmt.executeQuery()){
				if (rs.next())
					return new Group(rs.getInt("id"), name);
				else
					return null;
			}
		}
	}

	public static Student findFirstStudentByName(Connection connection, String firstName, String lastName)
			throws SQLException {
		String statement =
				"SELECT s.id AS student_id, s.first_name, s.last_name, " +
				"g.id AS group_id, g.group_name " +
				"FROM students s " +
				"LEFT JOIN groups g ON s.group_id = g.id " +
				"WHERE s.first_name = ? AND s.last_name = ? " +
				"ORDER BY s.id ASC";
		try(PreparedStatement stmt = connection
				.prepareStatement(statement)){
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			try(ResultSet rs = stmt.executeQuery()){
				if (rs.next()){
					int studentId = rs.getInt("student_id");
					Group group = null;
					int groupId = rs.getInt("group_id");
					if (!rs.wasNull())
						group = new Group(groupId,
								rs.getString("group_name"));
					return new Student(studentId, firstName,
							lastName, group);
				}
				else
					return null;
			}
		}
	}

	public static List<Student> findStudentsByGroup(Connection connection, Group group) throws SQLException {
		String query = "SELECT id, first_name, last_name " +
						"FROM students " +
						"WHERE group_id = ?";
		List<Student> res = new ArrayList<>();
		try(PreparedStatement stmt = connection
				.prepareStatement(query)){
			stmt.setInt(1, group.getId());
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next())
					res.add(new Student(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							group
					));
			}
		}
		return res;
	}

	public static boolean updateGroupById(Connection connection, Group group) throws SQLException {
		String query = "UPDATE groups " +
						"SET group_name = ? " +
						"WHERE id = ?";
		try(PreparedStatement stmt = connection
				.prepareStatement(query)){
			stmt.setString(1, group.getName());
			stmt.setInt(2, group.getId());
			return stmt.executeUpdate() != 0;
		}
	}

	public static boolean updateStudentById(Connection connection, Student student) throws SQLException {
		String query = "UPDATE students " +
						"SET first_name = ?, " +
						"last_name = ?, " +
						"group_id = ? " +
						"WHERE id = ?";
		try(PreparedStatement stmt = connection
				.prepareStatement(query)){
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			Group group = student.getGroup();
			if (group == null)
				stmt.setNull(3, Types.INTEGER);
			else
				stmt.setInt(3, group.getId());
			stmt.setInt(4, student.getId());
			return stmt.executeUpdate() != 0;
		}
	}
}