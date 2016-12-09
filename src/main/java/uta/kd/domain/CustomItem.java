package uta.kd.domain;

//Custom item to help sorting the distancelist
public class CustomItem implements Comparable<CustomItem> {
 private double item_distance;
 private String item_class;

 public CustomItem(double item_distance, String item_class) {
     this.item_distance = item_distance;
     this.item_class = item_class;
 }

 @Override
 public int compareTo(CustomItem other) {
     return Double.valueOf(item_distance).compareTo(Double.valueOf(other.item_distance));
 }

 @Override
 public String toString() {
     return item_class;
 }
}
