package ContactManager;

import java.util.ArrayList;
import java.util.Calendar; 
import java.util.GregorianCalendar; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List; 
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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

public class ContactManagerImpl implements ContactManager {
	private 	Map<Integer,MeetingImpl> meetingMap = new HashMap<Integer,MeetingImpl>();
	private 	Map<Integer,FutureMeetingImpl> futureMeetingMap = new HashMap<Integer,FutureMeetingImpl>();
	private 	Map<Integer,PastMeetingImpl> pastMeetingMap = new HashMap<Integer,PastMeetingImpl>();
	private 	Map<Integer,ContactImpl> contactMap = null;
	private int counter = 0;
	private Set<Contact> contacts = null;
	
	private final String CONTACTFILE = null;

	public static void main(String[] args){}
	
	public ContactManagerImpl() {
		XMLEncoder encode = null; 
		// encode writes the object to a file on disk
		try {
			encode = new XMLEncoder(
				new BufferedOutputStream(
					new FileOutputStream(CONTACTFILE)));
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		updateMeetings();
		encode.writeObject(meetingMap);
		encode.close();
// now read the file and display it
		Scanner sc = null;
		try {
			sc = new Scanner(
					new BufferedInputStream(
						new FileInputStream(CONTACTFILE)));
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		while (sc.hasNext()) {
			System.out.println(sc.next());
			sc.close();
		}
		contacts = new HashSet<>();
	}
	
	private void updateMeetings() {
		Set<Entry<Integer,FutureMeetingImpl>> futureMeetingSet = futureMeetingMap.entrySet();
		Iterator<Entry<Integer, FutureMeetingImpl>> futureMeetingIt = futureMeetingSet.iterator();
		Calendar now = Calendar.getInstance();

		while (futureMeetingIt.hasNext()) {
			Map.Entry<Integer,FutureMeetingImpl> futureMeetingSetObj = (Map.Entry<Integer,FutureMeetingImpl>) futureMeetingIt.next();
 			if (now.after(futureMeetingSetObj.getValue().getDate()) == true) {
				futureMeetingMap.remove(futureMeetingSetObj.getKey());
				addNewPastMeeting(futureMeetingSetObj.getValue().getContacts(),futureMeetingSetObj.getValue().getDate(),"some text");
 			}
		}
	}

	@Override
	public int addFutureMeeting(Set<Contact> contacts,Calendar date) {
		Calendar nowDate = Calendar.getInstance();
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
			System.out.println("can't set a future meeting with a past date");
		}
		meetingMap.put(meeting.getId(),meeting); 
		return meeting.getId();		
	}
	
	private int generateId(String str) {
		Long hashId = (long) Math.abs(str.hashCode());
		int hashIdInt = (int) (hashId%100000);
		counter++;
		String hashIdStr = ""+hashIdInt+counter;
		return Integer.parseInt(hashIdStr);
	}
	@Override
	public PastMeeting getPastMeeting(int id) {
		return pastMeetingMap.get(id);
	}
	
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		return futureMeetingMap.get(id);
	}
	
	@Override
	public Meeting getMeeting(int id) {
		return meetingMap.get(id);
	}
	
	@Override 
	public List<Meeting> getFutureMeetingList(Contact contact) {
		List<Meeting> meetings = new ArrayList<>();
// use iterator to go through the list or map. then use 
// enhanced for loop to put the meetings in a list.
		
		@SuppressWarnings("unchecked")
		List<Entry<Integer,FutureMeetingImpl>> futureMeetingList = (List<Entry<Integer, FutureMeetingImpl>>) futureMeetingMap.entrySet();
		Iterator<Entry<Integer, FutureMeetingImpl>> futureMeetingIt = futureMeetingList.iterator();
		
// look in the set of contacts for the contact within a try catch
// block, if set does not contain the contact, throw 
// IllegalArgumentException		
		

		
// Does one ever hold a collection of some element within the class definition of that element ?
		return meetings;
	}
	
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> futureMeetingList = new ArrayList<>();		
		return futureMeetingList;
	}
	 
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		
	}
	 
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {		
	}
	
	@Override
	public void addMeetingNotes(int id, String text) {
		
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

	@Override
	public void flush() { 
		// now turn it back into an object
		XMLDecoder decode = null;
		try {
			decode = new XMLDecoder(
					new BufferedInputStream(
						new FileInputStream(CONTACTFILE)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		Map<Integer,MeetingImpl> idAndMeetings = (Map<Integer,MeetingImpl>) decode.readObject();
		System.out.println("After: " + idAndMeetings);
		decode.close();
	}
}