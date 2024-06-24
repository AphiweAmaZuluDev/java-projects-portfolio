package MergeSort;

import java.util.Arrays;

public class MergeSort {
	// Merges two subarrays of array[]
    // First subarray is array[left..mid]
    // Second subarray is array[mid + 1..right]
	public static int[] myArray = {170, 45, 75, 90, 802, 24, 2, 66};
	
	static void merge(int array[], int left_index, int mid_index, int right_index) {
		// find the sizes of the 2 subarrays to be merged
		int size_left = mid_index - left_index + 1;
		int size_right = right_index - mid_index;
		
		// create the 2 subarrays
		int left_array[] = new int[size_left];
		int right_array[] = new int[size_right];
		
		// populate the 2 subarrays accordingly
		
		// left array:
		for (int i = 0; i < size_left; ++i) {
			left_array[i] = array[left_index + i];
		}
		// right array:
		for (int j = 0; j < size_right; ++j) {
			right_array[j] = array[mid_index + 1 + j];
		}
		
		// merge the 2 subarrays
		int i = 0, j = 0;
		int k = left_index;
		
		while(i < size_left && j < size_right) {
			if(left_array[i] <= right_array[j]) {
				array[k] = left_array[i];
				i++;
			} else {
				array[k] = right_array[j];
				j++;
			}
			k++;
		}
		
		// copy the remaining elements of left_array
		while(i < size_left) {
			array[k] = left_array[i];
			i++;
			k++;
		}
		
		// copy the remaining elements of right_array
		while(j < size_right) {
			array[k] = right_array[j];
			i++;
			k++;
		}
	}
	
	// main function that sorts array[left_index...right_index]
	// using merge()
	static void mergeSort(int[] array, int left_index, int right_index) {
		if (left_index < right_index) {
			// find the mid-point
			int mid_index = (left_index + right_index) / 2;
			
			// Sort the first and second halves
			mergeSort(array, left_index, mid_index);
			mergeSort(array, mid_index + 1, right_index);
			
			// merge the sorted halves
			merge(array, left_index, mid_index, 
					right_index);
		}
	}
	
	// utility function to print an array
	static void printArray(int[] array) {
		int n = array.length;
		for(int i = 0; i < n; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void main(String[] args) {
		int n = myArray.length;
		mergeSort(myArray, 0, n - 1);
		printArray(myArray);
	}
}
