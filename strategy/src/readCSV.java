import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import au.com.bytecode.opencsv.CSVReader;


public class readCSV {
	private static String ADDRESS_FILE_CSV;
	public readCSV(String address){
		ADDRESS_FILE_CSV = address;
	}
	public double [] read() throws IOException{
		int i;
		double [] valuesCSV;
		LineNumberReader  lnr = new LineNumberReader(new FileReader(ADDRESS_FILE_CSV));
		lnr.skip(Long.MAX_VALUE);
		valuesCSV = new double[lnr.getLineNumber()-1];
		//read the prices from csv file
		CSVReader reader = new CSVReader(new FileReader(ADDRESS_FILE_CSV));
		String [] nextLine;
		i=0;
		nextLine = reader.readNext();//read first line - title
		while ((nextLine = reader.readNext()) != null)
			valuesCSV[i++] = Float.parseFloat(nextLine[6]);
		return valuesCSV;
	}
}
