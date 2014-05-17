package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import models.FuelEntry;

public class FileDao {
	public final static String DEFAULT_FILE_LOCATION = "src/dao/file.txt";

	public BufferedReader openFile(String fileLocation)
			throws FileNotFoundException {
		return new BufferedReader(new FileReader(fileLocation));
	}

	/**
	 * This method parses the given file and returns arraylist of FuelEntry
	 * objects. For default file parsing, pass openFile with
	 * DEFAULT_FILE_LOCATION as parameter.
	 * 
	 * @param file
	 *            BufferedReader object that is the contents of a file to be
	 *            parsed
	 * @return Arraylist of parsed FuelEntry objects from file
	 * @throws ParseException
	 *             when parsing fails
	 * @throws IOException
	 *             when there is an IO error
	 */
	public List<FuelEntry> getFuelEntries(BufferedReader file)
			throws ParseException, IOException {
		List<FuelEntry> fuelEntryList = new ArrayList<FuelEntry>();
		String line;
		while ((line = file.readLine()) != null) {
			try {
				String[] values = line.split("\\|");
				fuelEntryList.add(new FuelEntry(values[0], values[1],
						values[2], values[3]));
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new ParseException(e.getMessage(), 0);
			}
		}
		file.close();
		return fuelEntryList;
	}
}
