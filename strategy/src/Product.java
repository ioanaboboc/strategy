
abstract class Product {
	public double [] historicalData;
	public abstract Product createProduct();
	public abstract void addHist(double [] historicalData2);
	public abstract void addTick(float newTick);
	public abstract int [] calculateStrategy();
	public abstract void initializeParameters(String [] params);
}
