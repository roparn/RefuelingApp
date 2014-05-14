package dao;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import models.FuelEntry;
import org.junit.Test;

public class FileDaoTest {

	@Test
	public void testGetFuelEntriesFromTestFile() throws ParseException, IOException {
		FileDao fd = new FileDao();
		List<FuelEntry> entryList1 = fd.getFuelEntries(fd.openFile(FileDao.DEFAULT_FILE_LOCATION));
		List<FuelEntry> entryList2 = new ArrayList<FuelEntry>();
		entryList2.add(new FuelEntry("98","1.319","50.56","01.01.2014"));
		entryList2.add(new FuelEntry("98","1.319","45,32","15.01.2014"));
		entryList2.add(new FuelEntry("95","1.21","30.4","02.02.2014"));
		entryList2.add(new FuelEntry("98","1.319","50.30","09.02.2014"));
		entryList2.add(new FuelEntry("98","1.392","45.25","11.03.2014"));
		entryList2.add(new FuelEntry("95","1.319","5.00","01.04.2014"));
		entryList2.add(new FuelEntry("D","1.219","5.00","01.02.2014"));
		entryList2.add(new FuelEntry("E85","0.95","15,12","12.11.2014"));
		for(int i = 0; i<entryList2.size(); i++){
			assertTrue("FuelEntry object values must be equal", entryList1.get(i).equals(entryList2.get(i)));
		}
	}
	@Test(expected = ParseException.class)
	public void testGetFuelEntriesBadFile() throws ParseException, IOException{
		BufferedReader file = new BufferedReader(new StringReader("D|1.3|0,833|03/09.2011"));
		new FileDao().getFuelEntries(file);
	}
	@Test(expected = FileNotFoundException.class)
	public void testGetFuelEntriesUnexistingFile() throws ParseException, IOException{
		FileDao fd = new FileDao();
		@SuppressWarnings("unused")
		List<FuelEntry> entrylist = fd.getFuelEntries(fd.openFile("asd213123"));
	}
	@Test
	public void testGetFuelEntriesCustomFile() throws ParseException, IOException{

		BufferedReader file = new BufferedReader(new StringReader("D|0,8|33.2|03.09.2011"));
		FuelEntry entry = new FileDao().getFuelEntries(file).get(0);
		FuelEntry entry2 = new FuelEntry("D", "0.8", "33.2", "03.09.2011");
		assertTrue(entry.equals(entry2));
	}
}
