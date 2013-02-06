import java.util.ArrayList;


public class MACD extends Strategy{

	int lengthHistData;
	int emaShort, emaLong, emaSignal;
	ArrayList<Double> historicalData = new ArrayList<Double>();
	EMA emaShortTS, emaLongTS, emaSignalTS;
	ArrayList<Double> MACDline = new ArrayList<Double>();
	public MACD(String[] params){
		emaShort = Integer.parseInt(params[0]);
		emaLong = Integer.parseInt(params[1]);
		emaSignal = Integer.parseInt(params[2]);
		emaShortTS = new EMA(emaShort);
		emaLongTS = new EMA(emaLong);
		emaSignalTS = new EMA(emaSignal);
		
	}

	@Override
	public int addTick(Double newTick) {
		Double valueShort = null, valueLong = null, valueSignal = null;
		historicalData.add(newTick);
		
		if(historicalData.size() == emaShort)
			emaShortTS.previous = MathOp.avg(historicalData);
		else if(historicalData.size() > emaShort)
			valueShort = emaShortTS.calculateEMA(newTick);
		
		
		if(historicalData.size() == emaLong)
			emaLongTS.previous = MathOp.avg(historicalData);
		else if (historicalData.size() > emaLong){
			valueLong = emaLongTS.calculateEMA(newTick);
			MACDline.add(valueShort - valueLong);
		}
		
		if(MACDline.size() == emaSignal)
			emaSignalTS.previous = MathOp.avg(MACDline);
		else if(MACDline.size() > emaSignal){
			valueSignal = emaSignalTS.calculateEMA(MACDline.get(MACDline.size()-1));
			return MathOp.sign(MACDline.get(MACDline.size()-1)-valueSignal);
		}
		
		return 0;
	}

}
