import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class strategy {
	private static final String ADDRESS_FILE_CSV = "D:\\Ioana\\EPFL\\Gog\\Project\\strategy\\data\\Google.csv";
	private static final String ADDRESS_FILE_TXT = "D:\\Ioana\\EPFL\\Gog\\Project\\strategy\\data\\chfeur.txt";
	private static float [] valuesCSV;
	private static float [] valuesTXT;
	
	public static void main(String[] args) throws IOException {
		
		//count the number of lines from csv file to allocate memory for the prices
		LineNumberReader  lnr = new LineNumberReader(new FileReader(ADDRESS_FILE_CSV));
		int i;
		lnr.skip(Long.MAX_VALUE);
		valuesCSV = new float[lnr.getLineNumber()];
		
		//read the prices from csv file
		CSVReader reader = new CSVReader(new FileReader(ADDRESS_FILE_CSV));
		String [] nextLine;
		i=0;
		nextLine = reader.readNext();//read first line - title
		while ((nextLine = reader.readNext()) != null) {
			//System.out.println("Date: [" + nextLine[0] + "] Open: [" + nextLine[1] + "] High: [" + nextLine[2] + "] Low:[" + nextLine[3] + "] Close:[" + nextLine[4] + "] Volume:[" + nextLine[5] + "] Adj Close:[" + nextLine[6]+"]");
			valuesCSV[i++] = Float.parseFloat(nextLine[6]);
			System.out.println(valuesCSV[i-1]);
		}
		i--;
		
		
		//count the number of lines from file to allocate memory for the prices
		lnr = new LineNumberReader(new FileReader(ADDRESS_FILE_TXT));
		lnr.skip(Long.MAX_VALUE);
		valuesTXT = new float[lnr.getLineNumber()];
		
		//reading values from text file
		String line;
		String [] tokens;
		i=0;
		BufferedReader br = new BufferedReader(new FileReader(ADDRESS_FILE_TXT));
		line = br.readLine();
		while((line = br.readLine()) != null) {
			tokens = line.split(",");
			valuesTXT[i++] = Float.parseFloat(tokens[4]);
		}
		i--;
		
		
		EMA tseries = new EMA(22, valuesTXT);
		double [] val = tseries.calculateTS_EMA();
		for(i=0;i<lnr.getLineNumber();i++)
			System.out.println(val[i]);
	}
}
