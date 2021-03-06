package ua.nure.cs.Motko.usermanagement.db;

import com.mockobjects.dynamic.Mock;

public class MockDaoFactory extends DaoFactory {
	
	private Mock mockUserDao;
	
	public MockDaoFactory() {
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
