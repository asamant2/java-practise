package com.aniket.practise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SequenceOfNumbers {

	private List<Integer> numList = new ArrayList<>();
	private boolean listUpdated = false;
	private int highestConsNumber;
	
	private Comparator<Integer> comparator = (num1, num2) -> num1 - num2;
	
	public void addNumber(int num) {
		if (!numList.contains(num)) {
			numList.add(num);
			Collections.sort(numList, comparator);
			listUpdated = true;
		}
	}
	
	public int longestConsecutive() {
		if(listUpdated) {
			for (int loop = 0; loop < numList.size()-1; loop++) {
				highestConsNumber = numList.get(loop);
				if(numList.get(loop+1) != numList.get(loop) + 1) {
					if(loop == numList.size()-2) {
						highestConsNumber = numList.get(loop+1);
					}
					break;
				}
			}
		}
		listUpdated = false;
		return highestConsNumber;
	}
}
