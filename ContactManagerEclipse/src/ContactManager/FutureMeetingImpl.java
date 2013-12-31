package ContactManager;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
	public FutureMeetingImpl(Set<ContactImpl> contacts,Calendar date) {
		super(contacts,date);
	}
}