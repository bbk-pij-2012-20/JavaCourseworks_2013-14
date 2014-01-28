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
	public 	Map<Integer,Contact> contactMap = null;
	private int counter = 0;
	private Calendar nowDate = Calendar.getInstance();
	
	public static void main(String[] args) {
		new ContactManagerImpl();
	}
	
	/**
	 * This constructs a ContactManager with no parameters.
	 * It looks for "ContactManager.xml" file on current directory. 
	 * If not found, it makes one. If found, it reads it in from 
	 * the current directory and converts it into an object of 
	 * ContactManagerImpl. The object should consist of   
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
				}
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("The input string to generateId() was null.");
		}

		return hashId;
	}
	
	/**
	 * Determines whether a contact exists in current Contact Manager. 
	 * 
	 * @param	contact	a contact
	 * @return			true if contact exists
	 * 
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
	 * Determines whether a contact is amongst the contacts for a meeting.
	 *  
	 * @param meetingContacts
	 * @param contact
	 * @return
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
			System.out.println("Past date not valid OR contact(s) not found.");
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
			System.out.println("Cannot get past meeting with id of future meeting.");
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
			System.out.println("Cannot get future meeting with id of past meeting.");
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
			
			meetingSet = new TreeSet<>();//not sure if this will be chronologically sorted yet need to test it.		
			
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
		
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		SortedSet<Meeting> meetingSet = new TreeSet<>(); 
		
		for (FutureMeeting futureMeeting : futureMeetingMap.values()) {
			
			if (futureMeeting.getDate().equals(date)) {
				meetingSet.add(futureMeeting);
			}
	
		}
		
		return new ArrayList<>(meetingSet);	
	}
	
	@Override 
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		SortedSet<PastMeeting> meetingSet = null; 
	
		try {
			
			if (!exists(contact)) { 
				throw new IllegalArgumentException();
			}
			
			meetingSet = new TreeSet<>();
			
			for (PastMeeting pastMeeting : pastMeetingMap.values()) {
			
				if (contains(pastMeeting.getContacts(),contact)) {
					meetingSet.add(pastMeeting);
				}
				
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println("That contact does not exist.");
 		}	
		
		return new ArrayList<>(meetingSet);
	}	
	
	/**
	 * As with addFutureMeeting, I am making the assumption that the user
	 * knows to add new contacts first, before adding new past meetings. 
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
			System.out.println("Contact list is empty and/or one or more contacts don't exist.");
		} catch (NullPointerException e) {
			System.out.println("One or more of contacts/date/text is null.");
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
			System.out.println("A meeting with that id does not exist.");
		} catch (IllegalStateException e) {
			System.out.println("Cannot add notes to a meeting that has not yet happened.");
		} catch (NullPointerException e) {
			System.out.println("Cannot add empty notes.");			
		}
	}
	
	/**
	 * I am assuming that this method is a suitable place for 
	 * generating a contacts unique id. 
	 */
	@Override
	public void addNewContact(String name, String notes) {		
		Contact contact = null;
		
		try {
		
			if (name == null || notes == null) {
				throw new NullPointerException();
			}
			
			contact = new ContactImpl(name,notes,generateId(name));
			contactMap.put(contact.getId(),contact);
		
		} catch (Exception e) {
			System.out.println("No name and/or no notes were given.");
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
			System.out.println("No contact(s) correspond(s) to that/those id number(s).");
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
			System.out.println("No name was given.");
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

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		encode.close();// do I need this close() either ???
	}
}