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
	
//	temporary access change for JUnit private Set<Contact> contacts = null;	
	public 	Map<Integer,PastMeeting> pastMeetingMap = null;

	private 	Map<Integer,Contact> contactMap = null;
	private int counter = 0;
	
//	temporary access change for JUnit private Set<Contact> contacts = null;
	public Set<Contact> contacts = null;

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
	
	/**
	 * Updates meetings from future to past, according to present time/date.
	 * 
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
	 * This private method converts the date of a new meeting to a string. 
	 * 
	 * @param	date		the date of a new meeting
	 * @return			the date of a new meeting as a string
	 * 
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
			System.out.println("The input date to toString() was null.");
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

			String hashIdStr = null;
			boolean unique = false;
			
			while (!unique) {
				hashIdStr = ""+hashId+counter;//counter = 0
				hashId = Integer.parseInt(hashIdStr);
			
				if (!contactMap.containsKey(hashId) || !meetingMap.containsKey(hashId)) {
					unique = true;					
				} else {
					counter++;
//					System.out.println("counter inside: "+counter);
				}
			}
//			System.out.println("counter outside: "+counter);

		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("The input string to generateId() was null.");
		}
//		System.out.println("counter before return: "+counter);

		return hashId;
	}
	
	/**
	 * Determines whether a contact exists in current Contact Manager. 
	 * 
	 * @param	contact	a contact
	 * @return			true if contact exists
	 * 
	 * (temporarily public for JUnit to access it.)
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
	 * Determines whether a contact is amongst the contacts for a meeting.
	 *  
	 * @param meetingContacts
	 * @param contact
	 * @return
	 */
	private boolean contains(Set<Contact> contacts, Contact contact) {
		boolean contains = false;
		for (Contact c : contacts) {
			if (c.getId() == contact.getId()) {
				contains = true;
			}
		}
		return contains;
	}

	/**
	 * I am interpreting the Javadoc of addFutureMeeting() interface stub 
	 * to mean that this method is not responsible for adding new contacts 
	 * passed here as an argument. It appears to be assumed that future 
	 * meetings will not be set up before contacts are added via 
	 * addNewContact(Contact). 
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
			System.out.println("Past date not valid OR contact(s) not found.");
		}
		
		return meeting.getId();		
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
		Meeting result = null;
		
		try {
		
			if (nowDate.before(getMeeting(id).getDate())) {
				throw new IllegalArgumentException();
			}
			
			if (id != 0) {
				result = meetingMap.get(id);
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println("can't get past meeting with id of future meeting");
		}
		
		return (PastMeeting) result;  
	}

	public FutureMeeting getFutureMeeting(int id) {
		Meeting result = null;
		
		try {

			if (nowDate.after(getMeeting(id).getDate())) {
				throw new IllegalArgumentException();
			}
				
			result = futureMeetingMap.get(id);
			
		} catch (IllegalArgumentException e) {
			System.out.println("Can't get future meeting with id of past meeting.");
		}	

		return (FutureMeeting) result;
	}
	
	@Override
	public Meeting getMeeting(int id) {
		return meetingMap.get(id);	
	}
	
	@Override 
	public List<Meeting> getFutureMeetingList(Contact contact) {
		SortedSet<Meeting> meetingSet = null; 
	
		try {
			if (!exists(contact)) { 
				throw new IllegalArgumentException();
			}
		
			meetingSet = new TreeSet<>();
			
			for (FutureMeeting futureMeeting : futureMeetingMap.values()) {
				if (contains(futureMeeting.getContacts(),contact)) {
					meetingSet.add(futureMeeting);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("That contact does not exist.");
 		}	

		return new ArrayList<>(meetingSet);	
	}
		
	public List<Meeting> getFutureMeetingList(Calendar date) {
		SortedSet<Meeting> meetingSet = new TreeSet<>(); 
		
		for (FutureMeeting futureMeeting : futureMeetingMap.values()) {
			if (futureMeeting.getDate().equals(date)) {
				meetingSet.add(futureMeeting);
			}
		}
		
		return new ArrayList<Meeting>(meetingSet);	
	}
	
/*	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		List<PastMeeting> pastMeetingList = (ArrayList<PastMeeting>) pastMeetingMap.values();
		SortedSet<PastMeeting> pastMeetingSet = null;
		try {
			if (!contacts.(contact)) {
				throw new IllegalArgumentException();
			}
		
			for (PastMeeting pastMeeting : pastMeetingList) {
				if (!pastMeeting.getContacts().(contact)) {
					pastMeetingList.remove(pastMeeting);
				}
			}

			pastMeetingSet = new TreeSet<>(pastMeetingList); 
			 
		} catch (IllegalArgumentException e) {
			System.out.println("that contact does not exist");
 		}
 			
 		return new ArrayList(pastMeetingSet);
	}*/
	/**
	 * This method is a little odd in that it creates a NEW past meeting which 
	 * requires you to give it contacts but these contacts need to have already 
	 * be in the records. i.e. they should already be in the member field of 
	 * this class in the Set<Contact> contacts, so for the JUnit, the contacts 
	 * need to be added first.
	 */
	
	@Override
	public void addNewPastMeeting(Set<Contact> contacts,Calendar date,String text) {			
		contacts =  (Set<ContactImpl); 
		
		Meeting meeting = null;
		try {
			if (contacts == null || date == null || text == null) {
				throw new NullPointerException();
			}

			if (contacts.isEmpty()) {
				throw new IllegalArgumentException();
			}
/*			
			this.contacts = (ContactImpl) 
				
			}
			this.contacts.addAll(contacts);

			meeting = new PastMeetingImpl(contacts,date,text,generateId(toString(date)));
			pastMeetingMap.put(meeting.getId(),(PastMeeting) meeting);
			meetingMap.put(meeting.getId(),meeting);		
*/					
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
			if (!meetingMap.Key(id)) {
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
				if (!contactMap.Key(ids[i])) {
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
 *	like () to search for the string within the name.
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