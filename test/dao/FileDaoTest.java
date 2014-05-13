package dao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import models.FuelEntry;
import org.junit.Test;

public class FileDaoTest {

	@Test
	public void testGetFuelEntriesFromTestFile() throws ParseException, IOException {
		FileDao fd = new FileDao();
		List<FuelEntry> entrylist = fd.getFuelEntries("/home/robert/workspace/RefuelApp/src/dao/file.txt");
		FuelEntry entry = new FuelEntry("98","1.319","50.56","01.01.2014");
		assertTrue(entrylist.get(0).equals(entry));
	}
	@Test(expected = FileNotFoundException.class)
	public void testGetFuelEntriesUnexistingFile() throws ParseException, IOException{
		FileDao fd = new FileDao();
		@SuppressWarnings("unused")
		List<FuelEntry> entrylist = fd.getFuelEntries("21321123");
	}

}
