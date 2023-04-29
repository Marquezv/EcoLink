package com.ecolink.dev.server.persistence;

import java.sql.SQLException;
import java.util.List;

public interface JdbcDao<T> {
	
	List<T> findAll() throws SQLException;
    T findByToken(String token) throws SQLException;
    void save(T entity) throws SQLException;
    void update(T entity) throws SQLException;
    void deleteByToken(String token) throws SQLException;
}
