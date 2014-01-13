package ContactManager;

public class ContactImpl implements Contact {
	protected int id;
	private String name;
	private String notes;
	
	public ContactImpl(String name) {
		this.name = name;
		notes = "";
	}
	public ContactImpl(){}
	
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
