package ContactManager;

import java.util.ArrayList;
import java.util.Calendar; 
import java.util.Collection;
import java.util.Collections;
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
 * I have written ContactManager, as a whole, on the assumptions 
 * that (a) the interface must be adhered to as much as possible; 
 * (b) the app will not be used for a very large number of contacts
 * and meetings, (i.e. only for a small organisation and only for 
 * a 3-5 years before perhaps being updated).
 * 
 * ~ 90 % of this code was written by trial & error and Eclipse's suggestions. 
 * ~ 10 % learned from Google searching/StackOverFlow. A bit of help from Oracle 
 * 'tutorials' and a few YouTube video tutorials. The XMLDecoder/Encoder code was
 * written by Keith Mannock. 
 * I wrote over 100 mini-programs to try things out (not submitted to GitHub).
 * ~ 50 % of code was re-written to pass JUnit tests which were written after 
 * the first attempts at the program. 
 * 
 * @author Shahin Zibaee 9th Feb 2014
 * 
 */
public class ContactManagerImpl implements ContactManager {
	
	private static final String XMLFILENAME = "contacts.xml";
	private Map<Integer,Meeting> meetingMap = null;
	private Map<Integer,FutureMeeting> futureMeetingMap = null; 
	private Map<Integer,PastMeeting> pastMeetingMap = null;
	private Map<Integer,Contact> contactMap = null;
	private int counter = 0;
	private Calendar nowDate = Calendar.getInstance();
	
/*	public static void main(String[] args) {	
	
	new ContactManagerImpl().flush();
	
	}
*/
	
	/* 
	 * This constructs a ContactManager with no parameters.
	 * It looks for "ContactManager.xml" file on current directory. 
	 * If not found, it makes one. If found, it reads it in from 
	 * the current directory and converts it into an object of 
	 * ContactManagerImpl. The object should consist of 4 hashmaps
	 * and an integer called counter. 
	 * 
	 * (Based on KJM example code.)   
	 */
	@SuppressWarnings("unchecked")
	public ContactManagerImpl() {
	
		if (!new File(XMLFILENAME).exists()) {
		
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
		
				System.out.println("File not found. (constructor)");
			
			}
		}
		
		updateMeetings();
		
	}
	
	/**
	 * This private method updates meetings from future to past, 
	 * according to present time/date. 
	 * (temporarily public for JUnit to access it.)
	 */
	private void updateMeetings() {
		
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
	 * @throws  
	 * (temporarily public for JUnit to access it.)
	 */
	private String toString(Calendar date) {
	
		String dateStr = null;
	
		try {
		
			if (date == null) {
	
				throw new NullPointerException();
			
			}
		
			if (date.get(Calendar.HOUR_OF_DAY) == 0 && date.get(Calendar.MINUTE) == 0) {
		
				throw new IllegalArgumentException();
			
			}	
			
			dateStr= ""+date.get(Calendar.YEAR)+date.get(Calendar.MONTH)+date.get(Calendar.DAY_OF_MONTH);
			
		} catch (NullPointerException e) {
		
			System.out.println("The input date was null. (Method: toString(Calendar)).");
		
		} catch (IllegalArgumentException e) {
	
			System.out.println("The input date did not specify the time, i.e. hour and/or minute. (Method: toString(Calendar)).");
		
		}
		
		return dateStr;
		
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
	private int generateId(String str) {
	
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

/* 
 * In order to reduce duplication of generateId() code for contactMap and meetingMap, 
 * the id numbers are going to be unique regardless of whether the id is for a 
 * contact or of a meeting. 
 */
				if (!contactMap.containsKey(potentiallyUniqueId) && !meetingMap.containsKey(potentiallyUniqueId)) {
		
					unique = true;					
				
				} else {
			
					counter++;
				
				}
			}
			
			hashId = potentiallyUniqueId;

		} catch (NullPointerException e) {
	
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
	private boolean exists(Contact contact) {
	
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
	private boolean contains(Set<Contact> contacts, Contact contact) {
		
		boolean contains = false;
	
		for (Contact kontact : contacts) {
		
			if (kontact.getId() == contact.getId()) {
	
				contains = true;
			
			}		
		}
		
		return contains;
		
	}
	
	/**
	 * This private method determines whether a query date is on the same day as a meeting date.  
	 * Written for the three getMeetingList() methods to use, based on the my design choice that
	 * meetings in ContactManager include time (hh:mm), but that the getMeetingList() methods
	 * will restrict a user request a list of meetings on a particular day rather than at a 
	 * particular time as well.
	 *  
	 * @param meetings	a list of meetings
	 * @param meeting	a meeting
	 * @return			a list of chronologically ordered meetings
	 * (Made temporarily public for JUnit to access it.)
	 */
	private boolean onSameDay(Calendar queryDate,Calendar meetingDate) {
	
		boolean sameDay = false;
		
		if (queryDate.get(Calendar.YEAR) == meetingDate.get(Calendar.YEAR)) {
	
			if (queryDate.get(Calendar.MONTH) == meetingDate.get(Calendar.MONTH)) {
		
				if (queryDate.get(Calendar.DAY_OF_MONTH) == meetingDate.get(Calendar.DAY_OF_MONTH)) {
			
					sameDay = true;
				
				}		
			}
		}
		
		return sameDay;
	}
	
	/**
	 * This private method orders a list of meetings, chronologically.  
	 *  
	 * @param meetings	a list of meetings
	 * @param meeting	a meeting
	 * @return			a list of chronologically ordered meetings
	 * (Made temporarily public for JUnit to access it.)
	 */
	private <T extends Meeting> List<T> addSort(List<T> meetings,T meeting) {
		
		List<T> orderedList = meetings;
				
		int i=0;
	
		while (i<meetings.size() && meeting.getDate().after(meetings.get(i).getDate())) {
	
			i++;
		
		}
		
		orderedList.add(i,meeting);
		
		return orderedList;
	}
	
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
	
		List<Meeting> meetings = null;

		try {
			
			if (!exists(contact)) { 
	
				throw new IllegalArgumentException();
			
			}
		
			meetings = new ArrayList<>();
			
			for (Meeting meeting : futureMeetingMap.values()) {
			
				if (contains(meeting.getContacts(),contact)) {
				
					if (meetings.isEmpty()) {
					
						meetings.add(meeting);
				
					} else {
					
						meetings = addSort(meetings, meeting);
				
					}
				}	
			}
			
		} catch (IllegalArgumentException e) {
	
			System.out.println("That contact does not exist. (Method: getFutureMeetingList()).");
 		
		}	
				
		return meetings;
		
	}
	
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
	
		List<Meeting> meetings = new ArrayList<>();
		
		for (Meeting meeting : futureMeetingMap.values()) {
			
			if (onSameDay(date, meeting.getDate())) {
				
				if (meetings.isEmpty()) {
				
					meetings.add(meeting);
				
				} else {
				
					meetings = addSort(meetings, meeting);
				
				}
			}
		}

		return meetings;
	
	}
	
	@Override 
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		
		List<PastMeeting> pastMeetings = null;

		try {
			
			if (!exists(contact)) { 
	
				throw new IllegalArgumentException();
			
			}
		
			pastMeetings = new ArrayList<>();
			
			
			for (PastMeeting pastMeeting : pastMeetingMap.values()) {
			
				if (contains(pastMeeting.getContacts(),contact)) {
				
					if (pastMeetings.isEmpty()) {
					
						pastMeetings.add(pastMeeting);
				
					} else {
					
						pastMeetings = addSort(pastMeetings, pastMeeting);
				
					}
				}	
			}
			
		} catch (IllegalArgumentException e) {
		
			System.out.println("That contact does not exist. (Method: getPastMeetingList()).");
 		
		}	
		
		return pastMeetings;
	
	}	

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
		
		String newNotes = "";
		PastMeeting sameMeetingNewNotes = null;
		
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
			
			for (PastMeeting meeting : pastMeetingMap.values()) {
				
				if (meeting.getId() == id) {
			
					newNotes = meeting.getNotes() + "; " + text;
					sameMeetingNewNotes = new PastMeetingImpl(meeting.getContacts(),meeting.getDate(),newNotes,id);
					pastMeetingMap.put(id, sameMeetingNewNotes);
				
				}
			}
			
		} catch (IllegalArgumentException e) {
	
			System.out.println("A meeting with that id does not exist. (Method: addMeetingNotes).");
		
		} catch (IllegalStateException e) {
	
			System.out.println("Cannot add notes to a meeting that has not yet happened. (Method: addMeetingNotes).");
		
		} catch (NullPointerException e) {
	
			System.out.println("Cannot add empty notes. (Method: addMeetingNotes).");			
		
		}
	}
	
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
				
				contacts.add(contactMap.get(ids[i])); 			

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
		
			System.out.println("No name or string was input. (Method: getContacts(String))");
	
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
	
			System.out.println("File not found. (Method: flush())");

		}
		
		encode.close();// do I need this close() either ???

	}
}