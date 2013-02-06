import java.util.ArrayList;


public class MathOp {

	public static Double avg(ArrayList<Double> array) {
		Double sum = new Double(0);
		int size = array.size();
		for(int i=0; i<size; ++i)
			sum+=array.get(i);
		return sum/size;
	}

	public static int sign(double d) {
		return d>0?1:-1;
	}

}
