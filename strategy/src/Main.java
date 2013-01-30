import java.io.IOException;


public class Main {
	/*
	 * argv[0] = address of CSV/TXT
	 * argv[1] = name of strategy
	 * argv[2+i] = param[i]
	 */
	public static void main(String [] argv) throws IOException{
		int i; 
		readCSV file = new readCSV(argv[0]);
		double [] valuesCSV = file.read();
		strategy strg = new strategy();
		String [] params = new String[argv.length-2];
		for(i=2;i<argv.length;++i){
			params[i-2] = argv[i];
		}
		
		if(argv[1].equalsIgnoreCase("MACD")){
			strg.registerProduct(argv[1], new MACD());
			strg.createProduct(argv[1], valuesCSV, params);
		}
	}
}
