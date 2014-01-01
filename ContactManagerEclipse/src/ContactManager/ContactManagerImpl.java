package ContactManager;

import java.util.ArrayList;
import java.util.Calendar; 
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
	private 	Map<Integer,MeetingImpl> mMap = new HashMap<>();
	private 	Map<Integer,FutureMeetingImpl> fmMap = new HashMap<>();
	private 	Map<Integer,PastMeetingImpl> pmMap = new HashMap<>();

	public ContactManagerImpl() {
		XMLEncoder encode = null;
		try {
			encode = new XMLEncoder(
				new BufferedOutputStream(
					new FileOutputStream(ContactsFile)));
			} catch (FileNotFoundException e) {
				System.err.println("encoding... " + e);
			}
			encode.writeObject(xxx);
			encode.close();
			// now read the file and display it
			Scanner sc = null;
		try {
			sc = new Scanner(
					new BufferedInputStream(
						new FileInputStream(FILENAME)));
			} catch (FileNotFoundException e) {
				System.err.println("reading... " + e);
			}
		while (sc.hasNext())
		System.out.println(sc.next());
		sc.close();
		
		updateMeetings();		
	}
	
	private void updateMeetings() {
		Set<Entry<Integer,FutureMeetingImpl>> fmSet = fmMap.entrySet();
		Iterator<Entry<Integer, FutureMeetingImpl>> fmIt = fmSet.iterator();
		Calendar now = Calendar.getInstance();

		while(fmIt.hasNext()) {
			Map.Entry<Integer,FutureMeetingImpl> fmSetObj = (Map.Entry<Integer,FutureMeetingImpl>) fmIt.next();
 			if(now.after(fmSetObj.getValue().getDate()) == true) {
				fmMap.remove(fmSetObj.getKey());
				addNewPastMeeting(fmSetObj.getValue().getContacts(),fmSetObj.getValue().getDate(),"some text");
 			}
		}
	}

	@Override
	public int addFutureMeeting(Set<ContactImpl> contacts,Calendar date) {
		FutureMeetingImpl fm = new FutureMeetingImpl(contacts,date);
		return fm.getId();
	}
	
	@Override
	public PastMeeting getPastMeeting(int id) {
		Integer
		Map<Integer,PastMeetingImpl> hm = new HashMap<Integer,PastMeetingImpl>();
		PastMeeting pmi = hm.get(id);
		return pmi;
	}
	
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		HashMap<Integer,FutureMeetingImpl> hm = new HashMap<>();
		return hm.get(id);
	}
	
	@Override
	public Meeting getMeeting(int id) {
		Map<Integer,MeetingImpl> hm = new HashMap<>();
		return hm.get(id);		
	}
	
	@Override 
	public List<Meeting> getFutureMeetingList(Contact contact) {
		List<MeetingImpl> meetings = new ArrayList<>();
		//use iterator to go through the list or map. then use 
		// enhanced for loop to put the meetings in a list.
		return meetings;
	}
	
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		 
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
		XMLDecoder d = null;
		try {
			d = new XMLDecoder(
					new BufferedInputStream(
						new FileInputStream(FILENAME)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		List<typeHere> variable1 = (List<typeHere>) d.readObject();
		System.out.println("After: " + result);
		d.close();
	}
}


