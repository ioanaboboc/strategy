import java.util.ArrayList;


public class ZeroCrossover extends Strategy{

	int lengthHistData;
	int emaShort, emaLong;
	ArrayList<Double> historicalData = new ArrayList<Double>();
	EMA emaShortTS, emaLongTS;
	public ZeroCrossover(String[] params){
		emaShort = Integer.parseInt(params[0]);
		emaLong = Integer.parseInt(params[1]);
		emaShortTS = new EMA(emaShort);
		emaLongTS = new EMA(emaLong);
	}
	@Override
	public int addTick(Double newTick) {
		Double valueShort = null, valueLong = null;
		valueShort = emaShortTS.iterate(newTick);
		valueLong = emaLongTS.iterate(newTick);
		if(valueShort != null && valueLong != null)
			return MathOp.sign(valueShort - valueLong);
		return 0;
	}

}
