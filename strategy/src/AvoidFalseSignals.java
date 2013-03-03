
public class AvoidFalseSignals extends Strategy{
	int lengthHistData;
	int emaShort, emaLong, emaSignal;
	EMA emaShortTS, emaLongTS, emaSignalTS;
	int direction = 0;//1 if the trend is bullish -> buy in the future sign;-1->trend bearish, sell
	int counterDays=0;
	int oldSig=0;
	
	public AvoidFalseSignals(String[] params){
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
			if(MathOp.sign(MACDline-valueSignal)==1 && direction==1){
				if(counterDays!=2){
					counterDays++;
				}
				else if(counterDays==2){
					//counterDays=0;
					oldSig=1;
				}
			}
			else if(MathOp.sign(MACDline-valueSignal)==1 && direction!=1){
				direction=1; //change direction
				counterDays=1; //maintain same position 3 days in a row
			}
			//reverse strategy for selling
			else if(MathOp.sign(MACDline-valueSignal)==-1 && direction==-1){
				if(counterDays!=2){
					counterDays++;
				}
				else if(counterDays==2){
					//counterDays=0;
					oldSig=-1;
				}
			}
			else if(MathOp.sign(MACDline-valueSignal)==-1 && direction!=-1){
				direction=-1; //change direction
				counterDays=1; //maintain same position 3 days in a row
			}
			else
				System.out.println("Error: Other case?");
		}
		
		return oldSig;
	}

}
