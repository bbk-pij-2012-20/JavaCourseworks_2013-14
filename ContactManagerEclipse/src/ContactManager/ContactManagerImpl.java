package ContactManager;

import java.util.ArrayList;
import java.util.Calendar; 
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List; 
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.lang.Exception;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * This class implements the ContactManager which holds 
 * all meetings paired with their id numbers in HashMaps. 
 * It holds all contacts in a HashSet. 
 * 
 * @author Shahin Zibaee 30th Jan 2014
 * 
 */
public class ContactManagerImpl implements ContactManager {
	private static final String XMLFILENAME = "contacts.xml";
	Map<Integer,Meeting> meetingMap = null;
	Map<Integer,FutureMeeting> futureMeetingMap = null; 
	Map<Integer,PastMeeting> pastMeetingMap = null;
 	Map<Integer,Contact> contactMap = null;
	int counter = 0;
	private Calendar nowDate = Calendar.getInstance();
	
//	public static void main(String[] args) {	
//		new ContactManagerImpl().flush();
//	}
	/**
	 * This constructs a ContactManager with no parameters.
	 * It looks for "ContactManager.xml" file on current directory. 
	 * If not found, it makes one. If found, it reads it in from 
	 * the current directory and converts it into an object of 
	 * ContactManagerImpl. The object should consist of 4 hashmaps
	 * and an integer called counter. 
	 * 
	 * (Based on KLM example code.)   
	 */
	@SuppressWarnings("unchecked")
	public ContactManagerImpl() {
	
/*		if (!new File(XMLFILENAME).exists()) {
			meetingMap = new HashMap<Integer,Meeting>();
			futureMeetingMap = new HashMap<Integer,FutureMeeting>();
			pastMeetingMap = new HashMap<Integer,PastMeeting>();
			contactMap = new HashMap<Integer,Contact>();
			counter = 0;
		} else {
			XMLDecoder decode = null;
		
			try {
				decode = new XMLDecoder(
							new BufferedInputStream(
								new FileInputStream(XMLFILENAME)));
				
				meetingMap = (Map<Integer,Meeting>) decode.readObject();
				futureMeetingMap = (Map<Integer,FutureMeeting>) decode.readObject();
				pastMeetingMap = (Map<Integer,PastMeeting>) decode.readObject();
				contactMap = (Map<Integer,Contact>) decode.readObject();
				counter = (int) decode.readObject();

			//	decode.close();// do I need the close() at all ...???

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("File not found. (constructor)");
			}
		}
		
		updateMeetings();
*/	}
	
	/**
	 * This private method updates meetings from future to past, 
	 * according to present time/date. 
	 * (temporarily public for JUnit to access it.)
	 */
	public void updateMeetings() {
		for (Meeting meeting : futureMeetingMap.values()) {
		
			if (nowDate.after(meeting.getDate())) {
		    		PastMeetingImpl pastMeeting = (PastMeetingImpl) meeting;
		    		pastMeetingMap.put(pastMeeting.getId(),pastMeeting);
		    		futureMeetingMap.remove(pastMeeting.getId());		    		
		    }
		}
	}
	
	/**
	 * This private method converts the date of a new meeting into a string. 
	 * 
	 * @param	date		the date of a new meeting
	 * @return			the date of a new meeting as a string 
	 * (temporarily public for JUnit to access it.)
	 */
	public String toString(Calendar date) {
		String DateStr = null;
	
		try {
		
			if (date == null) {
				throw new NullPointerException();
			}
			
			DateStr = ""+date.get(Calendar.YEAR)+date.get(Calendar.MONTH)+date.get(Calendar.DAY_OF_MONTH);
	
		} catch (NullPointerException e) {
			System.out.println("The input date to toString() was null (Method: toString(Calendar)).");
		}
		
		return DateStr;		
	}
	
	/**
	 * This private method generates a unique id number from any string, 
	 * such as for a new contact or the date of a new meeting.
	 * 
	 * @param  str	a contact or date in string format
	 * @return		a unique id number
	 * @throws		NullPointerException if the string is null 
	 * (temporarily public for JUnit to access it.)
	 */
	public int generateId(String str) {
		int hashId = 0;
		
		try {
		
			if (str == null) {
				throw new NullPointerException();
			}

			Long hashIdLong = (long) Math.abs(str.hashCode());
			hashId = (int) (hashIdLong%100000);
		
			boolean unique = false;
			int potentiallyUniqueId = 0;
			
			while (!unique) {
				potentiallyUniqueId = Integer.parseInt(""+hashId+counter);
				
				if (!contactMap.containsKey(potentiallyUniqueId) && !meetingMap.containsKey(potentiallyUniqueId)) {
					unique = true;					
				
				} else {
					counter++;
				}
			}
			
			hashId = potentiallyUniqueId;

		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("The input string to generateId() was null (Method: generateId()).");
		}
		
		return hashId;
	}
	
	/**
	 * This private method determines whether a contact exists in 
	 * the current Contact Manager. 
	 * 
	 * @param	contact	a contact
	 * @return			true if contact exists
	 * (Made temporarily public for JUnit to access it.)
	 */
	public boolean exists(Contact contact) {
		int id = contact.getId();
		boolean exists = false;
		
		for (int idKeys : contactMap.keySet()) {
	
			if (id == idKeys) {
				exists = true;
			}
			
		}
		
		return exists;
	}
	
	/**
	 * This private method determines whether a contact is amongst the 
	 * contacts for a meeting.
	 *  
	 * @param contacts	a list of contacts
	 * @param contact	a contact
	 * @return			true if the contact is in the list of contacts
	 * (Made temporarily public for JUnit to access it.)
	 */
	public boolean contains(Set<Contact> contacts, Contact contact) {
		boolean contains = false;
	
		for (Contact kontact : contacts) {
		
			if (kontact.getId() == contact.getId()) {
				contains = true;
			}
			
		}
		
		return contains;
	}
	
	/**
	 * I am interpreting the Javadoc of addFutureMeeting() interface stub 
	 * to mean that this method is not responsible for adding any new 
	 * contacts, passed here as an argument. 
	 * It is assumed, therefore, that future meetings will not be set up 
	 * before contacts are explicitly added via addNewContact(Contact). 
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts,Calendar date) {
		Meeting meeting = null;
	
		try {
			
			if (nowDate.after(date)) {
				throw new IllegalArgumentException();
			}
			
			for (Contact contact : contacts) {
				
				if (!exists(contact)) {
					throw new IllegalArgumentException("");
				}
				
			}
				
			meeting = new FutureMeetingImpl(contacts,date,generateId(toString(date)));
			meetingMap.put(meeting.getId(),meeting);
			futureMeetingMap.put(meeting.getId(),(FutureMeeting) meeting);
			
		} catch (IllegalArgumentException e) {
			System.out.println("Past date not valid OR contact(s) not found (Method: addFutureMeeting()).");
		}
		
		return meeting.getId();		
	}
	
	@Override
	public PastMeeting getPastMeeting(int id) {
		Meeting result = null;
		
		try {
		
			if (nowDate.before(getMeeting(id).getDate())) {
				throw new IllegalArgumentException();
			}
			
			if (id != 0) {
				result = meetingMap.get(id);
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println("Cannot get past meeting using the id of a future meeting. (Method: getPastMeeting()).");
		}
		
		return (PastMeeting) result;  
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		Meeting result = null;
		
		try {

			if (nowDate.after(getMeeting(id).getDate())) {
				throw new IllegalArgumentException();
			}
				
			result = futureMeetingMap.get(id);
			
		} catch (IllegalArgumentException e) {
			System.out.println("Cannot get future meeting using the id of a past meeting. (Method: getFutureMeeting()).");
		}	

		return (FutureMeeting) result;
	}
	
	@Override
	public Meeting getMeeting(int id) {
		return meetingMap.get(id);	
	}
	
	@Override 
	public List<Meeting> getFutureMeetingList(Contact contact) {
		SortedMap<Calendar,Meeting> meetingTreeMap = null; 
		List<Meeting> meetings = null;

		try {
			
			if (!exists(contact)) { 
				throw new IllegalArgumentException();
			}
		
			meetingTreeMap = new TreeMap<>();
			meetings = new ArrayList<>();
			
			for (Meeting meeting : futureMeetingMap.values()) {
			
				if (contains(meeting.getContacts(),contact)) {
					meetingTreeMap.put(meeting.getDate(),meeting);
				}
				
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println("That contact does not exist. (Method: getFutureMeetingList()).");
 		}	
		
		for (Meeting meeting : meetingTreeMap.values()) {
			meetings.add(meeting);
		}
		
		return meetings;
	}
		
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		SortedMap<Calendar,Meeting> meetingTreeMap = new TreeMap<>(); 
		List<Meeting> meetings = new ArrayList<>();

		for (Meeting meeting : futureMeetingMap.values()) {
			
			if (meeting.getDate().equals(date)) {
				meetingTreeMap.put(meeting.getDate(),meeting);
			}
	
		}
		
		for (Meeting meeting : meetingTreeMap.values()) {
			meetings.add(meeting);
		}
		
		return meetings;
	}
	
	@Override 
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		SortedMap<Calendar,PastMeeting> meetingTreeMap = null; 
		List<PastMeeting> pastMeetings = null;
		
		try {
			
			if (!exists(contact)) { 
				throw new IllegalArgumentException();
			}
			
			meetingTreeMap = new TreeMap<>();
			pastMeetings = new ArrayList<>();
			
			for (PastMeeting pastMeeting : pastMeetingMap.values()) {
			
				if (contains(pastMeeting.getContacts(),contact)) {
					meetingTreeMap.put(pastMeeting.getDate(),pastMeeting);
				}
				
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println("That contact does not exist. (Method: getPastMeetingList()).");
 		}	
	
		for (PastMeeting pastMeeting : meetingTreeMap.values()) {
			pastMeetings.add(pastMeeting);
		}
		
		return pastMeetings;
	}	
	
	/**
	 * As with addFutureMeeting, I am making the assumption that the user
	 * knows to add new contacts first via addNewContact() before adding 
	 * new past meetings. 
	 */
	@Override
	public void addNewPastMeeting(Set<Contact> contacts,Calendar date,String text) {				
		Meeting meeting = null;
	
		try {
			
			if (contacts == null || date == null || text == null) {
				throw new NullPointerException();
			}
			
			for (Contact contact : contacts) {
				
				if (!exists(contact)) {
					throw new IllegalArgumentException();
				}
				
			}
		
			meeting = new PastMeetingImpl(contacts,date,text,generateId(toString(date)));
			pastMeetingMap.put(meeting.getId(),(PastMeeting) meeting);
			meetingMap.put(meeting.getId(),meeting);		
					
		} catch (IllegalArgumentException e) {
			System.out.println("Contact list is empty and/or one or more contacts don't exist. (Method: addNewPastMeeting()).");
		} catch (NullPointerException e) {
			System.out.println("One or more of contacts/date/text is null. (Method: addNewPastMeeting()).");
		}
	}
	
	@Override
	public void addMeetingNotes(int id, String text) {		
		try {
			
			if (nowDate.before(meetingMap.get(id).getDate())) {
				throw new IllegalStateException();
			}
			
			if (!meetingMap.containsKey(id)) {
				throw new IllegalArgumentException();
			}
			
			if (text == null) {
				throw new NullPointerException();
			}			
			
			new PastMeetingImpl(text);
			
		} catch (IllegalArgumentException e) {
			System.out.println("A meeting with that id does not exist. (Method: addMeetingNotes).");
		} catch (IllegalStateException e) {
			System.out.println("Cannot add notes to a meeting that has not yet happened. (Method: addMeetingNotes).");
		} catch (NullPointerException e) {
			System.out.println("Cannot add empty notes. (Method: addMeetingNotes).");			
		}
	}
	
	/**
	 * I am assuming that this method is a suitable place for 
	 * generating a contact's unique id. 
	 */
	@Override
	public void addNewContact(String name, String notes) {		
		Contact contact = null;
		
		try {
			
			if (name == null || notes == null) {
				throw new NullPointerException();
			}
			
			contact = new ContactImpl(name,notes,generateId(name.toLowerCase()));
			contactMap.put(contact.getId(),contact);
		
		} catch (Exception e) {
			System.out.println("No name and/or no notes were given. (Method: addNewContact()");
		}
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		Set<Contact> contacts = new HashSet<>();
		
		try {
			
			for (int i=0;i<ids.length;i++) {
			
				if (!contactMap.containsKey(ids[i])) {
					throw new IllegalArgumentException();
				}
				
			} 
					
			for (int i=0;i<ids.length;i++) {
				contacts.add(contactMap.get(i)); 
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println("No contact(s) correspond(s) to that/those id number(s). (Method: getContacts(int.. ids)");
		}
		
		return contacts;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		Set<Contact> contacts = new HashSet<>();
		
		try {
			
			if (name == null) {
				throw new NullPointerException();
			}
			
			for (Contact contact : contactMap.values()) {
			
				if (contact.getName().toLowerCase().contains(name.toLowerCase())) {
					contacts.add(contact);
				}
				
			}
		} catch (NullPointerException e) {
			System.out.println("No name was given. (Method: getContacts(String))");
		}
		
		return contacts;
	}

	@Override
	public void flush() { 
		XMLEncoder encode = null; 
		
		try {
			encode = new XMLEncoder(
						new BufferedOutputStream(
							new FileOutputStream(XMLFILENAME)));

			encode.writeObject(meetingMap);
			encode.writeObject(futureMeetingMap);
			encode.writeObject(pastMeetingMap);
			encode.writeObject(contactMap);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found. (Method: flush())");
		}
		
		encode.close();// do I need this close() either ???
	}
}