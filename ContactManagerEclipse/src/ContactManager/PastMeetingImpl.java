package ContactManager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
	
@SuppressWarnings("serial")
public class PastMeetingImpl extends MeetingImpl implements PastMeeting,Serializable {
	private String notes;
	
	public PastMeetingImpl(Set<Contact> contacts,Calendar date,int id) {
		super(contacts,date,id);
	}
	
	public PastMeetingImpl(Set<Contact> contacts,Calendar date,String text,int id) {
		super(contacts,date,id);
		notes = text;
	}
	
	public PastMeetingImpl(String text) {
		notes = text;
	}
	
	@Override
	public String getNotes() {
		return notes;
	}
}