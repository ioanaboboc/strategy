import java.util.ArrayList;

public class StrategyFactory {
	
	public static Strategy createStrategy(String sID, String[] params) throws Exception{
		if(sID.equals("MACD"))
			return new MACD(params);
		else if(sID.equals("ZeroCrossover"))
			return new ZeroCrossover(params);
		throw new Exception("No strategy found");
	}

	public static void main(String[] argv) throws Exception{
		double profit=0;
		ArrayList<Double> valuesCSV = readCSV.read(argv[0]);
		String [] params = new String[argv.length-2];
		for(int i=2;i<argv.length;++i){
			params[i-2] = argv[i];
		}
		Strategy strategy = createStrategy(argv[1], params);
		int sizeValues = valuesCSV.size();
		int[] signal = new int[sizeValues];
		for(int i=0; i<sizeValues-1; ++i){
			signal[i] = strategy.addTick(valuesCSV.get(i));
			profit += signal[i]*valuesCSV.get(i+1)/valuesCSV.get(i);
		}
		System.out.println("Cumulative return is " + profit);
	}
}
