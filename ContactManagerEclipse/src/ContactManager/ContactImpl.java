package ContactManager;

/**
 * This class implements a contact that has a name, notes and a unique ID
 * and can be added to meetings. 
 * All contacts are stored in the ContactManagerImpl class.
 * 
 * @author Shahin Zibaee 24th Jan 2014
 *
 */
public class ContactImpl implements Contact {
	private int id;
	private String name;
	private String notes;
	
	/**
	 * This constructs a contact with a specified name.
	 * 
	 * @param name	the full name of the contact, e.g. "Shahin Zibaee" 
	 * 
	 */
	public ContactImpl(String name) {
		this.name = name;
		notes = "";
	}
	/**
	 * This constructs a contact with a specified id.
	 * 
	 * @param id		the unique id number of the contact
	 * 
	 */	
	public ContactImpl(int id) {
		setId(id);
	}
	
	/**
	 * This constructs a contact with a specified name, note and id.
	 * 	
	 * @param name	the full name of the contact
	 * @param note	the textual note associated with the contact
	 * @param id		the unique id number of the contact 
	 */
	public ContactImpl(String name,String note,int id) {
		this.name = name;
		addNotes(note);
		setId(id);
	}
	
	public ContactImpl(){}
	
	/**
	 * 
	 * @param id		
	 */
	private void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void addNotes(String note) {
		notes += note; 
	}
}
