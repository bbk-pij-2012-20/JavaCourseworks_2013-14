package ContactManager;

public class ContactImpl implements Contact {
	private int id;
	private String name;
	private String notes;
	
	public ContactImpl(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	String getNotes() {
		return notes;
	}
	
	public void addNotes(String note) {
		notes += note; 
	}
	
// added just to stop Eclipse complaining about a lack of a "main type"
	public static void main(String args[]) {	
	}
}
