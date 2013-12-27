package ContactManager;

import java.util.Calendar; 
import java.util.Set;
import java.util.HashSet;

/**
 * A class to represent meetings 
 *
 * Meetings have unique IDs, scheduled date and a list of participating contacts 
 */
public interface Meeting {
	
	/**
	 * (Not in original interface). 
	 * Add contacts to the meeting which takes one 
	 * contact at construction time.
	 * 
	 * @param	contact		the contact added one-by-one to a meeting.
	 */
	 void addContact(Contact contact);
	
	/**
	 * Returns the id of the meeting.
	 *
	 * @return	the id of the meeting.
	 */
	 int getId();

	/**
	 * Return the date of the meeting. 
	 *
	 * @return	the date of the meeting. 
	 */
	 Calendar getDate();

	/**
	 * Return the details of people that attended the meeting. 
	 *
	 * The list contains a minimum of one contact (if there were
	 * just two people: the user and the contact) and may contain an
	 * arbitrary number of them. 
	 *
	 * @return	the details of people that attended the meeting. 
	 */
	 Set<Contact> getContacts(); 
}
