import java.util.ArrayList;


public class EMA {
	private double k;
	private Double previous = null;
	private int days;
	private int in_days;
	public EMA(int days){
		k = 2.0/(days+1);
		in_days = 0;
		this.days = days;
	}
	
	//Calculate current value of EMA: k*Price(t)+(1-k)*EMA(t-1)
	public Double iterate(Double val){
		if(in_days<days){
			if(previous == null)
				previous=(val)/(++in_days);
			else
				previous=(previous*in_days+val)/(++in_days);
			return previous;
		}
		else
			previous = new Double (k*val+(1-k)*previous);
		return previous;
	}
}
