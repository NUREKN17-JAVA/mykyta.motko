package ua.nure.cs.Motko.usermanagement.db;

import ua.nure.cs.Motko.usermanagement.domain.User;

public class DaoFactoryImpl extends DaoFactory {

	@Override
	public Dao getUserDao() {
		Dao result;
		try {
			Class<?> clazz = Class.forName(properties.getProperty(USER_DAO));
			result=(Dao) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}

}
