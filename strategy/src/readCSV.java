import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;


public class readCSV {
	public static ArrayList<Double> read (String file) throws IOException{
		ArrayList<Double> valuesCSV = new ArrayList<Double>();
		CSVReader reader = new CSVReader(new FileReader(file));
		String [] nextLine;
		nextLine = reader.readNext();//read first line - title

		while ((nextLine = reader.readNext()) != null)
			valuesCSV.add(Double.parseDouble(nextLine[6]));

		return valuesCSV;
	}
}
