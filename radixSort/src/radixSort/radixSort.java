package radixSort;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class radixSort {
	
	static int[] myArray = {170, 45, 75, 90, 802, 24, 2, 66};
	
//	Below is the radixSort function.
	static int[] radixSortFun(int[] array) {
		ArrayList<Integer> arrayList = new ArrayList<>(array.length);
		int maxVal = 0;
		int exp = 1;
		int value = 0;
		int radixIndex = 0;

//		create an ArrayList version of the argument array
		for(int i = 0; i < array.length; i++) {
			arrayList.add(array[i]);
		}
		
//		create a new 2D ArrayList with a length equal to the 
//		length of the argument array.
		ArrayList<ArrayList<Integer>> radixArray = new ArrayList<>(array.length);
		for(int i = 0; i < array.length; i++) {
			radixArray.add(new ArrayList<>());
		}
		
//		get the maximum value of the argument array/arrayList.
		maxVal = Collections.max(arrayList);
		
//		Outer loop that runs as many times as there are digits in the 
//		maximum value.		
		while(Math.floorDiv(maxVal, exp) > 0) {
//			Inner loop that puts values into the correct index in the 
//			radix array. 
			for(int i = 0; i < arrayList.size(); i++) {
				value = arrayList.get(i);
				radixIndex = Math.floorDiv(value, exp) % 10;
				if(radixIndex >= arrayList.size()) {
					radixArray.get(arrayList.size() - 1).add(value);
				} else {
					radixArray.get(radixIndex).add(value);
				}
			}
			arrayList.clear();
//			Inner loop that returns values to the arrayList
//			sorted according to the radixIndex
			for(int j = 0; j < radixArray.size(); j++) {
				while(radixArray.get(j).size() > 0) {
					int k = 0;
					arrayList.add(radixArray.get(j).get(k));
					radixArray.get(j).remove(k);
					k++;
				}
			}
			
			exp*= 10;
		}
		int[] sortedArray = new int[arrayList.size()];
		for(int i = 0; i < arrayList.size(); i++) {
			sortedArray[i] = arrayList.get(i);
		}
		return sortedArray;
	}
	
//	Main Method
	public static void main(String[] args) {
		System.out.print(Arrays.toString(radixSortFun(myArray)));
	}
}
