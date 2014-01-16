package ContactManager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class MeetingImpl implements Meeting,Serializable {
	
	private int id;
	private Calendar date;
//	private ContactManagerImpl cmi = new ContactManagerImpl();
//	private Set<Contact> contacts = null; //not sure if it is beneficial to create contacts as a member field?
//	private Map<Integer,MeetingImpl> 
//	private Calendar nowDate = Calendar.getInstance();

	public MeetingImpl(Set<Contact> contacts,Calendar date) {
		contacts = new HashSet<>();
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

//getContacts() method here suggests I should hold the 
//Set<Contact> in this class rather than ContactManagerImpl. 
//The latter class can then access this by getContacts() method.
	@Override  
	public Set<Contact> getContacts() {
		return contacts;
	}
}