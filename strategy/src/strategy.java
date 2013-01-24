import au.com.bytecode.opencsv.CSVReader;
//import gov.sns.tools.ArrayMath;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class strategy {
	private static final String ADDRESS_FILE_CSV = "D:\\Ioana\\EPFL\\Gag\\Project\\strategy\\data\\Google.csv";
	private static final String ADDRESS_FILE_TXT = "D:\\Ioana\\EPFL\\Gag\\Project\\strategy\\data\\chfeur.txt";
	private static double [] valuesCSV;
	private static double [] valuesTXT;
	
	
	/*
	 * Strategy 1.
	 * Buy when the MACD line crosses up through the signal line, 
	 * or sell when it crosses down through the signal line.
	 */
	public static int [] SignalLineCrossover(double [] MACD, double [] signal){
		int MACDlng = MACD.length;
		int [] s = new int[MACDlng];
		for(int i=0; i<MACDlng; i++){
			if(MACD[i]>signal[i])
				s[i] = 1;
			else if (MACD[i]<signal[i])
				s[i] = -1;
		}
		return s;
	}
	
	/*
	 * Strategy 2.
	 * Zero crossover. 
	 * A move from positive to negative is bearish and from negative to positive, bullish. 
	 */
	public static int [] ZeroCrossover(double [] MACD){
		int MACDlng = MACD.length;
		int [] s = new int[MACDlng];
		for(int i=0; i<MACDlng; i++){
			if(MACD[i]>0)
				s[i] = 1;
			else if (MACD[i]<0)
				s[i] = -1;
		}
		return s;
	}
	
	/*
	 * Strategy 3.
	 * Avoid false signals.
	 * Buy if the MACD line breaks above the signal line and then remains above it for three days
	 */
	public static int [] AvoidFalseSignals(double [] MACD, double [] signal){
		int MACDlng = MACD.length, counterDays=0;
		int direction = 0;//1 if the trend is bullish -> buy in the future sign;-1->trend bearish, sell
		int [] s = new int[MACDlng];
		for(int i=1; i<MACDlng; i++)
			//if MACD is up to the signal we keep the same position 
			//until the up direction takes more than 3 days -> position becomes buy
			if(MACD[i]>=signal[i] && direction==1){
				if(counterDays!=2){
					counterDays++;
					s[i]=s[i-1];
				}
				else if(counterDays==2){
					//counterDays=0;
					s[i]=1;
				}
			}
		
			//MACD cross the signal
			else if(MACD[i]>=signal[i] && direction!=1){
				direction=1; //change direction
				counterDays=1;
				s[i]=s[i-1]; //maintain same position 3 days in a row
			}
		
			//reverse strategy for selling
			else if(MACD[i]<signal[i] && direction==-1){
				if(counterDays!=2){
					counterDays++;
					s[i]=s[i-1];
				}
				else if(counterDays==2){
					//counterDays=0;
					s[i]=-1;
				}
			}
			
			//MACD cross the signal
			else if(MACD[i]<signal[i] && direction!=-1){
				direction=-1; //change direction
				counterDays=1;
				s[i]=s[i-1]; //maintain same position 3 days in a row
			}
			
			else
				System.out.println("Error: Other case?");
			
		return s;
	}
	
	public static double returnStrategy(double [] price, int [] s){
		return ArrayMath.sum(ArrayMath.pairwiseMultiply(price, s));
	}
	
	public static void main(String[] args) throws IOException {
		
		//count the number of lines from csv file to allocate memory for the prices
		LineNumberReader  lnr = new LineNumberReader(new FileReader(ADDRESS_FILE_CSV));
		int i;
		lnr.skip(Long.MAX_VALUE);
		valuesCSV = new double[lnr.getLineNumber()];
		
		//read the prices from csv file
		CSVReader reader = new CSVReader(new FileReader(ADDRESS_FILE_CSV));
		String [] nextLine;
		i=0;
		nextLine = reader.readNext();//read first line - title
		while ((nextLine = reader.readNext()) != null) {
			//System.out.println("Date: [" + nextLine[0] + "] Open: [" + nextLine[1] + "] High: [" + nextLine[2] + "] Low:[" + nextLine[3] + "] Close:[" + nextLine[4] + "] Volume:[" + nextLine[5] + "] Adj Close:[" + nextLine[6]+"]");
			valuesCSV[i++] = Float.parseFloat(nextLine[6]);
			//System.out.println(valuesCSV[i-1]);
		}
		i--;
		
		
		//count the number of lines from file to allocate memory for the prices
		lnr = new LineNumberReader(new FileReader(ADDRESS_FILE_TXT));
		lnr.skip(Long.MAX_VALUE);
		valuesTXT = new double[lnr.getLineNumber()];
		
		//reading values from text file
		String line;
		String [] tokens;
		i=0;
		BufferedReader br = new BufferedReader(new FileReader(ADDRESS_FILE_TXT));
		line = br.readLine();
		while((line = br.readLine()) != null) {
			tokens = line.split(",");
			valuesTXT[i++] = Double.parseDouble(tokens[4]);
		}
		i--;
		
		
		EMA tseries = new EMA(12, valuesTXT);
		double [] EMA12 = tseries.calculateTS_EMA();
		tseries = new EMA(26,valuesTXT);
		double [] EMA26 = tseries.calculateTS_EMA();
//		static double [] MACD = ArrayMath.subtract(EMA12,EMA26);
		int length = valuesTXT.length;
		double[] MACD = new double[length];
/*		double[] MACD2 = new double[length];
		for(i=0; i<length; i++)
			MACD2[i] = EMA12[i]-EMA26[i];//how to import ArrayMath; important
*/		MACD = ArrayMath.pairwiseSubtract(EMA12, EMA26);
		tseries = new EMA(9,MACD);
		double [] signal = tseries.calculateTS_EMA();//important - EMA9

		int [] s1 = new int[length], s2 = new int[length], s3 = new int[length];
		s1 = strategy.SignalLineCrossover(MACD, signal);
		s2 = strategy.ZeroCrossover(MACD);
		s3 = strategy.AvoidFalseSignals(MACD, signal);
		
		System.out.println(strategy.returnStrategy(valuesTXT, s1));
		System.out.println(strategy.returnStrategy(valuesTXT, s2));
		System.out.println(strategy.returnStrategy(valuesTXT, s3));
		
//		for(i=0;i<lnr.getLineNumber();i++)
//			System.out.println(MACD[i]+"   "+ MACD2[i]);


	}
}
