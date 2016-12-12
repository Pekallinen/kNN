import java.io.File;
import java.util.*;

// EXAMPLE USAGE:
// java kNN iris.data iris_unclassified.data 3
// java kNN car.data car_unclassified.data 5

// Data sets gathered from http://archive.ics.uci.edu/ml/index.html
public class kNN {
    public static void main(String[] args) {
    	// Training data used to classify new cases
    	List<List<String>> trainingData = getDataFromFile(args[0]);
	Preprocess.normalizeAll(trainingData);
    	// Data that will be classified
    	List<List<String>> unclassifiedData = getDataFromFile(args[1]);
	Preprocess.normalizeAll(unclassifiedData);
    	// Number of neighbors
    	int k = Integer.parseInt(args[2]);

    	// Classify the unclassified data
    	for(int i = 0; i < unclassifiedData.size(); i++) {
    		System.out.println(findClass(trainingData, unclassifiedData.get(i), k));
    	}
    }

    // Saves data from file to a List<List<String>> array
    public static List<List<String>> getDataFromFile(String filename) {
    	List<List<String>> data = new ArrayList<List<String>>();
		try {
            Scanner sc = new Scanner(new File(filename)).useDelimiter("\n");
            while(sc.hasNext()) {
            	String line = sc.next();
            	List<String> item = Arrays.asList(line.split(","));
            	data.add(item);
            }
            sc.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return data;
    }

    // Finds the class of an item based on its k-nearest neighbors in data
    public static String findClass(List<List<String>> data, List<String> item, int k) {
    	List<CustomItem> distanceList = new ArrayList<CustomItem>();
    	List<String> kNearestList = new ArrayList<String>();

    	// Compare unclassified item to every item in data
    	for(int i = 0; i < data.size(); i++) {
    		List<String> currentItem = data.get(i);
    		distanceList.add(new CustomItem(getDistance(currentItem, item), currentItem.get(currentItem.size()-1)));
    	}

    	// Sort the list to ascending order
    	Collections.sort(distanceList);

    	// Add k nearest to kNearestList
    	for(int i = 0; i < k; i++) {
    		kNearestList.add(distanceList.get(i).toString());
    	}
 		
 		// Return the most common class in kNearestList
    	return mostCommon(kNearestList);
    }

    // Compares two items and returns the euclidean distance of them
    public static double getDistance(List<String> item1, List<String> item2) {
    	List<String> distanceItem = new ArrayList<String>();
    	double distance = 0.0;
    	
    	// Calculate the euclidean distance
    	for(int i = 0; i < item2.size(); i++) {
    		if(isDouble(item1.get(i)) && isDouble(item2.get(i))) {
    			double d1 = Double.parseDouble(item1.get(i));
    			double d2 = Double.parseDouble(item2.get(i));
    			distance += (d1-d2)*(d1-d2);
    		}
    		// If the attribute isn't a number add 1.0 to the distance if the values are not equal
    		// TODO: Take the added value as an argument or use a different measure
    		else {
    			if(!item1.get(i).equals(item2.get(i))) {
    				distance += 1.0;
    			}
    		}
    	}
    	distance = Math.sqrt(distance);

    	return distance;
    }

    // Helper function for comparing items
    public static boolean isDouble(String s) {
    	try {
    		Double.parseDouble(s);
    		return true;
    	}
    	catch(NumberFormatException e) {
    		return false;
    	}
    }

    // Helper function to find the most common class
    public static <T> T mostCommon(List<T> list) {
    	Map<T, Integer> map = new HashMap<>();

    	for(T t : list) {
    		Integer val = map.get(t);
    		map.put(t, val == null ? 1 : val + 1);
    	}

    	Map.Entry<T, Integer> max = null;

    	for(Map.Entry<T, Integer> e : map.entrySet()) {
    		if(max == null  || e.getValue() > max.getValue()) {
    			max = e;
    		}
    	}

    	return max.getKey();
    }
    
}
