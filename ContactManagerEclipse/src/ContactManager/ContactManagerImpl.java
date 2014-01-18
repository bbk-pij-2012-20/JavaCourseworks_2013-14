package ContactManager;

import java.util.ArrayList;
import java.util.Calendar; 
import java.util.Collection;
import java.util.GregorianCalendar; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List; 
import java.util.Map;
import java.util.Map.Entry;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * This class implements the ContactManager which holds 
 * all meetings paired with their id numbers in hashmaps. 
 * It holds all contacts in a hashset. 
 * 
 * @author Shahin Zibaee 24th Jan 2014
 *
 */
public class ContactManagerImpl implements ContactManager {
	private 	Map<Integer,MeetingImpl> meetingMap = new HashMap<Integer,MeetingImpl>();
	private 	Map<Integer,FutureMeetingImpl> futureMeetingMap = new HashMap<Integer,FutureMeetingImpl>();
	private 	Map<Integer,PastMeetingImpl> pastMeetingMap = new HashMap<Integer,PastMeetingImpl>();
	private 	Map<Integer,ContactImpl> contactMap = null;
	private int counter = 0;
	private Set<Contact> contacts = null;
	private Calendar nowDate = Calendar.getInstance();

	public static void main(String[] args){}
	/**
	 * This constructs a ContactManager with no parameters.
	 *  
	 * It searches the current directory for an xml file  
	 * called "ContactManager.xml", reads it in and converts it 
	 * into an object of ContactManagerImpl. 
	 */
/*	public ContactManagerImpl() {
		XMLDecoder decode = null;
		try {
			decode = new XMLDecoder(
					new BufferedInputStream(
						new FileInputStream("ContactManager.xml")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
//		Map<Integer,MeetingImpl> idAndMeetings = (Map<Integer,MeetingImpl>) decode.readObject();
		ContactManagerImpl contactManager = (ContactManagerImpl) decode.readObject();
		decode.close();

		update(contactManager.contacts,contactManager.futureMeetingMap);
	}
*/	
	private void update(Set<Contact> contacts,Map<Integer,FutureMeetingImpl> futureMeetingMap) {
		updateContacts(contacts);
		updateMeetings(futureMeetingMap);
	}

	private void updateContacts(Set<Contact> contacts) {
		this.contacts = new HashSet<>(contacts);
	}
	
	private void updateMeetings(Map<Integer,FutureMeetingImpl> futureMeetingMap) {
		for (MeetingImpl meeting : futureMeetingMap.values()) {
		    if (nowDate.after(meeting.getDate())) {
		    		PastMeetingImpl pastMeeting = (PastMeetingImpl) meeting;
		    		pastMeetingMap.put(pastMeeting.getId(),pastMeeting);
		    		futureMeetingMap.remove(pastMeeting.getId());		    		
		    }
		}
	}

	@Override
	public int addFutureMeeting(Set<Contact> contacts,Calendar date) {
		MeetingImpl meeting = null;
		Map<Integer,MeetingImpl> meetingMap = null;
		try {
			if (nowDate.after(date)) {
				throw new IllegalArgumentException();
			}
			
			meeting = new FutureMeetingImpl(contacts,date);
			meetingMap = new HashMap<Integer,MeetingImpl>();
			
			String dateStr = ""+date.getTime();
			new MeetingImpl(generateId(dateStr));
			
		} catch (IllegalArgumentException e) {
			System.out.println("can't set up a future meeting with a past date");
		}
		
		meetingMap.put(meeting.getId(),meeting);
		futureMeetingMap.put(meeting.getId(),(FutureMeetingImpl) meeting);
		
		return meeting.getId();		
	}
	/**
	 * Generates a unique id number for any string, 
	 * such as for a new contact or new meeting.
	 * 
	 * @param str	a contact or date in string format
	 * @return		a unique id number
	 */
	//this is private, temporarily made public for JUnit test to work	
	public int generateId(String str) {
		Long hashId = (long) Math.abs(str.hashCode());
		int hashIdInt = (int) (hashId%100000);
		counter++;
		String hashIdStr = ""+hashIdInt+counter;
		return Integer.parseInt(hashIdStr);
	}
	
	/**
	 * Get a past meeting with the unique
	 * @param id		unique id for a meeting 
	 * @return		a past meeting
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
		
		for (PastMeetingImpl pastMeeting : pastMeetingList) {
			if (!pastMeeting.getContacts().contains(contact)) {
				pastMeetingList.remove(pastMeeting);
			}
		}
		try {
			if (!contacts.contains(contact)) {
		}
			throw new IllegalArgumentException();
		} catch (IllegalArgumentException e) {
			System.out.println("that contact does not exist");
 		}
 		SortedSet<PastMeetingImpl> pastMeetingSet = new TreeSet<>(pastMeetingList); 
 	
 		return new ArrayList(pastMeetingSet);
	}
	 
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {			
		MeetingImpl meeting = null;
		Map<Integer,MeetingImpl> meetingMap = null;
		try {
			if (contacts == null || date == null || text == null) {
				throw new NullPointerException();
			}		
			if (contacts.isEmpty() || !this.contacts.contains(contacts)) {
				throw new IllegalArgumentException();
			}
			this.contacts.addAll(contacts);
			meeting = new PastMeetingImpl(contacts,date,text);
			meetingMap = new HashMap<Integer,MeetingImpl>();
			
			String dateStr = ""+date.getTime();
			new MeetingImpl(generateId(dateStr));
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("either contact list is empty or it contains a contact that doesn't exist");
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("one or more of contacts/date/text is null");
		}
	
		meetingMap.put(meeting.getId(),meeting);
		pastMeetingMap.put(meeting.getId(),(PastMeetingImpl) meeting);
	}
	
	@Override
	public void addMeetingNotes(int id, String text) {
		Meeting meeting;
		try {
			meeting = meetingMap.get(id);
			if (nowDate.before(meeting.getDate())) {
				throw new IllegalArgumentException();
			}
			if (text == null) {
				throw new NullPointerException();
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println("can't add notes to meeting with future date");
		} catch (NullPointerException e) {
			System.out.println("can't add empty notes");			
		}
	}
	
	@Override
	public void addNewContact(String name, String notes) {
		try {
			if (name == null || notes == null) {
				throw new NullPointerException();
			}
			ContactImpl newContact = new ContactImpl(name,notes,generateId(name));
			MeetingImpl meeting = new MeetingImpl();
 			contacts.add(newContact);
			contactMap.put(generateId(name),newContact);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		Set<Contact> contacts = new HashSet<>();
		return contacts;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		Set<Contact> contacts = new HashSet<>();
		if (contacts.contains(name)) {
			
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
		updateMeetings();
		encode.writeObject(meetingMap);
		encode.close();

	}
}