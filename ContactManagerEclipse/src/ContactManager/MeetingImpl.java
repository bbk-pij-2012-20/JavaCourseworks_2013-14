package ContactManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class MeetingImpl implements Meeting {
	
	private static int id;
	private Calendar date;
	private Set<Contact> contacts;

	public MeetingImpl(Set<Contact> contacts,Calendar date) {
		this.date = date;
		this.contacts = contacts;
		contacts = new HashSet<>();
		id++;
	}
	
	@Override
	public void addContact(Contact contact) {
		contacts.add((ContactImpl) contact);
	}
	
	@Override
	public int getId() {
		int id = MeetingImpl.id;
		return id;
	}
	
	@Override
	public Calendar getDate() {
		return date;
	}
	
	@Override
	public Set<Contact> getContacts() {
		Set<Contact> contacts = new HashSet<>();
		return contacts;
	}
}