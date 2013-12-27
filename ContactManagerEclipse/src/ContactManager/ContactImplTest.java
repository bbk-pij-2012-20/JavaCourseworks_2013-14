package ContactManager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContactImplTest {
	private ContactImpl ci;
	
	@Before
	public void setUp() throws Exception {
		ci = new ContactImpl("Shahin Zibaee");
		ci = new ContactImpl("Humphrey Bogart");
//		ci = new ContactImpl("Kenny Dalglish");
//		ci = new ContactImpl("Fred Sanger");
	}

	@After
	public void tearDown() throws Exception {
		ci = null;
	}

	@Test
	public void testGetId() {
		assertEquals(2, ci.getId());		
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNotes() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNotes() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
