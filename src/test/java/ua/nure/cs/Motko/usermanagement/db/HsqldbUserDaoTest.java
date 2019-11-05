package ua.nure.cs.Motko.usermanagement.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.cs.Motko.usermanagement.domain.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {

	private static final String UPDATE_NAME = "NewName";
	  private static final String UPDATE_LNAME = "NewLName";
	  private static final Date UPDATE_DATE = new Date(2000-11-02);
	  private static final String DELETE_TEST_LASTNAME = "Surname";
	  private static final String DELETE_TEST_NAME = "Name";
	  private static final long TEST_ID = 3L;
	  private static final String FIRST_NAME = "John";
	  private static final String LAST_NAME = "Doe";
	  private HsqldbUserDao dao;
	  private ConnectionFactory connectionFactory;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}
	
	public void testFind() {
        try {
            User user = new User();
            user = dao.find(TEST_ID);
            user.setFirstName(FIRST_NAME);
            user.setLastName(LAST_NAME);
            assertEquals(FIRST_NAME,user.getFirstName());
            assertEquals(LAST_NAME, user.getLastName());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
	
	public void testCreate() throws DatabaseException {
		User user = new User();
		user.setFirstName(FIRST_NAME);
		user.setLastName(LAST_NAME);
		user.setDateOfBirth(new Date());
		assertNull(user.getId());
		User userToChek = dao.create(user);
		assertNotNull(userToChek);
		assertNotNull(userToChek.getId());
		assertEquals(user.getFirstName(),userToChek.getFirstName());
		assertEquals(user.getLastName(),userToChek.getLastName());
		assertEquals(user.getDateOfBirth(),userToChek.getDateOfBirth());
		
	}
	
	public void testUpdate() throws DatabaseException{
	    User user = new User();
	    user.setFirstName(DELETE_TEST_NAME);
	    user.setLastName(DELETE_TEST_LASTNAME);
	    user.setDateOfBirth(new Date());
	    
	    User userToCheck = dao.create(user);
	    assertEquals(DELETE_TEST_NAME, userToCheck.getFirstName());
	    assertEquals(DELETE_TEST_LASTNAME, userToCheck.getLastName());
	    assertEquals(user.getDateOfBirth(),userToCheck.getDateOfBirth());
	    
	    userToCheck.setFirstName(UPDATE_NAME);
	    userToCheck.setLastName(UPDATE_LNAME);
	    userToCheck.setDateOfBirth(UPDATE_DATE);
	    
	    dao.update(userToCheck);
	    
	    assertEquals(UPDATE_NAME, userToCheck.getFirstName());
	    assertEquals(UPDATE_LNAME, userToCheck.getLastName());
	    assertEquals(UPDATE_DATE,userToCheck.getDateOfBirth());
	    //Test New branch
	    
	    
	  }
	
	
	public void testDelete() throws DatabaseException{
	    try {
	      User user = new User();
	      user.setFirstName(DELETE_TEST_NAME);
	       user.setLastName(DELETE_TEST_LASTNAME);
	      user.setDateOfBirth(new Date());
	      user = dao.create(user);
	      assertNotNull(user.getId());
	      dao.delete(user);
	      User user2 = dao.find(user.getId());
	      assertNull(user2.getId());
	    } catch (DatabaseException e) {
	      e.printStackTrace();
	      fail(e.toString());
	    }
	    
	  }
	
	public void testFindAll() throws DatabaseException  {
	    try {
	      Collection<User> users = dao.findAll();
	      assertNotNull("Collection is null", users);
	      assertEquals("Collection size does not match", 2, users.size());
	    } catch (DatabaseException e) {
	      e.printStackTrace();
	      fail(e.toString());
	    }
	    
	  }
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver"
				,"jdbc:hsqldb:file:db/usermanagement"
				,"sa"
				,"");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet=new XmlDataSet(
				getClass().
				getClassLoader().
				getResourceAsStream("usersDataSet.xml")
				);
		return dataSet;
	}
	
	
	
}
