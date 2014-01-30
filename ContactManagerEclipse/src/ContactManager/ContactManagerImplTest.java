package ContactManager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class ContactManagerImplTest {
	
	private Calendar someFutureDate;
	private Calendar somePastDate;
	private Set<Contact> someContacts;
	private Set<Contact> someNonContacts;
	private String someText;
	private ContactManagerImpl cmi; 
	private Contact nonContact; 
//	*NB: cmi is ..Impl 'cause toString(date) and 
//	generateId() are not in the interface.
	
	@Before
	public void setUp() throws Exception {
		someFutureDate = new GregorianCalendar(2014,11,15);
		somePastDate = new GregorianCalendar(1974,11,15);
		someContacts = new HashSet<>();
		someNonContacts = new HashSet<>();
		someText = "new past meeting notes";
		
		cmi = new ContactManagerImpl();
		cmi.meetingMap = new HashMap<>();
		cmi.futureMeetingMap = new HashMap<>();
		cmi.pastMeetingMap = new HashMap<>();
		cmi.contactMap = new HashMap<>();		

		String name = "Bob John";
		String name2 = "Jon Bob";
		String notes = "yeah but no";
		String notes2 = "no but yeah";
		cmi.addNewContact(name,notes);
		cmi.addNewContact(name2,notes2);
		String name3 = "nobody";
		nonContact = new ContactImpl(name3);

		for (Contact contact : cmi.contactMap.values()) {
			someContacts.add(contact);
		}
	}
	
	@After
	public void tearDown() throws Exception {
		cmi = null;
	}
	
	/**
	 * This test gets the meeting after calling two other 
	 * methods: addNewContact() and addNewPastingMeeting().
	 * The test only passes when I comment out parts of the
	 * ContactManagerImpl constructor related to the file
	 * reading in. Instead the maps are initialised. 
	 */
	@Test
	public void testGetPastMeeting() {				
		cmi.counter = 0;
		cmi.addNewPastMeeting(someContacts,somePastDate,someText);
		String actualOutput = null;	
		for (Map.Entry<Integer,PastMeeting> entry : cmi.pastMeetingMap.entrySet()) {
			if (entry.getKey() == 975770) {
				Calendar date = cmi.getPastMeeting(entry.getKey()).getDate();
				actualOutput = ""+date.get(Calendar.YEAR)+date.get(Calendar.MONTH)+date.get(Calendar.DAY_OF_MONTH);
			}
		}
		String expectedOutput = "19741115";
		assertEquals(expectedOutput,actualOutput);	
	}

	@Test
	public void testAddFutureMeeting() {		
		cmi.counter = 0;
		someNonContacts.add(nonContact);
		
/*		int actualOutput = cmi.addFutureMeeting(someNonContacts,someFutureDate);
 *		int expectedOutput = 328270; // 328270 = id for 15/12/14 
 * 
 * The 2 lines above correctly throw an exception as one of the contacts not 
 * found in contactMap, it was added directly through the ContactImpl(String name) 
 * constructor rather than via addNewContact().
 */
 
/*		int actualOutput = cmi.addFutureMeeting(someContacts,somePastDate);
 *		int expectedOutput = 975770; // 975770 = id for 15/12/74
 * 
 * The 2 lines above correctly throw an exception as the date is in 
 * the past rather than in the future.
 */

		int expectedOutput = 328270; 
		int actualOutput = cmi.addFutureMeeting(someContacts,someFutureDate);
		assertEquals(expectedOutput,actualOutput);
	}
	
	/**
	 * Testing the private method generateId() in ContactManagerImpl with 
	 * name string as inputs. In order to test this method directly 
	 * (i.e. not via the addNewContact() or addNewMeeting() methods), the 
	 * visibility of the private method is changed and then the 
	 * generateId() method is first called and then the contact and key 
	 * are put in directly. This is to show that generateId() checks the 
	 * hashmaps for pre-existing id numbers, which prompt a counter increment.
	 */
	@Test
	public void testGenerateId() {		
		cmi.counter = 0;
		int expectedOutput1 = 977170;//"bob"
		int expectedOutput2 = 78550;//"mac"
		int expectedOutput3 = 78551;//"mac" again
		
		String name = "bob";
		String name2 = "Mac";
		String name3 = "mac";
		
		int actualOutput1 = cmi.generateId(name.toLowerCase());		
		cmi.contactMap.put(977170,new ContactImpl(name.toLowerCase()));
		int actualOutput2 = cmi.generateId(name2);
		cmi.contactMap.put(78550,new ContactImpl(name2.toLowerCase()));
		int actualOutput3 = cmi.generateId(name3.toLowerCase());//a 2nd mac
		
		assertEquals(expectedOutput1,actualOutput1);
		assertEquals(expectedOutput2,actualOutput2);
		assertEquals(expectedOutput3,actualOutput3);
	}
	
	/**
	 * Testing with name string as inputs. In order to test this method 
	 * directly (i.e. not via the addNewContact() or addNewMeeting() 
	 * methods), the visibility of the private method is changed and then 
	 * the generateId() method is first called and then the contact and 
	 * key are put in directly. This is to show that generateId() checks 
	 * the hashmaps for pre-existing id numbers, which prompt a counter 
	 * increment.
	 */
	@Test
	public void testGenerateId2() {
		cmi.counter = 0;
		int expectedOutput1 = 328270;//future date 2014,11,15
		int expectedOutput2 = 975770;//past date 1974,11,15
		int expectedOutput3 = 975771;//past date 1974,11,15 again
	
		int actualOutput1 = cmi.generateId(cmi.toString(someFutureDate));
		cmi.meetingMap.put(328270,new MeetingImpl());
		int actualOutput2 = cmi.generateId(cmi.toString(somePastDate));
		cmi.meetingMap.put(975770,new MeetingImpl());
		int actualOutput3 = cmi.generateId(cmi.toString(somePastDate));
		
		assertEquals(expectedOutput1,actualOutput1);
		assertEquals(expectedOutput2,actualOutput2);
		assertEquals(expectedOutput3,actualOutput3);
	}
	/**
	 * As with testGetPastMeeting(), this test relies on two other
	 * methods working in order to work and not throw an exception.
	 * In this case, addFutureMeeting(). As before addNewContact()
	 * is used (in the setUp()) to avoid throwing exception 
	 * "one or more contacts does not exist". 
	 */
	@Test
	public void testGetFutureMeeting() {
		cmi.counter = 0;
		cmi.addFutureMeeting(someContacts,someFutureDate);
		String actualOutput = null;	
		for (Entry<Integer, FutureMeeting> entry : cmi.futureMeetingMap.entrySet()) {
			if (entry.getKey() == 328270) {
				Calendar date = cmi.getFutureMeeting(entry.getKey()).getDate();
				actualOutput = ""+date.get(Calendar.YEAR)+date.get(Calendar.MONTH)+date.get(Calendar.DAY_OF_MONTH);
			}
		}
		String expectedOutput = "20141115";
		assertEquals(expectedOutput,actualOutput);	
	}

	@Test
	public void testGetMeeting() {
		cmi.counter = 0;
		cmi.addFutureMeeting(someContacts,someFutureDate);
		String actualOutput1 = null;	
		for (Entry<Integer, FutureMeeting> entry : cmi.futureMeetingMap.entrySet()) {
			if (entry.getKey() == 328270) {
				Calendar date = cmi.getMeeting(entry.getKey()).getDate();
				actualOutput1 = ""+date.get(Calendar.YEAR)+date.get(Calendar.MONTH)+date.get(Calendar.DAY_OF_MONTH);
			}
		}
		String expectedOutput1 = "20141115";
		assertEquals(expectedOutput1,actualOutput1);	
		
		cmi.counter = 0;
		cmi.addNewPastMeeting(someContacts,somePastDate,someText);
		String actualOutput2 = null;	
		for (Map.Entry<Integer,PastMeeting> entry : cmi.pastMeetingMap.entrySet()) {
			if (entry.getKey() == 975770) {
				Calendar date = cmi.getMeeting(entry.getKey()).getDate();
				actualOutput2 = ""+date.get(Calendar.YEAR)+date.get(Calendar.MONTH)+date.get(Calendar.DAY_OF_MONTH);
			}
		}
		String expectedOutput2 = "19741115";
		assertEquals(expectedOutput2,actualOutput2);	
	}

	/*	@Test
	public void testGetFutureMeetingListContactImpl() {
		
	} */

	@Test
	public void testGetFutureMeetingListCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPastMeetingList() {
		fail("Not yet implemented");
	}

	/**
	 * In order to test this method, it is first called,
	 * with some contacts, a past date and text.
	 * I know the past date produces an int Id of 975771. 
	 * So, in order to test for creation of the new meeting,
	 * partly confirmed by the creation of the id, the key-
	 * value pair is put in the map, and finally, the id 
	 * for the new meeting created is compared with the 
	 * expected id number (975771).
	 */
/*	@Test
	public void testAddNewPastMeeting() {
		cmi.addNewPastMeeting(someContacts,somePastDate,someText);//*if this is commented out
		cmi.pastMeetingMap.put(975771,(PastMeeting) meeting);//*and this is commented out
		int actualOutput = 0;
		for (Map.Entry<Integer,PastMeeting> entry : cmi.pastMeetingMap.entrySet()) {
			if (entry.getValue().equals(meeting)) {
				actualOutput = entry.getKey();
			}
		}
		int expectedOutput = 975771;
//		int expectedOutput = 0; //* then this would pass.
		assertEquals(expectedOutput,actualOutput);	
	}
*/
	@Test
	public void testAddMeetingNotes() {
		fail("Not yet implemented");
	}
	
	/*
	 * The test works when the accessibility of contactMap in
	 * ContactManagerImpl is temporarily changed to anything
	 * other than private.
	 */
 	@Test
	public void testAddNewContact() {
 		String name = "Bob John";//with counter=0, id = 499100;
 		String notes = "I dig a pigmy";
 		cmi.addNewContact(name,notes);
 		String expectedOutput = "Bob John";
 		String actualOutput = cmi.contactMap.get(499100).getName();
 		int actualOutput2 = 0;
 		for (Map.Entry<Integer,Contact> contact : cmi.contactMap.entrySet()) {
			if (contact.getValue().getName().equals("Bob John")) {
				actualOutput2 = contact.getKey();
			}
		}
 		int expectedOutput2 = 499100;
 		assertEquals(expectedOutput,actualOutput);
 		assertEquals(expectedOutput2,actualOutput2);
 	}

		
	@Test
	public void testGetContactsIntArray() {
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