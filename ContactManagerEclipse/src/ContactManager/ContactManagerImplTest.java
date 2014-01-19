package ContactManager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ContactManagerImplTest {
	
	Calendar someDate;
	Calendar nowDate;
	ContactManagerImpl cmi = null;
	Set<Contact> someContacts;
	int counter = 0;

	@Before
	public void setUp() throws Exception {
		cmi = new ContactManagerImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Before
	public void setUp2() throws Exception {
		someDate = new GregorianCalendar(15,12,2014);
		nowDate = Calendar.getInstance();
		someContacts = new HashSet<>();
		someContacts.add(new ContactImpl("bob"));
		someContacts.add(new ContactImpl("mac"));
		someContacts.add(new ContactImpl("mac"));		
	}
	/**
	 * test for addfutureMeeting, takes a set of contacts and a calendar date
	 * and generates unique id using 'generateId()' and date as a string.
	 */
	@Test
	public void testAddFutureMeeting() {		
		assertEquals(2,cmi.addFutureMeeting(someContacts, someDate));
	}
	
	/**
	 * Unfortunately had to resort to changing accessibility of generateId() to public.  
	 * (My failed attempt at using reflection to make private method generateId()  
	 * accessible to JUnit test is commented out). 
	 *
	 */
	@Test
	public void testGenerateId() {
/*		String parameters;
		Method method = ContactManagerImpl.getDeclaredMethod(cmi.generateId("bob"), parameters);/
		method.setAccessible(true);
		int result = method.invoke(cmi, argObjects);*/
		
		int expectedOutput1 = 977171;
		int expectedOutput2 = 78552;
		int expectedOutput3 = 78553;
		assertEquals(expectedOutput1,cmi.generateId("bob"));
		assertEquals(expectedOutput2,cmi.generateId("mac"));
		assertEquals(expectedOutput3,cmi.generateId("mac"));
	}
	
/*	@Test
	public void testGetPastMeeting() {
		ContactManagerImpl cmi = new ContactManagerImpl();
		PastMeetingImpl pmi = new PastMeetingImpl(contacts,date);
		assertEquals(pmi,cmi.getPastMeeting(-1));	
	}*/

/*	@Test
	public void testGetFutureMeeting() {
		ContactManagerImpl cmi = new ContactManagerImpl();
		FutureMeetingImpl fmi = new FutureMeetingImpl(contacts,date);
		assertEquals(fmi,cmi.getFutureMeeting(2));	
	}*/

/*	@Test
	public void testGetMeeting() {
		ContactManagerImpl cmi = new ContactManagerImpl();
		MeetingImpl mi = new MeetingImpl();
		assertEquals(mi,cmi.getMeeting(1));
	}*/

	/*	@Test
	public void testGetFutureMeetingListContactImpl() {
		ContactManagerImpl cmi = new ContactManagerImpl();
		FutureMeetingImpl fmi = new FutureMeetingImpl();
		assertEquals(mi,cmi.getFutureMeeting(4));
	} */

	@Test
	public void testGetFutureMeetingListCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPastMeetingList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewPastMeeting() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMeetingNotes() {
		fail("Not yet implemented");
	}

/*	@Before
	public void setUp() throws Exception {
		ContactManagerImpl cmi = new ContactManagerImpl();
		cmi.contactCounter = 0;
	}*/
/*	@Test
	public void testAddNewContact() {
		ContactImpl c1 = new ContactImpl();		
		String name = "Shahin";
		ContactManagerImpl cmi = new ContactManagerImpl();
		cmi.addNewContact(name,"hash");
		int contactCounter = 1;
		Long hashId = (long) Math.abs(name.hashCode());
		int hashIdInt = (int) (hashId%100000);
		String idStr = ""+hashIdInt+contactCounter;
		int idTest = Integer.parseInt(idStr);
		assertEquals(idTest,c1.getId());
	}*/

	@Before
	public void setUp12() throws Exception {
		contacts = new HashSet<>();
		contacts.add(new ContactImpl("bob"));
		contacts.add(new ContactImpl("mac"));
		contacts.add(new ContactImpl("mac"));	
		int id = 977171;
		int id = 78552;
		int id = 78553;
		
		
	@Test
	public void testGetContactsIntArray() {
		
		assertEquals(contacts,);
	}

	@Test
	public void testGetContactsString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFlush() {
		fail("Not yet implemented");
	}
}
