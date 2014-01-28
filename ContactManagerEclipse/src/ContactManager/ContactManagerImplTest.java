package ContactManager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class ContactManagerImplTest {
	
	private Calendar someFutureDate;

	private Calendar somePastDate;
	private Set<Contact> someContacts;
	private String someText;

	private ContactManagerImpl cmi; 
	
	private Meeting meeting;
//	private Map<Integer,Contact> contactMap;
		
//	*NB: cmi needs to be the Impl because toString(date) and 
//	generateId() are not in the interface.
	
	@Before
	public void setUp() throws Exception {
		someFutureDate = new GregorianCalendar(2014,11,15);

		somePastDate = new GregorianCalendar(1974,11,15);
		someContacts = new HashSet<>();
//		someContacts.add(new ContactImpl("bob"));
//		someContacts.add(new ContactImpl("mac"));
//		someContacts.add(new ContactImpl("mac"));
		someText = "I need a haircut";
		
		cmi = new ContactManagerImpl();

		meeting = new PastMeetingImpl();
	
//		contactMap = new HashMap<>();
	
	}

	/**
	 * Having created a new past meeting with its id, these are held in the 
	 * pastMeetingMap. To get access to the meeting, I have to iterate through
	 * the pastMeetingMap and get the id number, which I can use to assign the 
	 * associated value to the expectedOutput. It seems a rather round-about 
	 * way of doing things but not sure how else to do this.
	 */
/*	@Test
	public void testGetPastMeeting() {				
		PastMeeting actualOutput = null;
		
		for (Contact contact : someContacts) {
			cmi.addNewContact(contact.getName(),someText);
		}
		for (Contact contact : cmi.contacts) {
			System.out.println("contacts not empty! :" +contact.getName()+", text: "+contact.getNotes());
		}
		cmi.addNewPastMeeting(someContacts,somePastDate,someText);
		
		for (Map.Entry<Integer,PastMeeting> entry : cmi.pastMeetingMap.entrySet()) {
			if (entry.getValue().equals(meeting)) {
				int id = entry.getKey();
				actualOutput = cmi.pastMeetingMap.get(id);
			}
		}

		PastMeeting expectedOutput = (PastMeeting) meeting;
		assertEquals(expectedOutput,actualOutput);	
	}
*/
	@After
	public void tearDown() throws Exception {
//		cmi = null;
	}

	@Test
	public void testAddFutureMeeting() {
//		int expectedOutput = 975771;//* is id for 15/12/74 - correctly causes exception.
//		int actualOutput = cmi.addFutureMeeting(someContacts,somePastDate);//*

		int expectedOutput = 328271;
		int actualOutput = cmi.addFutureMeeting(someContacts,someFutureDate);
		assertEquals(expectedOutput,actualOutput);
	}
	
	/**
	 * testing generateId() with name string inputs
	 */
	@Test
	public void testGenerateId() {		
		int expectedOutput1 = 977170;
		int expectedOutput2 = 78550;
//		int expectedOutput3 = 78551;
		int actualOutput1 = cmi.generateId("bob");		
		int actualOutput2 = cmi.generateId("mac");
//		int actualOutput3 = cmi.generateId("mac");//a 2nd mac
		assertEquals(expectedOutput1,actualOutput1);
		assertEquals(expectedOutput2,actualOutput2);
//		assertEquals(expectedOutput2,actualOutput2);
	}
	
	/**
	 * testing generateId() with Calendar date inputs
	 */
	@Test
	public void testGenerateId2() {
		int expectedOutput1 = 328271;//future date 2014,11,15
		int expectedOutput2 = 975771;//past date 1974,11,15
		System.out.println("toString(futureDate)"+cmi.toString(someFutureDate));
	
		int actualOutput1 = cmi.generateId(cmi.toString(someFutureDate));
		int actualOutput2 = cmi.generateId(cmi.toString(somePastDate));

		assertEquals(expectedOutput1,actualOutput1);
		assertEquals(expectedOutput2,actualOutput2);
	}
	
	

/*	@Test
	public void testGetFutureMeeting() {
		
	}*/

/*	@Test
	public void testGetMeeting() {
		
	}*/

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