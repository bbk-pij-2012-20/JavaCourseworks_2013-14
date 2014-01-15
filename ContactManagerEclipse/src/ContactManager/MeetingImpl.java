package ContactManager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class MeetingImpl implements Meeting,Serializable {
	
	private int id;
	private int counter;
	private Calendar date;
	private ContactManagerImpl cmi = new ContactManagerImpl();
	private Set<Contact> contacts = cmi.contacts;//not sure if it is beneficial to create contacts as a member field?
//	private Map<Integer,MeetingImpl> 
//	private Calendar nowDate = Calendar.getInstance();

	public MeetingImpl(Set<Contact> contacts,Calendar date) {
		this.contacts = new HashSet<>();
		setContacts(contacts);
		setDate(date);
		String dateStr = ""+date.getTime();
		id = dateStr.hashCode() + ++counter;
	}
	public MeetingImpl(int id) {
		setId(id);
	}
	
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
//		Set<Contact> contacts = new HashSet<>();
		return contacts;
	}
}