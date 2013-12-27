package ContactManager;

import java.util.Calendar; 
import java.util.List; 
import java.util.Set;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

public class ContactManagerImpl implements ContactManager {
	private MeetingImpl  
	public int addFutureMeeting(Set<ContactImpl> contacts, Calendar date) {
		
	}
	
	public PastMeetingImpl getPastMeeting(int id) {
		
	}
	 
	public FutureMeetingImpl getFutureMeeting(int id) {
		
	}
	 
	public MeetingImpl getMeeting(int id) {
		
	}
	 
	public List<MeetingImpl> getFutureMeetingList(ContactImpl contact) {
		
	}
	 
	public List<MeetingImpl> getFutureMeetingList(Calendar date) {
		 
	}
	 
	public List<PastMeetingImpl> getPastMeetingList(ContactImpl contact) {
		
	}
	 
	public void addNewPastMeeting(Set<ContactImpl> contacts, Calendar date, String text) {
		
	}
	
	public void addMeetingNotes(int id, String text) {
		
	}

	public void addNewContact(String name, String notes) {
		
	}

	public Set<ContactImpl> getContacts(int... ids) {
		
	}

	public Set<ContactImpl> getContacts(String name) {
		
	}

	public void flush() { 
		
	}
}