package ua.nure.cs.Motko.usermanagement.domain;

import java.util.Calendar;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	private static final int ETALONE_AGE_1 = 20;
	private static final int DAY_OF_BIRTH = 26;
	private static final int MONTH_OF_BIRTH = 3;
	private static final int YEAR_OF_BIRTH = 1999;
	private static final int ETALONE_AGE_2 = 19;
	private static final int MONTH_OF_BIRTH_2 = 11;
	private static final int MONTH_OF_BIRTH_3 = 10;
	private static final int DAY_OF_BIRTH_3 = 26;
	private static final int ETALONE_AGE_3 = 18;
	private static final int YEAR_OF_BIRTH_5 = 1999;
	private static final int MONTH_OF_BIRTH_5 = 10;
	private static final int DAY_OF_BIRTH_5 = 16;
	private User user;
	
	public void testGetFullName() {
		user.setFirstName("John");
		user.setLastName("Doe");
		assertEquals("Doe, John", user.getFullName());
	}
	
	public void testGetAge1() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH, DAY_OF_BIRTH);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_1, user.getAge());
	}

	public void testGetAge2() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_2, DAY_OF_BIRTH);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_2, user.getAge());
	}
	
	public void testGetAge3() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_3, DAY_OF_BIRTH_3);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_2, user.getAge());
	}
	
	public void testGetAge4() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_3, DAY_OF_BIRTH_3);
		user.setDateOfBirth(calendar.getTime());
		assertTrue("Your age is less than 18", user.getAge()>ETALONE_AGE_3);
	}
	
	public void testGetAge5() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH_5, MONTH_OF_BIRTH_5, DAY_OF_BIRTH_5);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_2, user.getAge());
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
