package swing;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainAppMenuTest {


	@Test
	public void testGetDataBadPath() {
		MainAppMenu mainApp = new MainAppMenu();
		mainApp.getData("n/o/n/existent/path");
		String expected = "File not found";
		String message = "Statusbar test did not equal: \"" + expected +"\"";
		assertEquals(message, mainApp.getStatusbar().getText(), expected);
	}
	

}
