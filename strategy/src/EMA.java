
public class EMA {
	private double k;
	private double [] values;
	private int days;
	private int lengthSeries;
	
	public EMA(int days, double [] values, int length){
		k = 2.0/(days+1);
		this.values = values;
		this.days = days;
		lengthSeries = length;
	}
	
	//Calculate current value of EMA: k*Price(t)+(1-k)*EMA(t-1)
	public double calculateEMA(double val, double ema){
		return k*val+(1-k)*ema;
	}
	
	//Calculate the time series of EMA considering prices (closed or adjusted)
	public double [] calculateTS_EMA(){
		int i=0;
		double [] ema = new double[lengthSeries];
		ema[0] = values[0];

		//first number of days, we calculate the moving average
		for (i=1;i<days;++i){
			ema[i] = ema[i-1]*i/(i+1)+values[i]/(i+1);
		}

		//next period, calculate EMA
		for (i=days;i<lengthSeries;++i){
			ema[i] = calculateEMA(values[i],ema[i-1]);
		}
		return ema;
	}
	
	//print the prices 
	public void printVal(){
		for (int i=0;i<values.length; ++i)
			System.out.println(values[i]);
	}
	
}
