package ContactManager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This JUnit class tests the one method of PastMeetingImpl as 
 * well as the constructor chain to MeetingImpl.
 * 
 * @author Shahin Zibaee 21.01.13 
 *
 */
public class PastMeetingImplTest {

	private int id = 0;
	private Calendar date = Calendar.getInstance();
	private Set<Contact> contacts = null;
	private Meeting meeting;

	private String notes = "";
	private PastMeeting pastMeeting = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		contacts = new HashSet<>();
		date.set(1974,11,15);
		contacts.add(new ContactImpl("Tom"));
		contacts.add(new ContactImpl("Jerry"));
		id = 2468;
	
		notes = "are fighting again";
		pastMeeting = new PastMeetingImpl(contacts,date,notes,id);
		meeting = (MeetingImpl) pastMeeting;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pastMeeting = null;
		meeting = null;
	}

	/**
	 * Test method for {@link ContactManager.PastMeetingImpl#getNotes()}.
	 */
	@Test
	public void testGetIdpassedUpConstructorToMeetingImpl() {
		int expectedOutput = id;
		int actualOutput = meeting.getId();
		assertEquals(expectedOutput,actualOutput);
	}

	@Test
	public void testGetNotes() {
		String expectedOutput = notes;
		String actualOutput = pastMeeting.getNotes();
		assertEquals(expectedOutput,actualOutput);
	}
}