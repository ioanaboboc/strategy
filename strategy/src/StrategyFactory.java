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
		String [] auxParams = new String[argv.length-3];
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
		
		//Find best combination of parameters
		int[] auxParam = new int[params.length];
		int[] findParam = new int[params.length];
		for(int i=0;i<params.length;++i)
			auxParam[i] = Integer.parseInt(params[i]);
		for(int j=0;j<100;++j){
			for(int i=0;i<params.length;++i){
				findParam[i] = (int) (auxParam[i]+(int)(Math.random()*10)*Math.pow((-1),(int)(Math.random()*10)));
				if(findParam[i]==0)
					findParam[i]=1;
				auxParams[i] = String.valueOf(findParam[i]);
			}
			strategy = createStrategy(argv[2], auxParams);
			double newProfit = 0;
			for(int k=0; k<sizeValues-1; ++k){
				signal[k] = strategy.addTick(values.get(k));
				newProfit += signal[k]*Math.log(values.get(k+1)/values.get(k));
			}
			if(newProfit>profit){
				profit = newProfit;
				for(int i=0;i<params.length;++i)
					params[i] = auxParams[i];
			}
		}
		System.out.print("Best parameters combination is ");
		for(int i=0;i<params.length;++i)
			System.out.print(params[i]+" ");
		System.out.println("\n" + "Cumulative return is " + profit);
	}
}
