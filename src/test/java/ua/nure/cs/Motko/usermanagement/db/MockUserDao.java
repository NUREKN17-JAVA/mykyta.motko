package ua.nure.cs.Motko.usermanagement.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.mockobjects.dynamic.Mock;

import ua.nure.cs.Motko.usermanagement.domain.User;

public class MockUserDao extends DaoFactory {
	private long id = 0;
	private Map users = new HashMap();
	
	public User create(User user) throws DatabaseException {
		Long currentId = new Long(++id);
		user.setId(currentId);
		users.put(currentId, user);
		return user;
	}
	
	public void update(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
		users.put(currentId, user);
	}
	
	public void delete(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
	}
	
	public User find(Long id) throws DatabaseException {
		
		return (User) users.get(id);
	}
	
	public Collection findAll() throws DatabaseException {
		return users.values();
	}
	
	private Mock mockUserDao;
	
	public MockUserDao() {
		mockUserDao = new Mock(Dao.class);
	}
	
	public Mock getMockUserDao() {
		return mockUserDao;
	}
	
	@Override
	public Dao getUserDao() {
		return (Dao) mockUserDao.proxy();
	}

}
