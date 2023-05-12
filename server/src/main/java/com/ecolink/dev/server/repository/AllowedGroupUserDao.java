package com.ecolink.dev.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecolink.dev.server.domain.entity.AllowedGroupUser;
import com.ecolink.dev.server.persistence.JdbcDao;
import com.ecolink.dev.server.utils.ConnectJDBC;

public class AllowedGroupUserDao implements JdbcDao<AllowedGroupUser> {
	
	
	AllowedGroupUser allowedGroupUser;
	
	
	@Override
	public List<AllowedGroupUser> findAll() throws SQLException {
		List<AllowedGroupUser> allowed = new ArrayList<>();
		String query = "SELECT * FROM allowedGroupUser";
		try(Connection conn = ConnectJDBC.connectDB()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				AllowedGroupUser allowedGroupUser = mapRowToUser(resultSet);
				allowed.add(allowedGroupUser);
			}
			conn.close();
		}

		return allowed;
	}
	
	@Override
	public AllowedGroupUser findByToken(String tkGroup) throws SQLException {
		String query = "SELECT * FROM allowedGroupUser WHERE id=?";
		Connection connection = ConnectJDBC.connectDB();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, tkGroup);
			try(ResultSet resultSet = statement.executeQuery()){
				if(resultSet.next()) {
					AllowedGroupUser group = mapRowToUser(resultSet);
					connection.close();
					return group;
				}
			}
		}
		return null;
	}
	
	@Override
	public void save(AllowedGroupUser allowedGroupUser) throws SQLException {
        String query = "INSERT INTO allowedGroupUser (id, tkGroup, tkUser, level) VALUES (?, ?, ?, ?)";
		Connection connection = ConnectJDBC.connectDB();
        try(PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, allowedGroupUser.getId());
        	statement.setString(2, allowedGroupUser.getTkGroup());
        	statement.setString(3, allowedGroupUser.getTkUser());
        	statement.setInt(4, allowedGroupUser.getLevel());
        	statement.executeUpdate();
            connection.close();
        	System.out.println("SAVED");
        }
        connection.close();

	}

	@Override
	public void update(AllowedGroupUser allowedGroupUser) throws SQLException {
        String query = "UPDATE allowedGroupUser SET level=? WHERE id=?";
		Connection connection = ConnectJDBC.connectDB();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, allowedGroupUser.getLevel());
        	statement.setString(2, allowedGroupUser.getId());
        	statement.executeUpdate();
        	connection.close();
        }
        connection.close();
	}

	@Override
	public void deleteByToken(String id) throws SQLException {
        String query = "DELETE FROM allowedGroupUser WHERE id=?";
		Connection connection = ConnectJDBC.connectDB();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
            connection.close();
        }
        connection.close();
	}
	
	public List<AllowedGroupUser> findGroup(String tkGroup) throws SQLException {
        String query = "SELECT * FROM allowedGroupUser WHERE tkGroup=?";
        List<AllowedGroupUser> allowed = new ArrayList<>();
        try(Connection conn = ConnectJDBC.connectDB()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				AllowedGroupUser allowedGroupUser = mapRowToUser(resultSet);
				allowed.add(allowedGroupUser);
			}
			conn.close();
		}
		return allowed;
	}
	
	private AllowedGroupUser mapRowToUser(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String tkGroup = resultSet.getString("tkGroup");
        String tkUser = resultSet.getString("tkUser");
        Integer level = resultSet.getInt("level");
        return new AllowedGroupUser(id, tkGroup, tkUser, level);
    }

	
}
