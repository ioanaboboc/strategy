import java.util.HashMap;


public class strategy {
	private HashMap<String, Product> productMap = new HashMap<String, Product>();
	
	/*public strategy(String stratID, Product p, double [] historicalData){
		registerProduct(stratID, p);
		createProduct(stratID, historicalData);
	}*/
	
	public void registerProduct(String stratID, Product p){
		productMap.put(stratID, p);
	}
	
	public void createProduct(String stratID, double [] historicalData, String [] params){
		Product p = (productMap.get(stratID)).createProduct();
		p.addHist(historicalData);
		p.initializeParameters(params);
	}
	
	public void addTick(String stratID, float newTick){
		Product p = productMap.get(stratID);
		p.addTick(newTick);
	}
}
