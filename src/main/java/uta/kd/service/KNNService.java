package uta.kd.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import uta.kd.domain.CustomItem;

@Service
public class KNNService {

    public void doKNN(final String[] args) {
        if (args.length < 3) {
            return;
        }
        // Training data used to classify new cases
        List<List<String>> trainingData = getDataFromFile(args[0]);

        // Data that will be classified
        List<List<String>> unclassifiedData = getDataFromFile(args[1]);

        // Number of neighbors
        Integer k = Integer.parseInt(args[2]);

        // Classify the unclassified data
        for (int i = 0; i < unclassifiedData.size(); i++) {
            System.out.println(findClass(trainingData, unclassifiedData.get(i), k));
        }
    }

    // Saves data from file to a List<List<String>> array
    private List<List<String>> getDataFromFile(final String filename) {
        List<List<String>> data = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filename));
            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                String line = sc.next();
                List<String> item = Arrays.asList(line.split(","));
                data.add(item);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    // Finds the class of an item based on its k-nearest neighbors in data
    private String findClass(final List<List<String>> data, final List<String> item, final Integer k) {
        List<CustomItem> distanceList = new ArrayList<CustomItem>();
        List<String> kNearestList = new ArrayList<String>();

        // Compare unclassified item to every item in data
        for (int i = 0; i < data.size(); i++) {
            List<String> currentItem = data.get(i);
            distanceList.add(new CustomItem(getDistance(currentItem, item), currentItem.get(currentItem.size() - 1)));
        }

        // Sort the list to ascending order
        Collections.sort(distanceList);

        // Add k nearest to kNearestList
        for (int i = 0; i < k; i++) {
            kNearestList.add(distanceList.get(i).toString());
        }

        // Return the most common class in kNearestList
        return mostCommon(kNearestList);
    }

    // Compares two items and returns the euclidean distance of them
    private Double getDistance(List<String> item1, List<String> item2) {
        Double distance = 0d;

        // Calculate the euclidean distance
        for (int i = 0; i < item2.size(); i++) {
            if (isDouble(item1.get(i)) && isDouble(item2.get(i))) {
                double d1 = Double.parseDouble(item1.get(i));
                double d2 = Double.parseDouble(item2.get(i));
                distance += (d1 - d2) * (d1 - d2);
            }
            // If the attribute isn't a number add 1.0 to the distance if the values are not equal
            // TODO: Take the added value as an argument or use a different measure
            else {
                if (!item1.get(i).equals(item2.get(i))) {
                    distance += 1.0;
                }
            }
        }
        distance = Math.sqrt(distance);

        return distance;
    }

    // Helper function for comparing items
    private Boolean isDouble(final String itemValue) {
        try {
            Double.parseDouble(itemValue);
            return Boolean.TRUE;
        } catch (NumberFormatException e) {
            return Boolean.FALSE;
        }
    }

    // Helper function to find the most common class
    private <T> T mostCommon(final List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            if (max == null || entry.getValue() > max.getValue()) {
                max = entry;
            }
        }

        return max.getKey();
    }
}
