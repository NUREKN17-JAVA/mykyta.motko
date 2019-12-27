package ua.nure.cs.Motko.usermanagement.db;

import java.util.Collection;

import ua.nure.cs.Motko.usermanagement.domain.User;

public interface Dao {
	
	User create(User user) throws DatabaseException;
	
	void update(User user) throws DatabaseException;
	
	void delete(User user) throws DatabaseException;
	
	User find(Long id) throws DatabaseException;
	
	Collection<User> findAll() throws DatabaseException;
	
	Collection<?> find(String firstName, String lastName) throws DatabaseException;
	
	void setConnectionFactory(ConnectionFactory connectionFactory);
}
