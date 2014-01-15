package ContactManager;

public class ContactImpl implements Contact {
	protected int id;
	private String name;
	private String notes;
	
	public ContactImpl(String name) {
		this.name = name;
		notes = "";
	}
	
	public ContactImpl(int id) {
		setId(id);
	}
	
	public ContactImpl(String name,String note,int id) {
		this.name = name;
		addNotes(note);
		setId(id);
	}
	
	public ContactImpl(){}
	
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
