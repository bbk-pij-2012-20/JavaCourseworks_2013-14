package ContactManager;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContactImplTest {
	private ContactImpl contact;
	private String name;
	private int id;
	private String note;
	
	@Before
	public void setUp() throws Exception {
		id = 1234567;
		name = "Shahin Zibaee";
		note = "is a football guru";
		contact = new ContactImpl(name,note,id);
	}
	
	@After
	public void tearDown() throws Exception {
		contact = null;
	}

	@Test
	public void testGetId() {
		int expectedOutput = id;
		int actualOutput = contact.getId();
		assertEquals(expectedOutput,actualOutput);
	}

	@Test
	public void testGetName() {		
		String expectedOutput = name;
		String actualOutput = contact.getName();
		assertEquals(expectedOutput,actualOutput);
	}

	@Test
	public void testGetNotes() {		
		contact = new ContactImpl(name,note,id);
		String expectedOutput = note;
		String actualOutput = contact.getNotes();
		assertEquals(expectedOutput,actualOutput);
	}

	@Test
	public void testAddNotes() {		
		contact = new ContactImpl();
		contact.addNotes(note);
		String expectedOutput = note;
		String actualOutput = contact.getNotes();
		assertEquals(expectedOutput,actualOutput);
	}

}
