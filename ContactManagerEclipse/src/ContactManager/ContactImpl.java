package ContactManager;

public class ContactImpl implements Contact {
	private static int id;
	private String name;
	private String notes;
	
	public ContactImpl(String name) {
		this.name = name;
		id++;
		notes = "";
	}
	
	public static int getId() {
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
	
/* added just to stop Eclipse complaining about a lack of a "main type"
	public static void main(String args[]) {	
		ContactImpl ci = new ContactImpl("Shahin Zibaee");
		System.out.println(ContactImpl.getId());
		System.out.println(ci.getName());
	}*/
}
