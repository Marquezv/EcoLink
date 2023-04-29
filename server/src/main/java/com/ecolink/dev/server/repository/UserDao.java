package com.ecolink.dev.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecolink.dev.server.domain.entity.User;
import com.ecolink.dev.server.persistence.JdbcDao;
import com.ecolink.dev.server.utils.ConnectJDBC;

public class UserDao implements JdbcDao<User> {
	
	
	User user;
	
	
	@Override
	public List<User> findAll() throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM users";
		try(Connection conn = ConnectJDBC.connectDB()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				User user = mapRowToUser(resultSet);
				users.add(user);
			}
			conn.close();
		}
		
		return users;
	}
	
	@Override
	public User findByToken(String token) throws SQLException {
		String query = "SELECT * FROM users WHERE token=?";
		Connection connection = ConnectJDBC.connectDB();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, token);
			try(ResultSet resultSet = statement.executeQuery()){
				if(resultSet.next()) {
					User user = mapRowToUser(resultSet);
					connection.close();
					return user;
				}
			}
		}
		return null;
	}
	
	@Override
	public void save(User user) throws SQLException {
        String query = "INSERT INTO users (id, token, name, password) VALUES (?, ?, ?, ?)";
		Connection connection = ConnectJDBC.connectDB();
        try(PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, user.getId());
        	statement.setString(2, user.getToken());
        	statement.setString(3, user.getName());
        	statement.setString(4, user.getPassword());
        	statement.executeUpdate();
        	System.out.println("SAVED");
        	connection.close();
        }
        
	}

	@Override
	public void update(User user) throws SQLException {
        String query = "UPDATE users SET name=?, password=? WHERE token=?";
		Connection connection = ConnectJDBC.connectDB();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, user.getName());
        	statement.setString(2, user.getPassword());
        	statement.setString(3, user.getToken());
        	statement.executeUpdate();
        	connection.close();
        }
	}

	@Override
	public void deleteByToken(String token) throws SQLException {
        String query = "DELETE FROM users WHERE token=?";
		Connection connection = ConnectJDBC.connectDB();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            statement.executeUpdate();
        }
    	connection.close();
	}
	
	private User mapRowToUser(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String token = resultSet.getString("token");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        return new User(id, token, name, password);
    }


	
}
