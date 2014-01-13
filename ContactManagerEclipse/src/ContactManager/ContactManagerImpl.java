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
	private 	Map<Integer,MeetingImpl> meetingMap;
	private 	Map<Integer,FutureMeetingImpl> futureMeetingMap;
	private 	Map<Integer,PastMeetingImpl> pastMeetingMap;
	private 	Map<Integer,ContactImpl> contactMap;
	int contactCounter = 0;
	int meetingCounter = 0;
	
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
		MeetingImpl meeting;
		Map<Integer,MeetingImpl> meetingMap;
		try {
			if (nowDate.after(date)) {
				throw new IllegalArgumentException();
			}
			meeting = new FutureMeetingImpl(contacts,date);
			meetingMap = new HashMap<Integer,MeetingImpl>();
			
			new MeetingImpl(generateId(date));
			
		} catch (IllegalArgumentException e) {
			System.out.println("can't set a future meeting with a past date");
		}
		meetingMap.put(meeting.getId(),meeting); 
		return meeting.getId();		
	}
	
	private int generateId(Calendar date) {
		String dateStr = ""+date.getTime();
		Long hashId = (long) Math.abs(dateStr.hashCode());
		int hashIdInt = (int) (hashId%100000);
		meetingCounter++;
		String hashIdStr = ""+hashIdInt+meetingCounter;
		return Integer.parseInt(hashIdStr);
	}
	@Override
	public PastMeeting getPastMeeting(int id) {
		return pmMap.get(id);
	}
	
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		return fmMap.get(id);
	}
	
	@Override
	public Meeting getMeeting(int id) {
		return mMap.get(id);
	}
	
	@Override 
	public List<Meeting> getFutureMeetingList(Contact contact) {
		List<Meeting> meetings = new ArrayList<>();
		//use iterator to go through the list or map. then use 
		// enhanced for loop to put the meetings in a list.
		
		@SuppressWarnings("unchecked")
		List<Entry<Integer,FutureMeetingImpl>> fmList = (List<Entry<Integer, FutureMeetingImpl>>) fmMap.entrySet();
		Iterator<Entry<Integer, FutureMeetingImpl>> fmIt = fmList.iterator();
		
// Should I make a MeetingList member variable here and/or in the MeetingImpl class ?
// If in the MeetingImpl, should it be static?
		return meetings;
	}
	
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> fml = new ArrayList<>();
		
		return 
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
			ContactImpl c1 = new ContactImpl(name);
			c1.addNotes(notes);
			Long hashId = (long) Math.abs(name.hashCode());
			int hashIdInt = (int) (hashId%100000);
			contactCounter++;
			String hashIdStr = ""+hashIdInt+contactCounter;
			c1.id = Integer.parseInt(hashIdStr);
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