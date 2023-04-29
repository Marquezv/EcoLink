package com.ecolink.dev.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecolink.dev.server.domain.entity.Group;
import com.ecolink.dev.server.persistence.JdbcDao;
import com.ecolink.dev.server.utils.ConnectJDBC;

public class GroupDao implements JdbcDao<Group> {
	
	
	Group group;
	
	
	@Override
	public List<Group> findAll() throws SQLException {
		List<Group> groups = new ArrayList<>();
		String query = "SELECT * FROM groups";
		try(Connection conn = ConnectJDBC.connectDB()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Group group = mapRowToUser(resultSet);
				groups.add(group);
			}
		}

		return groups;
	}
	
	@Override
	public Group findByToken(String token) throws SQLException {
		String query = "SELECT * FROM groups WHERE token=?";
		Connection connection = ConnectJDBC.connectDB();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, token);
			try(ResultSet resultSet = statement.executeQuery()){
				if(resultSet.next()) {
					Group group = mapRowToUser(resultSet);
					return group;
				}
			}
		}
		return null;
	}
	
	@Override
	public void save(Group group) throws SQLException {
        String query = "INSERT INTO groups (id, token, name, password, tkAdmin, userLimit) VALUES (?, ?, ?, ?, ?, ?)";
		Connection connection = ConnectJDBC.connectDB();
        try(PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, group.getId());
        	statement.setString(2, group.getToken());
        	statement.setString(3, group.getName());
        	statement.setString(4, group.getPassword());
        	statement.setString(5, group.getTkAdmin());
        	statement.setInt(6, group.getUserLimit());
        	statement.executeUpdate();
        	System.out.println("SAVED");
        }
        
	}

	@Override
	public void update(Group group) throws SQLException {
        String query = "UPDATE groups SET name=?, password=? WHERE token=?";
		Connection connection = ConnectJDBC.connectDB();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, group.getName());
        	statement.setString(2, group.getPassword());
        	statement.setString(3, group.getToken());
        	statement.setInt(4, group.getUserLimit());
        	statement.executeUpdate();
        }
	}

	@Override
	public void deleteByToken(String token) throws SQLException {
        String query = "DELETE FROM groups WHERE token=?";
		Connection connection = ConnectJDBC.connectDB();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            statement.executeUpdate();
        }
	}
	
	private Group mapRowToUser(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String token = resultSet.getString("token");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String tkAdmin = resultSet.getString("tkAdmin");
        Integer userLimit = resultSet.getInt("userLimit");
        return new Group(id, token, name, password, tkAdmin, userLimit);
    }


	
}
