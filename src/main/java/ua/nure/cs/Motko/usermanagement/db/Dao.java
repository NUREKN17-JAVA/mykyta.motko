package ua.nure.cs.Motko.usermanagement.db;

import java.util.Collection;

import ua.nure.cs.Motko.usermanagement.domain.User;

//import ua.nure.cs.Motko.usermanagement.domain.User;

public interface Dao <T> {
	T create(T entity) throws DatabaseException;
	
	void update(T entity) throws DatabaseException;
	
	T delete(T entity) throws DatabaseException;
	
	T find(Long id) throws DatabaseException;
	
	Collection <T> findAll() throws DatabaseException;
	
	public void setConnectionFactory(ConnectionFactory connectionFactory);
}
