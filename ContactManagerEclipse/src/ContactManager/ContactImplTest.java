package ContactManager;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContactImplTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
//		ContactImpl ci = null;
	}

	@Test
	public void testGetId() {
		new ContactImpl("Shahin Zibaee");
		new ContactImpl("Shahin Zibaee2");
		new ContactImpl("Shahin Zibaee3");
		assertEquals(3,ContactImpl.getId());		
	}

	@Test
	public void testGetName() {
		ContactImpl ci = new ContactImpl("Shahin Zibaee");
		assertEquals("Shahin Zibaee",ci.getName());
	}

	@Test
	public void testAddNotes() {		
		ContactImpl ci = new ContactImpl("Shahin Zibaee");
		ci.addNotes("..is a football god");
		assertEquals("..is a football god",ci.getNotes());
	}
}
