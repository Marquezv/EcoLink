package com.ecolink.dev.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecolink.dev.server.domain.User;
import com.ecolink.dev.server.utils.JdbcDao;

public class UserDao implements JdbcDao<User>{
	
	private final Connection connection;
	
	public UserDao(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public List<User> findAll() throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM users";
		try (PreparedStatement statement = connection.prepareStatement(query)){
			try(ResultSet resultSet = statement.executeQuery()) {
				while(resultSet.next()) {
					User user = mapRowToUser(resultSet);
					users.add(user);
				}
			}
		}
		return null;
	}

	@Override
	public User findByToken(String token) throws SQLException {
		String query = "SELECT * FROM users WHERE token=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, token);
			try(ResultSet resultSet = statement.executeQuery()){
				if(resultSet.next()) {
					User user = mapRowToUser(resultSet);
					return user;
				}
			}
		}
		return null;
	}
	

	@Override
	public void save(User user) throws SQLException {
        String query = "INSERT INTO users (id, token, name, password) VALUES (?, ?, ?, ?)";	
        try(PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, user.getId());
        	statement.setString(2, user.getToken());
        	statement.setString(3, user.getName());
        	statement.setString(4, user.getPassword());
        	statement.executeUpdate();
        }
        
	}

	@Override
	public void update(User user) throws SQLException {
        String query = "UPDATE users SET name=?, password=? WHERE token=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, user.getName());
        	statement.setString(2, user.getPassword());
        	statement.setString(3, user.getId());
        	statement.executeUpdate();
        }
	}

	@Override
	public void deleteByToken(String token) throws SQLException {
        String query = "DELETE FROM users WHERE token=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            statement.executeUpdate();
        }
	}
	
	private User mapRowToUser(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String token = resultSet.getString("token");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        return new User(name, password);
    }
	
}
