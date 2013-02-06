
public class EMA {
	public double k;
	public Double previous = new Double(0);
	
	public EMA(int days){
		k = 2.0/(days+1);
	}
	
	//Calculate current value of EMA: k*Price(t)+(1-k)*EMA(t-1)
	public Double calculateEMA(Double val){
		Double result = new Double (k*val+(1-k)*previous);
		previous = result;
		return result;
	}
}
