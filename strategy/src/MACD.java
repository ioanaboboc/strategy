
public class MACD extends Product{

	int lengthHistData;
	int emaShort, emaLong, emaSignal;
	double [] historicalData;
	
	@Override
	public Product createProduct() {
		return new MACD();
	}

	@Override
	public void addHist(double[] historicalData2) {
		historicalData = new double[2*historicalData2.length];
		historicalData = historicalData2;
		lengthHistData = historicalData2.length;
	}

	@Override
	public void addTick(float newTick) {
		historicalData[lengthHistData] = newTick;
		++lengthHistData;
	}

	@Override
	public void initializeParameters(String [] params){
		emaShort = Integer.parseInt(params[0]);
		emaLong = Integer.parseInt(params[1]);
		emaSignal = Integer.parseInt(params[2]);
	}

	@Override
	public int[] calculateStrategy() {
		int i;

		//Calculate MACD line: EMA[stockPrices,12] – EMA[stockPrices,26]
		EMA tseries = new EMA(emaShort, historicalData,lengthHistData);
		double [] EMAShortSeries = tseries.calculateTS_EMA();
		tseries = new EMA(emaLong,historicalData,lengthHistData);
		double [] EMALongSeries = tseries.calculateTS_EMA();
		double[] MACD = new double[lengthHistData];
		for(i=0; i<lengthHistData; ++i){
			MACD[i] = EMAShortSeries[i]-EMALongSeries[i];
			System.out.println('a');
		}
		//Calculate Signal line: EMA[MACD,9]
		tseries = new EMA(emaSignal,MACD,lengthHistData);
		double [] signal = tseries.calculateTS_EMA();
		
		int MACDlng = MACD.length;
		int [] s = new int[MACDlng];
		for(i=0; i<MACDlng; i++){
			if(MACD[i]>signal[i])
				s[i] = 1;
			else if (MACD[i]<signal[i])
				s[i] = -1;
		}
		return s;
	}
}
