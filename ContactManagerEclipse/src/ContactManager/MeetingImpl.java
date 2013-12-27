package ContactManager;

import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

public class MeetingImpl implements Meeting {
	
	private static int id;
	private Calendar date;
	private Set<ContactImpl> contacts;
	
	// takes one contact at construction time. Therefore any meeting
	// must always contain at least 1 contact. (No other constructor provided).
	public MeetingImpl(Calendar date,ContactImpl contact) {
		this.date = date;
		contacts = new HashSet<>();
		contacts.add(contact);
		id++;
	}
	
	public void addContact(ContactImpl contact) {
		contacts.add(contact);
	}
	
	public int getId() {
		return id;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public Set<Contact> getContacts() {
		return contacts;
	}
}
