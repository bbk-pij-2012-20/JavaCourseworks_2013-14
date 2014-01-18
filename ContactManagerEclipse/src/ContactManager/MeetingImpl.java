package ContactManager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

public class MeetingImpl implements Meeting,Serializable {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts = null;

	public MeetingImpl(Set<Contact> contacts,Calendar date) {
		this.contacts = new HashSet<>();
		setContacts(contacts);
		setDate(date);
	}
	
	public MeetingImpl(int id) {
		setId(id);
	}
	
	public MeetingImpl(){}
	
	private void setDate(Calendar date) {
		this.date = date;
	}
	
	private void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
	
	private void setId(int id) {
		this.id = id;
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