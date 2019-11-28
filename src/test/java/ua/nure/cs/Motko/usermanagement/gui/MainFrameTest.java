package ua.nure.cs.Motko.usermanagement.gui;

import java.awt.Component;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.cs.Motko.usermanagement.db.DaoFactory;
import ua.nure.cs.Motko.usermanagement.db.DaoFactoryImpl;
import ua.nure.cs.Motko.usermanagement.db.MockDaoFactory;
import ua.nure.cs.Motko.usermanagement.db.MockUserDao;
import ua.nure.cs.Motko.usermanagement.domain.User;

public class MainFrameTest extends JFCTestCase {
	
	private static final int NUMBER_OF_COLUMNS_IN_USER_TABLE = 3;
	private static final Date DATE_OF_BIRTH = new Date();
	private static final String DATE_OF_BIRTH_FIELD = "dateOfBirthField";
	private static final String LAST_NAME_FIELD = "lastNameField";
	private static final String FIRST_NAME_FIELD = "firstNameField";
	private static final String CANCEL_BUTTON = "cancelButton";
	private static final String OK_BUTTON = "okButton";
	private static final String ADD_PANEL = "addPanel";
	private static final String LAST_NAME = "Doe";
	private static final String FIRST_NAME = "John";
	private static final String BROWSE_PANEL = "browsePanel";
	private static final String DETAILS_BUTTON = "detailsButton";
	private static final String DELETE_BUTTON = "deleteButton";
	private static final String EDIT_BUTTON = "editButton";
	private static final String ADD_BUTTON = "addButton";

	private MainFrame mainFrame;
	
	private Mock mockUserDao;
	
	private ArrayList<User> users;
	
	private final Date DATE = DATE_OF_BIRTH;
	
	protected void setUp() throws Exception {
		try {
			super.setUp();
			Properties properties = new Properties();
			properties.setProperty("dao.factory", MockDaoFactory.class.getName());
			DaoFactory.getInstance().init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
			mockUserDao.expectAndReturn("findAll", new ArrayList());
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	protected void tearDown() throws Exception {
		try {
			mockUserDao.verify();
			mainFrame.setVisible(false);
			getHelper().cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Component find (Class componentClass, String name) {
		NamedComponentFinder finder;
		finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component '" + name + "'", component);
		return component;
	}
	
	public void testBrowseControls() {
		find(JPanel.class, BROWSE_PANEL);
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(NUMBER_OF_COLUMNS_IN_USER_TABLE, table.getColumnCount());
		assertEquals("ID", table.getColumnName(0));
		assertEquals("Имя", table.getColumnName(1));
		assertEquals("Фамилия", table.getColumnName(2));
		find(JButton.class, ADD_BUTTON);
		find(JButton.class, EDIT_BUTTON);
		find(JButton.class, DELETE_BUTTON);
		find(JButton.class, DETAILS_BUTTON);
	}
	
	public void testAddUser() {
		try {
			String firstName = FIRST_NAME;
			String lastName = LAST_NAME;
			Date now = DATE_OF_BIRTH;
			
			User user = new User(firstName, lastName, now);
			
			User expectedUser = new User(new Long(1), firstName, lastName, now);
			mockUserDao.expectAndReturn("create", user, expectedUser);
			
			ArrayList<User> users = new ArrayList<User>();
			users.add(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);
			
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			
			JButton addButton = (JButton) find(JButton.class, ADD_BUTTON);
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			
			find(JPanel.class, ADD_PANEL);
			
			JTextField firstNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD);
			JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD);
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, DATE_OF_BIRTH_FIELD);
			JButton okButton = (JButton) find(JButton.class, OK_BUTTON);
			find(JButton.class, CANCEL_BUTTON);
			
			
			getHelper().sendString(new StringEventData(this, firstNameField, firstName));
			getHelper().sendString(new StringEventData(this, lastNameField, lastName));
			DateFormat formatter = DateFormat.getDateInstance();
			String date = formatter.format(now);
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
			
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			
			find(JPanel.class, BROWSE_PANEL);
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(2, table.getRowCount());
		} catch (Exception e) {
            fail(e.toString());
        }		
	}
	
	public void testEditUser() {
        try {  
            find(JPanel.class, BROWSE_PANEL);

            User expectedUser = new User(new Long(1001), "Nikita", "Motko", DATE);
            mockUserDao.expect("update", expectedUser);
            List<User> users = new ArrayList<User>(this.users);
            users.add(expectedUser);

            mockUserDao.expectAndReturn("findAll", users);

            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            JButton editButton = (JButton) find(JButton.class, EDIT_BUTTON);
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, "editPanel");

            JTextField firstNameField = (JTextField) find(JTextField.class,
                    FIRST_NAME_FIELD);
            JTextField lastNameField = (JTextField) find(JTextField.class,
                    LAST_NAME_FIELD);
            
            getHelper().sendString(
                    new StringEventData(this, firstNameField, "1"));
            getHelper().sendString(
                    new StringEventData(this, lastNameField, "1"));

            JButton okButton = (JButton) find(JButton.class, OK_BUTTON);
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
            find(JPanel.class, BROWSE_PANEL);
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(2, table.getRowCount());
        	} catch (Exception e) {
            fail(e.toString());
        }
    }

  
  
  public void testDeleteUser() {
        try {

            User expectedUser = new User(new Long(1001), "George", "Bush", DATE);
            mockUserDao.expect("delete", expectedUser);
            List<User> users = new ArrayList<User>();
            mockUserDao.expectAndReturn("findAll", users);

            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            JButton deleteButton = (JButton) find(JButton.class, DELETE_BUTTON);
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
            
            find(JPanel.class, BROWSE_PANEL);
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(0, table.getRowCount());


        } catch (Exception e) {
            fail(e.toString());
        }
    }
  
  
   private void findDialog(String title) {
          JDialog dialog;
          DialogFinder dFinder = new DialogFinder(title);
          dialog = (JDialog) dFinder.find();
          assertNotNull("Could not find dialog '" + title + "'", dialog);
          getHelper();
          TestHelper.disposeWindow( dialog, this );
      }



  private void fillFields(String firstName, String lastName, Date dateOfBirth) {
    JTextField firtNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD);
    JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD);
    JTextField dateOfBirthField = (JTextField) find(JTextField.class, DATE_OF_BIRTH_FIELD);
    
    getHelper().sendString(new StringEventData(this, firtNameField, firstName));
    getHelper().sendString(new StringEventData(this, lastNameField, lastName));
    DateFormat formatter = DateFormat.getDateInstance();
    String date = formatter.format(dateOfBirth);
    getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
  }
  
}
