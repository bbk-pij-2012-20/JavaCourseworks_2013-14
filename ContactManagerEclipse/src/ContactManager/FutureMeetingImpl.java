package ContactManager;

import java.io.Serializable;
import java.util.Set;
import java.util.Calendar;

@SuppressWarnings("serial")
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting,Serializable {
	public FutureMeetingImpl(Set<Contact> contacts,Calendar date,int id) {
		super(contacts,date,id);
	}
	
	/**
	 * An empty constructor.
	 */
	public FutureMeetingImpl(){}
	
}