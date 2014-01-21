package ContactManager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MeetingImplTest {

	private int id = 0;
	private Calendar date = Calendar.getInstance();
	private Set<Contact> contacts = null;
	private MeetingImpl meeting = null;
	
	@Before
	public void setUp() throws Exception {
		contacts = new HashSet<>();
		date.set(1974,11,15);
		contacts.add(new ContactImpl("Tom"));
		contacts.add(new ContactImpl("Jerry"));
		id = 2468;
		meeting = new MeetingImpl(contacts,date,id);
	}

	@After
	public void tearDown() throws Exception {
		contacts = null;
		meeting = null;
		id = 0;
		date = null;
	}

	@Test
	public void testGetId() {
		int expectedOutput = id;
		int actualOutput = meeting.getId();
		assertEquals(expectedOutput,actualOutput);
	}

	@Test
	public void testGetDate() {
		Calendar expectedOutput = date;
		Calendar actualOutput = meeting.getDate();
		assertEquals(expectedOutput,actualOutput);
	}

	@Test
	public void testGetContacts() {
		Set<Contact> expectedOutput = contacts;
		Set<Contact> actualOutput = meeting.getContacts();
		assertEquals(expectedOutput,actualOutput);
	}
}
