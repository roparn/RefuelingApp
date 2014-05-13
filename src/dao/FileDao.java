package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import models.FuelEntry;

public class FileDao {
	
	public List<FuelEntry> getFuelEntries(String fileLocation) throws ParseException, IOException{
		List<FuelEntry> fuelEntryList = new ArrayList<FuelEntry>();
		String line;
        BufferedReader br = new BufferedReader(new FileReader(fileLocation));
        while ((line = br.readLine()) != null){
        	String [] values = line.split("\\|");
        	fuelEntryList.add(new FuelEntry(values[0], values[1], values[2], values[3]));
        }
        br.close();
		return fuelEntryList;	
	}
}
