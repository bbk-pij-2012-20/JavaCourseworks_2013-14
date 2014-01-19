package ContactManager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

@SuppressWarnings("serial")
public class MeetingImpl implements Meeting,Serializable {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts = null;

	public MeetingImpl(Set<Contact> contacts,Calendar date, int id) {
		this.contacts = new HashSet<>();
		setContacts(contacts);
		setDate(date);
		this.id = id;
	}

	public MeetingImpl(){}
	
	private void setDate(Calendar date) {
		this.date = date;
	}
	
	private void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
		
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public Calendar getDate() {
		return date;
	}

	@Override  
	public Set<Contact> getContacts() {
		return contacts;
	}
}