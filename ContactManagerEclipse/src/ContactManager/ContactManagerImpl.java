package ContactManager;

import java.util.ArrayList;
import java.util.Calendar; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.List; 
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
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
 * @author Shahin Zibaee 24th Jan 2014
 *
 */
public class ContactManagerImpl implements ContactManager {
	private static final String XMLFILENAME = "contacts.xml";
	private 	Map<Integer,Meeting> meetingMap = null;
	private 	Map<Integer,FutureMeeting> futureMeetingMap = null; 
	private 	Map<Integer,PastMeeting> pastMeetingMap = null;
	private 	Map<Integer,Contact> contactMap = null;
	private int counter = 0;
	private Set<Contact> contacts = null;
	private Calendar nowDate = Calendar.getInstance();
	
	/**
	 * This constructs a ContactManager with no parameters.
	 * It reads an xml file called "ContactManager.xml" from 
	 * the current directory, reads it in and converts it 
	 * into an object of ContactManagerImpl.  
	 */
	@SuppressWarnings("unchecked")
	public ContactManagerImpl() {
		if (!new File(XMLFILENAME).exists()) {
			meetingMap = new HashMap<Integer,Meeting>();
			futureMeetingMap = new HashMap<Integer,FutureMeeting>();
			pastMeetingMap = new HashMap<Integer,PastMeeting>();
			contacts = new HashSet<>();
			contactMap = new HashMap<Integer,Contact>();
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
				contacts = (Set<Contact>) decode.readObject();
					
				decode.close();// do I need the close() at all ...???

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		updateMeetings();
	}
	
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
	 * Creates a new meeting with a new id number which is stored with 
	 * its meeting as a key-value pair in a HashMap.
	 * 
	 * @param	contacts		the contacts who will attend the meeting
	 * @param	date			the date on which the meeting will be held
	 * @return	id			the unique id number, generated for this meeting
	 * @throws				IllegalArgumentException if date is not in the future.
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts,Calendar date) {
		FutureMeeting meeting = null;
		try {
			if (nowDate.after(date)) {
				throw new IllegalArgumentException();
			}

			this.contacts.addAll(contacts);
			meeting = new FutureMeetingImpl(contacts,date,generateId(toString(date)));
			meetingMap.put(meeting.getId(),meeting);
			futureMeetingMap.put(meeting.getId(),meeting);
			
		} catch (IllegalArgumentException e) {
			System.out.println("can't set up a future meeting with a past date");
		}

		return meeting.getId();		
	}
	
	/**
	 * This private method converts the date of a new meeting to a string. 
	 * 
	 * @param	date		the date of a new meeting
	 * @return			the date of a new meeting as a string
	 * 
	 */
	public String toString(Calendar date) {
		String DateStr = null;
		try {
			if (date == null) {
				throw new NullPointerException();
			}
			
			DateStr = ""+date.get(Calendar.YEAR)+date.get(Calendar.MONTH)+date.get(Calendar.DAY_OF_MONTH);
	
		} catch (NullPointerException e) {
			System.out.println("the input date to toString() was null");
		}
		return DateStr;		
	}
	
	/**
	 * Private method to generate unique id number from any string, 
	 * such as for a new contact or the date of a new meeting.
	 * 
	 * @param  str	a contact or date in string format
	 * @return		a unique id number
	 * @throws		NullPointerException if the string is null 
	 * 
	 * (needs to made temporarily public for JUnit to access it.)
	 */
	public int generateId(String str) {
		int hashId = 0;
		try {
			if (str == null) {
				throw new NullPointerException();
			}

			Long hashIdLong = (long) Math.abs(str.hashCode());
			hashId = (int) (hashIdLong%100000);

			String hashIdStr = null;
			boolean unique = false;
			
			while (!unique) {
				hashIdStr = ""+hashId+counter;//counter = 0
				hashId = Integer.parseInt(hashIdStr);
			
				if (!contactMap.containsKey(hashId) || !meetingMap.containsKey(hashId)) {
					unique = true;					
				} else {
					counter++;
					System.out.println("counter inside: "+counter);
				}
			}
			System.out.println("counter outside: "+counter);

		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("the input string to generateId() was null");
		}
		System.out.println("counter before return: "+counter);

		return hashId;
	}
	
	/**
	 * Get a past meeting with the unique.
	 * 
	 * @param	id	unique id for a meeting 
	 * @return		a past meeting
	 * 
	 */
	@Override
	public PastMeeting getPastMeeting(int id) {
		PastMeeting pastMeeting = null;
		try {
			if (id == 0) {
				pastMeeting = null;
			} else {
				pastMeeting = pastMeetingMap.get(id);
				if (nowDate.before(pastMeeting.getDate())) {
					throw new IllegalArgumentException();
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("can't get past meeting with id of future meeting");
		}	
		return pastMeeting;
	}

	/**
	 * Private method to generate unique id number from any string, 
	 * such as for a new contact or the date of a new meeting.
	 * 
	 * @param  id	the id number for a meeting
	 * @return		the future meeting 
	 * @throws		IllegalArgumentException if the id is for a past meeting 
	 * 
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		try {
			if (nowDate.after(futureMeetingMap.get(id).getDate())) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			System.out.println("can't get future meeting with id of past meeting");
		}	
		return futureMeetingMap.get(id);
	}
	
	/**
	 * Private method to generate unique id number from any string, 
	 * such as for a new contact or the date of a new meeting.
	 * 
	 * @param  id	the id number for a meeting
	 * @return		the future meeting 
	 * @throws		IllegalArgumentException if the id is for a past meeting 
	 * 
	 */	
	@Override
	public Meeting getMeeting(int id) {
		return meetingMap.get(id);	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override 
	public List<Meeting> getFutureMeetingList(Contact contact) {
		List<FutureMeeting> futureMeetingList = (ArrayList<FutureMeeting>) futureMeetingMap.values();
		for (FutureMeeting futMeeting : futureMeetingList) {
			if (!futMeeting.getContacts().contains(contact)) {
//if contains() uses equals(), could override it to compare ids rather than if the objects have the same reference?
//but what what other methods would this affect? Would it for eg alter the containsKey() method or others?				
				futureMeetingList.remove(futMeeting);
			}
		}
		try {
			if (!contacts.contains(contact)) { 
				throw new IllegalArgumentException();
			}
	
		} catch (IllegalArgumentException e) {
			System.out.println("that contact does not exist");
 		}	
 		return new ArrayList(futureMeetingList);//requires couple of suppress warnings..?!	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<FutureMeeting> futureMeetingList = (ArrayList<FutureMeeting>) futureMeetingMap.values();
		
		for (FutureMeeting futMeeting : futureMeetingList) {
			if (!futMeeting.getDate().equals(date)) {
				futureMeetingList.remove(futMeeting);
			}
		}
		SortedSet<FutureMeeting> futureMeetingSet = new TreeSet<>(futureMeetingList); 
				
		return new ArrayList(futureMeetingSet);	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		List<PastMeeting> pastMeetingList = (ArrayList<PastMeeting>) pastMeetingMap.values();
		SortedSet<PastMeeting> pastMeetingSet = null;
		try {
			if (!contacts.contains(contact)) {
				throw new IllegalArgumentException();
			}
		
			for (PastMeeting pastMeeting : pastMeetingList) {
				if (!pastMeeting.getContacts().contains(contact)) {
					pastMeetingList.remove(pastMeeting);
				}
			}

			pastMeetingSet = new TreeSet<>(pastMeetingList); 
			 
		} catch (IllegalArgumentException e) {
			System.out.println("that contact does not exist");
 		}
 			
 		return new ArrayList(pastMeetingSet);
	}
	 
	@Override
	public void addNewPastMeeting(Set<Contact> contacts,Calendar date,String text) {			
		PastMeeting meeting = null;
		try {
			if (contacts == null || date == null || text == null) {
				throw new NullPointerException();
			}		
			if (contacts.isEmpty() || !this.contacts.contains(contacts)) {
				throw new IllegalArgumentException();
			}
			
			this.contacts.addAll(contacts);
			meeting = new PastMeetingImpl(contacts,date,text,generateId(toString(date)));
			pastMeetingMap.put(meeting.getId(),meeting);
			meetingMap.put(meeting.getId(),meeting);		
					
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("Contact list is empty and/or one or more contacts don't exist.");
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("One or more of contacts/date/text is null.");
		}
	}
	
	@Override
	public void addMeetingNotes(int id, String text) {
		Meeting meeting;
		try {
			meeting = meetingMap.get(id);
			if (nowDate.before(meeting.getDate())) {
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
			System.out.println("a meeting with that id does not exist");
		} catch (IllegalStateException e) {
			System.out.println("can't add notes to meeting with future date");
		} catch (NullPointerException e) {
			System.out.println("can't add empty notes");			
		}
	}
	
	@Override
	public void addNewContact(String name, String notes) {
		Contact newContact;
		try {
			if (name == null || notes == null) {
				throw new NullPointerException();
			}
			
			newContact = new ContactImpl(name,notes,generateId(name));
			contacts.add(newContact);
			contactMap.put(generateId(name),newContact);
		
		} catch (Exception e) {
			System.out.println("no name or no notes were given");
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
			System.out.println("no contact(s) correspond(s) to that/those id number(s)");
		}
		return contacts;
	}

	@Override
	public Set<Contact> getContacts(String name) {
//		Set<Contact> contacts = new HashSet<>();
		
/*	I could iterate through all contacts list and convert 
 *	each name which is already an array of characters, 
 *	into a data structure that allows me to use a method 
 *	like contains() to search for the string within the name.
 *		for (int i=0;i<contacts)
 */
		try {
			if (name == null) {
				throw new NullPointerException();
			}
			for (Contact contact : this.contacts) {
				if (contact.getName().equals(name)) {
					contacts.add(contact);
				}
			}
		} catch (NullPointerException e) {
			System.out.println("you gave no name");
		}
		return contacts;
	}

	/**
	 * 
	 */
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
			encode.writeObject(contacts);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		encode.close();// do I need this close() either ???
	}
}