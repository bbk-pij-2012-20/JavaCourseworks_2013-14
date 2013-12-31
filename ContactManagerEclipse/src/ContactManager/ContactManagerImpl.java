package ContactManager;

import java.util.ArrayList;
import java.util.Calendar; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.List; 
import java.util.Map;
import java.util.Set;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.net.IDN;

public class ContactManagerImpl implements ContactManager {
	
	public ContactManagerImpl() {
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
		HashMap<Integer,MeetingImpl> hm = new HashMap<>();
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
		
	}
}