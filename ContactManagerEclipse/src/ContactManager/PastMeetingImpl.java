package ContactManager;

import java.util.Calendar;
import java.util.Set;
	
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	private String notes;
	
	public PastMeetingImpl(Set<Contact> contacts,Calendar date) {
		super(contacts,date);
	}
	
	@Override
	public String getNotes() {
		return notes;
	}
}
