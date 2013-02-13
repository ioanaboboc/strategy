import java.util.ArrayList;


public class EMA {
	private double k;
	private Double previous = null;
	private ArrayList<Double> history;
	private int days;
	public EMA(int days){
		k = 2.0/(days+1);
		history = new ArrayList<Double>();
		this.days = days;
	}
	
	//Calculate current value of EMA: k*Price(t)+(1-k)*EMA(t-1)
	public Double iterate(Double val){
		if(history.size()<days){
			history.add(val);
			return null;
		}
		else if(history.size()==days && previous==null){
			previous=MathOp.avg(history);
			return previous;
		}
		previous = new Double (k*val+(1-k)*previous);
		return previous;
	}
}
