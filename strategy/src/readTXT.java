import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class readTXT {

	public static ArrayList<Double> read(String string) throws NumberFormatException, IOException {
		 String line; 
		 String [] tokens; 
		 ArrayList<Double> valuesTXT = new ArrayList<Double>();
		 BufferedReader br = new BufferedReader(new FileReader(string)); 
		 line = br.readLine(); 
		 while((line = br.readLine()) != null) { 
			 tokens = line.split(","); 
			 valuesTXT.add(Double.parseDouble(tokens[4])); 
		 }  
		 return valuesTXT;
	}

}
