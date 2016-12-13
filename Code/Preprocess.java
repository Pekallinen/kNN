import java.util.*;

public class Preprocess {
	// Normalizes qualitative data of an attribute
	// as input: data, attribute Id	
	public static void normalize(List<List<String>> dataTrain, List<List<String>> dataTest, int attribute){
		double max = 0;
		double min = 0;
		double currAttr = 0;
		// dataTrain
		for(int i = 0; i < dataTrain.size(); i++) { 
    		List<String> currentItem = dataTrain.get(i);
    		//System.out.println(currentItem.get(attribute));
    		//System.out.println(i);
    		if(isDouble(currentItem.get(attribute))){
    			currAttr = Double.parseDouble(currentItem.get(attribute));
    			if(max < currAttr)	max = currAttr;
    			else if(min > currAttr)	min = currAttr;
    		}
    	}
		// dataTest
		for(int i = 0; i < dataTest.size(); i++) {
    		List<String> currentItem = dataTrain.get(i);
    		//System.out.println(currentItem.get(attribute));
    		//System.out.println(i);
    		if(isDouble(currentItem.get(attribute))){
    			currAttr = Double.parseDouble(currentItem.get(attribute));
    			if(max < currAttr)	max = currAttr;
    			else if(min > currAttr)	min = currAttr;
    		}
    	}
		
		double range = max - min;
		// dataTrain normalization
		for(int i = 0; i < dataTrain.size(); i++) {
    		List<String> currentItem = dataTrain.get(i);
    		if(isDouble(currentItem.get(attribute))){
    			double normalizedValue = (Double.parseDouble(currentItem.get(attribute))-min)/range;
    			String weightedValue = Double.toString(normalizedValue);
    			currentItem.set(attribute, weightedValue );	
    		}
		}
		// dataTest normalization
		for(int i = 0; i < dataTest.size(); i++) {
    		List<String> currentItem = dataTest.get(i);
    		if(isDouble(currentItem.get(attribute))){
    			double normalizedValue = (Double.parseDouble(currentItem.get(attribute))-min)/range;
    			String weightedValue = Double.toString(normalizedValue);
    			currentItem.set(attribute, weightedValue );	
    		}
		}
	}
	// this functions normalizes all attributes that could be normalized
	public static int normalizeAll(List<List<String>> dataTrain, List<List<String>> dataTest){
		int nbAttr = dataTrain.get(0).size();// number of attributes
		for(int attr=0; attr<nbAttr; attr++){
			normalize(dataTrain, dataTest, attr);
		}
		return 1;
	}
	
	static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	// Thus function attributes a desired weight to a data attribute
	public static void weigh(List<List<String>> data, double weight, int attribute){
		for(int i = 0; i < data.size(); i++) { 
    		List<String> currentItem = data.get(i);
    		if(isDouble(currentItem.get(attribute))){
    			String weightedValue = Double.toString(weight*Double.parseDouble(currentItem.get(attribute)));
    			currentItem.set(attribute, weightedValue );	
    		}
		}
	}
}
