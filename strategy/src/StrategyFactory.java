import java.util.ArrayList;

public class StrategyFactory {
	
	public static Strategy createStrategy(String sID, String[] params) throws Exception{
		if(sID.equals("MACD"))
			return new MACD(params);
		else if(sID.equals("ZeroCrossover"))
			return new ZeroCrossover(params);
		else if(sID.equals("MACDv2"))
			return new MACDv2(params);
		else if(sID.equals("AvoidFalseSignals"))
			return new AvoidFalseSignals(params);
		throw new Exception("No strategy found");
	}

	public static void main(String[] argv) throws Exception{
		double profit=0;
		ArrayList<Double> values;
		if(argv[0].equals("csv"))
			values = readCSV.read(argv[1]);
		else if(argv[0].equals("txt"))
			values = readTXT.read(argv[1]);
		else
			throw new Exception("No good type for file");
		String [] params = new String[argv.length-3];
		for(int i=3;i<argv.length;++i){
			params[i-3] = argv[i];
		}
		Strategy strategy = createStrategy(argv[2], params);
		int sizeValues = values.size();
		int[] signal = new int[sizeValues];
		for(int i=0; i<sizeValues-1; ++i){
			signal[i] = strategy.addTick(values.get(i));
			profit += signal[i]*Math.log(values.get(i+1)/values.get(i));
		}
		System.out.println("Cumulative return is " + profit);
	}
}
