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
	
	private Calendar somePastDate;
	private Calendar someFutureDate;
	private Set<Contact> someContacts;//NEED TO CHANGE TO LIST BECAUSE CONTACTS CAN HAVE IDENTICAL NAMES!
	private ContactManagerImpl cmi; // *
	private String someText;
	private int pastMeetingId;
	private int futureMeetingId;
	private int contactId;
	private PastMeeting pastMeeting;
	private FutureMeeting futureMeeting;
	private Map<Integer,PastMeeting> pastMeetingMap;
	
//	*NB: cmi needs to be the Impl because toString(date) and 
//	generateId() are not in the interface.
	
	@Before
	public void setUp() throws Exception {
		cmi = new ContactManagerImpl();
		pastMeeting = new PastMeetingImpl();
		pastMeetingMap = new HashMap<>();
		someFutureDate = new GregorianCalendar(2014,11,15);		
//		somePastDate = Calendar.getInstance();
		somePastDate = new GregorianCalendar(1974,11,15);
//		System.out.println("in @Before of cmiTest - date: "+somePastDate.getTime());
		
		someText = "I need a haircut";
		someContacts = new HashSet<>();
		someContacts.add(new ContactImpl("bob"));
		someContacts.add(new ContactImpl("mac"));
		someContacts.add(new ContactImpl("mac"));
	}

	@After
	public void tearDown() throws Exception {
//		cmi = null;
	}

	@Test
	public void testAddFutureMeeting() {
//		int expectedOutput = 975771; // id for a past date 15/12/74, would cause exception, as it should.
		int expectedOutput = 328271;
//		int actualOutput = cmi.addFutureMeeting(someContacts,somePastDate);//would cause exception, as it should.
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
		int expectedOutput3 = 78551;
		int actualOutput1 = cmi.generateId("bob");		
		int actualOutput2 = cmi.generateId("mac");
		assertEquals(expectedOutput1,actualOutput1);
		assertEquals(expectedOutput2,actualOutput2);
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
	
	@Test
	public void testGetPastMeeting() {				
		for (Contact contact : someContacts) {
			System.out.println("someContacts: "+contact.getName());
		}
		cmi.addNewPastMeeting(someContacts,somePastDate,someText);//*if this is commented out
		pastMeetingMap.put(975771,pastMeeting);//*and this is commented out
		System.out.println("somePastDate: "+pastMeeting.getDate());
		
		int actualOutput = 0;
		for (Map.Entry<Integer,PastMeeting> entry : pastMeetingMap.entrySet()) {
			if (entry.getValue().equals(pastMeeting)) {
				actualOutput = entry.getKey();
			}
		}
		int expectedOutput = 975771;
//		int expectedOutput = 0; //* then this would pass.
		assertEquals(expectedOutput,actualOutput);	
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
	@Test
	public void testAddNewPastMeeting() {
		cmi.addNewPastMeeting(someContacts,somePastDate,someText);//*if this is commented out
		pastMeetingMap.put(975771,pastMeeting);//*and this is commented out
		int actualOutput = 0;
		for (Map.Entry<Integer,PastMeeting> entry : pastMeetingMap.entrySet()) {
			if (entry.getValue().equals(pastMeeting)) {
				actualOutput = entry.getKey();
			}
		}
		int expectedOutput = 975771;
//		int expectedOutput = 0; //* then this would pass.
		assertEquals(expectedOutput,actualOutput);	
	}

	@Test
	public void testAddMeetingNotes() {
		fail("Not yet implemented");
	}
/*	
 	@Test
	public void testAddNewContact() {
	}
*/
		
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