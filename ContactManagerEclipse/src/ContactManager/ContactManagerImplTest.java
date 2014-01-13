package ContactManager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ContactManagerImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddFutureMeeting() {
		Calendar date = new GregorianCalendar(15,12,2014);
		ContactManagerImpl cmi= new ContactManagerImpl();
		Set<Contact> contacts = new HashSet<>();
		contacts.add(new ContactImpl("shahin"));
		contacts.add(new ContactImpl("micky"));
		contacts.add(new ContactImpl("johnny"));
		assertEquals(2,cmi.addFutureMeeting(contacts, date));
	}

	@Test
	public void testGetPastMeeting() {
		ContactManagerImpl cmi = new ContactManagerImpl();
		PastMeetingImpl pmi = new PastMeetingImpl(contacts,date);
		assertEquals(pmi,cmi.getPastMeeting(-1));	
	}

	@Test
	public void testGetFutureMeeting() {
		ContactManagerImpl cmi = new ContactManagerImpl();
		FutureMeetingImpl fmi = new FutureMeetingImpl(contacts,date);
		assertEquals(fmi,cmi.getFutureMeeting(2));	
	}

	@Test
	public void testGetMeeting() {
		ContactManagerImpl cmi = new ContactManagerImpl();
		MeetingImpl mi = new MeetingImpl();
		assertEquals(mi,cmi.getMeeting(1));
	}

	@Test
	public void testGetFutureMeetingListContactImpl() {
		ContactManagerImpl cmi = new ContactManagerImpl();
		FutureMeetingImpl fmi = new FutureMeetingImpl();
		assertEquals(mi,cmi.getFutureMeeting(4));
	}

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

	@Before
	public void setUp() throws Exception {
		ContactManagerImpl cmi = new ContactManagerImpl();
		cmi.contactCounter = 0;
	}
	@Test
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
	}

	@Test
	public void testGetContactsIntArray() {
		fail("Not yet implemented");
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
