
public class MACDv2 extends Strategy{

	int lengthHistData;
	int emaShort, emaLong, emaSignal;
	EMA emaShortTS, emaLongTS, emaSignalTS;

	public MACDv2(String[] params){
		emaShort = Integer.parseInt(params[0]);
		emaLong = Integer.parseInt(params[1]);
		emaSignal = Integer.parseInt(params[2]);
		emaShortTS = new EMA(emaShort);
		emaLongTS = new EMA(emaLong);
		emaSignalTS = new EMA(emaSignal);		
	}

	@Override
	public int addTick(Double newTick) {
		Double valueShort = null, valueLong = null, valueSignal = null, MACDline = null;
		valueShort = emaShortTS.iterate(newTick);
		valueLong = emaLongTS.iterate(newTick);
		if((valueShort != null) && (valueLong != null)){
			MACDline = valueShort - valueLong;
			valueSignal = emaSignalTS.iterate(MACDline);
			if(valueSignal == null)
				return 0;
			int iPart = (int)(MACDline-valueSignal);
			if(iPart != 0)
				return iPart;
			return MathOp.sign(MACDline-valueSignal);	
		}
		return 0;
	}

}
