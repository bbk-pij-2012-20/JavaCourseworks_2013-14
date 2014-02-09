package ContactManager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
	
@SuppressWarnings("serial")
public class PastMeetingImpl extends MeetingImpl implements PastMeeting,Serializable {
	private String notes = "";
	
	public PastMeetingImpl(Set<Contact> contacts,Calendar date,String text,int id) {
		super(contacts,date,id);
		notes += text;
	}

	/**
	 * An empty constructor.
	 */
	public PastMeetingImpl(){}
	
	@Override
	public String getNotes() {
		return notes;
	}
}