package models;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FuelEntryTest {
	
	@BeforeClass
	public static void testSetup() {
	}
	
	@AfterClass
	public static void testCleanup() {
	}

	@Test
	public void testFuelEntryConstructorByString() throws ParseException{
		FuelEntry entry = new FuelEntry("98", 1.9, 14.22, 
				new SimpleDateFormat("dd.MM.yyyy").parse("12.02.1998"));
		String expectedString = "FuelEntry [fuelName=98, fuelPrice=1.9" 
				+ ", fuelAmount=14.22, refuelingDate=12.02.1998]";
	    assertEquals("Strings must equal", expectedString, entry.toString());
	}
	@Test
	public void testFuelEntryConstructorByEquals() throws ParseException{
		FuelEntry entry = new FuelEntry("98", 1.9, 14.22, 
				new SimpleDateFormat("dd.MM.yyyy").parse("12.02.1998"));
		FuelEntry entry2 = new FuelEntry("98", 1.9, 14.22, 
				new SimpleDateFormat("dd.MM.yyyy").parse("12.02.1998"));
		assertTrue("Object values must equal", entry.equals(entry2));
	}
	@Test
	public void testFuelEntryConstructorAltByString() throws ParseException {
		FuelEntry entry = new FuelEntry("95", "1,32", "32.32", "12.02.1998");
		String expectedString = "FuelEntry [fuelName=95, fuelPrice=1.32" 
				+ ", fuelAmount=32.32, refuelingDate=12.02.1998]";
	    assertEquals("Strings must equal", expectedString, entry.toString());
	}
	@Test
	public void testFuelEntryConstructorAltByEquals() throws ParseException {
		FuelEntry entry = new FuelEntry("95", "1,32", "32.32", "12.02.1998");
		FuelEntry entry2 = new FuelEntry("95", 1.32, 32.32, 
				new SimpleDateFormat("dd.MM.yyyy").parse("12.02.1998"));
		assertTrue("Object values must equal", entry.equals(entry2));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testFuelEntryIllegalPriceByConstructor() throws ParseException{
		@SuppressWarnings("unused")
		FuelEntry entry = new FuelEntry("98", -1.9, 14.22, 
				new SimpleDateFormat("dd.MM.yyyy").parse("12.02.1998"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testFuelEntryIllegalAmountByConstructor() throws ParseException{
		@SuppressWarnings("unused")
		FuelEntry entry = new FuelEntry("98", 1.9, -14.22, 
				new SimpleDateFormat("dd.MM.yyyy").parse("12.02.1998"));
	}

}
