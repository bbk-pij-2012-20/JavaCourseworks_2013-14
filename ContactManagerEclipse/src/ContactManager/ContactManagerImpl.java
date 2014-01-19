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
	private 	Map<Integer,MeetingImpl> meetingMap = null;
	private 	Map<Integer,FutureMeetingImpl> futureMeetingMap = null; 
	private 	Map<Integer,PastMeetingImpl> pastMeetingMap = null;
	private 	Map<Integer,ContactImpl> contactMap = null;
	private int counter = 0;
	private Set<Contact> contacts = null;
	private Calendar nowDate = Calendar.getInstance();
	
	/*
	 * Temporary psvm placed here to stop Eclipse complaining!
	 */
//	public static void main(String[] args){}
	
	/**
	 * An empty constructor.
	 */
	public ContactManagerImpl(){}
	
	/**
	 * This constructs a ContactManager with no parameters.
	 * It reads an xml file called "ContactManager.xml" from 
	 * the current directory, reads it in and converts it 
	 * into an object of ContactManagerImpl. 
	 * 
	 */
	public ContactManagerImpl(String filename) {
		final String FILENAME = filename;
		XMLDecoder decode = null;
		try {
			decode = new XMLDecoder(
					new BufferedInputStream(
						new FileInputStream(FILENAME)));		
		
			meetingMap = new HashMap<Integer,MeetingImpl>();
			futureMeetingMap = new HashMap<Integer,FutureMeetingImpl>();
			pastMeetingMap = new HashMap<Integer,PastMeetingImpl>();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ContactManagerImpl contactManager = (ContactManagerImpl) decode.readObject();
		decode.close();

		update(contactManager.contacts,contactManager.futureMeetingMap);
	}
	
	/**
	 * Private method to update the contact manager object with the values read in 
	 * from the xml file. 
	 * 
	 * @param contacts			all contacts from all meetings 
	 * @param futureMeetingMap	future meetings paired with their unique id number
	 * 
	 */
	private void update(Set<Contact> contacts,Map<Integer,FutureMeetingImpl> futureMeetingMap) {
		updateContacts(contacts);
		updateMeetings(futureMeetingMap);
	}
	
	/**
	 * Private method to update the full set of all contacts read in from the xml file.
	 *
	 * @param contacts	all contacts from all meetings
	 * 
	 */
	private void updateContacts(Set<Contact> contacts) {
		this.contacts = new HashSet<>(contacts);
	}
	
	/**
	 * Private method to update the file to move meetings that are now in the past
	 * from future meetings to past meetings based on the current date.
	 *  
	 * @param futureMeetingMap	future meetings paired with their unique id number
	 * 
	 */
	private void updateMeetings(Map<Integer,FutureMeetingImpl> futureMeetingMap) {
		for (MeetingImpl meeting : futureMeetingMap.values()) {
		    if (nowDate.after(meeting.getDate())) {
		    		PastMeetingImpl pastMeeting = (PastMeetingImpl) meeting;
		    		pastMeetingMap.put(pastMeeting.getId(),pastMeeting);
		    		futureMeetingMap.remove(pastMeeting.getId());		    		
		    }
		}
	}
	
	/**
	 * Creates a new meeting with a new id number, and further stores
	 * id as key and meeting as the value in a HashMap.
	 * 
	 * @param	contacts		the contacts who will attend the meeting
	 * @param	date			the date on which the meeting will be held
	 * @return	id			the unique id number, generated for this meeting
	 * @throws				IllegalArgumentException if date is not in the future.
	 * 
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts,Calendar date) {
		MeetingImpl meeting = null;
		try {
			if (nowDate.after(date)) {
				throw new IllegalArgumentException();
			}

			this.contacts.addAll(contacts);
			meeting = new MeetingImpl(contacts,date,generateId(toString(date)));
			meetingMap.put(meeting.getId(),meeting);
			futureMeetingMap.put(meeting.getId(),(FutureMeetingImpl) meeting);
			
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
	private String toString(Calendar date) {
		return ""+date.get(Calendar.YEAR)+date.get(Calendar.MONTH)+date.get(Calendar.DAY_OF_MONTH);		
	}
	
	/**
	 * Private method to generate unique id number from any string, 
	 * such as for a new contact or the date of a new meeting.
	 * 
	 * @param  str	a contact or date in string format
	 * @return		a unique id number
	 * @throws		NullPointerException if the string is null 
	 * 
	 */
	//temporarily made public for JUnit test to work	
	public int generateId(String str) {
		String hashIdStr = null;
		try {
			if (str == null) {
				throw new NullPointerException();
			}
			
			Long hashId = (long) Math.abs(str.hashCode());
			int hashIdInt = (int) (hashId%100000);
			counter++;
			hashIdStr = ""+hashIdInt+counter;
		
		} catch (NullPointerException e){
			System.out.println("the input was null");
		}
			return Integer.parseInt(hashIdStr);
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
		try {
			if (nowDate.before(pastMeetingMap.get(id).getDate())) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			System.out.println("can't get past meeting with id of future meeting");
		}	
		return pastMeetingMap.get(id);
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
		List<FutureMeetingImpl> futureMeetingList = (ArrayList<FutureMeetingImpl>) futureMeetingMap.values();
		for (FutureMeetingImpl futMeeting : futureMeetingList) {
			if (!futMeeting.getContacts().contains(contact)) {
				futureMeetingList.remove(futMeeting);
			}
		}
		try {
			if (!contacts.contains(contact)) {
			}
		
		throw new IllegalArgumentException();
		
		} catch (IllegalArgumentException e) {
			System.out.println("that contact does not exist");
 		}	
 		return new ArrayList(futureMeetingList);//requires couple of suppress warnings..?!	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<FutureMeetingImpl> futureMeetingList = (ArrayList<FutureMeetingImpl>) futureMeetingMap.values();
		
		for (FutureMeetingImpl futMeeting : futureMeetingList) {
			if (!futMeeting.getDate().equals(date)) {
				futureMeetingList.remove(futMeeting);
			}
		}
		SortedSet<FutureMeetingImpl> futureMeetingSet = new TreeSet<>(futureMeetingList); 
				
		return new ArrayList(futureMeetingSet);	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		List<PastMeetingImpl> pastMeetingList = (ArrayList<PastMeetingImpl>) pastMeetingMap.values();
		SortedSet<PastMeetingImpl> pastMeetingSet = null;
		try {
			if (!contacts.contains(contact)) {
				throw new IllegalArgumentException();
			}
		
			for (PastMeetingImpl pastMeeting : pastMeetingList) {
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
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {			
		PastMeetingImpl meeting = null;
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
		MeetingImpl meeting;
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
		ContactImpl newContact;
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
		Set<Contact> contacts = new HashSet<>();
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
		final String XMLFILENAME ="ContactManager.xml";

		XMLEncoder encode = null; 
		try {
			encode = new XMLEncoder(
				new BufferedOutputStream(
					new FileOutputStream(XMLFILENAME)));
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		encode.writeObject(new ContactManagerImpl());
		encode.close();
	}
}