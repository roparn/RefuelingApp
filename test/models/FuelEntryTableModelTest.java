package models;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import dao.FileDao;

public class FuelEntryTableModelTest {

	static List<FuelEntry> data;
	static FuelEntryTableModel fetm;
	public static void populateList() throws FileNotFoundException, ParseException, IOException{
		data = new FileDao().getFuelEntries(new BufferedReader(
				new FileReader(FileDao.DEFAULT_FILE_LOCATION)));
		fetm = new FuelEntryTableModel(data);
	}
	@BeforeClass
	public static void testSetup() throws FileNotFoundException, ParseException, IOException{
		populateList();
	}
	@Test
	public void testFuelEntryTableModel() {
		@SuppressWarnings("unused")
		FuelEntryTableModel fetm = new FuelEntryTableModel(data);
	}

	@Test
	public void testGetColumnCount() {
		String message = "Column count must be 4, was " +fetm.getColumnCount();
		assertTrue(message,fetm.getColumnCount()==4);
	}

	@Test
	public void testGetRowCount() {
		String message = String.format("Data length must be %d, was %d",data.size(),fetm.getColumnCount());
		assertTrue(message, data.size()==fetm.getRowCount());
	}

	@Test
	public void testGetValueAt() {
		assertTrue(data.get(0).getFuelName()==fetm.getValueAt(0, 0));
	}

}
