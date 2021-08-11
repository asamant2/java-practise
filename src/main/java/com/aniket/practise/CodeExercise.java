package com.aniket.practise;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodeExercise {
	
	public static void main(String[] args) {
		String strNumbers = "";
		Integer[] intArray = null;
		SequenceOfNumbers seq = new SequenceOfNumbers();
		System.out.println("Questions :\n\n");
		System.out.println("1:\r\n" + 
				"Having an array of integers, write a function to find and return 2 of them that have the \r\n" + 
				"difference equal to x. If there is no solution, return NULL instead.\r\n" + 
				"\r\n" + 
				"2:\r\n" + 
				"The cost of a cryptocurrency on each day is given in an array, \r\n" + 
				"find the max profit that you can make by buying and selling in those days.\r\n" + 
				"\r\n" + 
				"3:\r\n" + 
				"Given a square 2D array of letters (NxN), find all words from a given dictionary \r\n" + 
				"(like a word search puzzle). You can go all possible 8 directions \r\n" + 
				"(up, down, left, right, all diagonals) but cannot reuse the same positions, \r\n" + 
				"you can also start from any position of the matrix:\r\n" + 
				"\r\n" + 
				"4:\r\n" + 
				"Design a class that offers two public functions/methods,\r\n" + 
				"addNumber(int num): return void.\r\n" + 
				"longestConsecutive(): return the length of the longest consecutive elements sequence \r\n" + 
				"from the numbers num that were added by addNumber(int num).");
		System.out.println("\n\nChoose question choice or q to quit: ");
		Scanner intScanner = new Scanner(System.in);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> intScanner.close()));
		while(true) {
			String strChoice = intScanner.next();
			if("q".equals(strChoice)) {
				System.exit(1);
			}
			int qChoice = Integer.parseInt(strChoice);
			switch(qChoice) {
				case 1 : 
					System.out.println("Enter the comma separated values : ");
					//intScanner.nextLine();
					strNumbers = intScanner.next();
					System.out.println("Enter the difference of numbers : ");
					int difference = intScanner.nextInt();
					intArray = transformStringToIntArray(strNumbers);
					printMatchedNumbers(intArray, difference);
					System.out.println("\n\nContinue Testing");
					System.out.println("Choose question choice or q to quit: ");
					continue;
				case 2 :
					System.out.println("Enter the comma separated values for currency values : ");
					//intScanner.nextLine();
					strNumbers = intScanner.next();
					intArray = transformStringToIntArray(strNumbers);
					printBuySellDaysForMaxProfit(intArray);
					System.out.println("\n\nContinue Testing");
					System.out.println("Choose question choice or q to quit: ");
					continue;
				case 3:
					System.out.println("Not yet developed...");
					System.out.println("\n\nContinue Testing");
					System.out.println("Choose question choice or q to quit: ");
					continue;
				case 4:
					System.out.println("Enter sequence of numbers : ");
					//intScanner.nextLine();
					strNumbers = intScanner.next();
					Stream
						.of(strNumbers.split(","))
						.map(strNumber -> strNumber.trim())
						.map(Integer::parseInt)
						.forEach(seq::addNumber);
					System.out.println("Highest consecutive number : "+seq.longestConsecutive());
					System.out.println("\n\nContinue Testing");
					System.out.println("Choose question choice or q to quit: ");
					continue;
				default:
					System.out.println("\n\nContinue Testing");
					System.out.println("Choose question choice or q to quit: ");
			}
		}
	}
	
	private static Integer[] transformStringToIntArray(String numbers) {
		return Stream
				.of(numbers.split(","))
				.map(strNumber -> strNumber.trim())
				.map(Integer::parseInt)
				.collect(Collectors.toList())
				.toArray(new Integer[0]);
	}
	
	private static void printMatchedNumbers(Integer[] nums, int difference) {
		List<Integer> indicesSearched = new ArrayList<>();
		StringBuilder positions = new StringBuilder("");
		int addedResult, subtractedResult;
		for (int outerLoop = 0; outerLoop < nums.length - 1; outerLoop++) {
			addedResult = nums[outerLoop]+difference;
			subtractedResult = nums[outerLoop]-difference;
			for(int innerLoop = outerLoop+1; innerLoop < nums.length; innerLoop++) {
				if(indicesSearched.contains(innerLoop)) {
					continue;
				}
				if (nums[innerLoop] == addedResult || nums[innerLoop] == subtractedResult) {
					positions.append(String.format("( %d , %d ) %n", nums[outerLoop], nums[innerLoop]));
					indicesSearched.add(innerLoop);
					break;
				}
			}
			indicesSearched.add(outerLoop);
		}
		if (positions.length() > 0) {
			System.out.println("Matches found : \n"+positions.toString());
		} else {
			System.out.println("No Match Found");
		}
	}
	
	private static void printBuySellDaysForMaxProfit(Integer[] prices) {
		List<Integer> marginalPriceList = new ArrayList<>();
		StringBuilder maxProfitDaysBuilder = new StringBuilder();
		List<Integer> positionList = new ArrayList<>();
		if(prices.length >= 3) {
			if (prices[0] < prices[1]) {
				marginalPriceList.add(prices[0]);
				positionList.add(0);
			}
			for (int loop = 1; loop < prices.length - 1; loop++) {
				if((prices[loop] > prices[loop-1] && prices[loop] > prices[loop+1])
						|| (prices[loop] < prices[loop-1] && prices[loop] < prices[loop+1])) {
					marginalPriceList.add(prices[loop]);
					positionList.add(loop);
				}
			}
			if(prices[prices.length-1] > prices[prices.length-2]) {
				marginalPriceList.add(prices[prices.length-1]);
				positionList.add(prices.length-1);
			}
			int prevBuyingPrice = marginalPriceList.get(0);
			int prevBuyingPricePos = positionList.get(0);
			int prevSellingPrice = marginalPriceList.get(1);
			int prevSellingPricePos = positionList.get(1);
			int esteemedProfit = prevSellingPrice - prevBuyingPrice;
			for (int loop = 2; loop < marginalPriceList.size(); loop+=2) {
				int price = marginalPriceList.get(loop);
				if(price > (prevBuyingPrice+esteemedProfit/2) && marginalPriceList.get(loop+1) > prevSellingPrice ) {
					prevSellingPrice = marginalPriceList.get(loop+1);
					prevSellingPricePos = positionList.get(loop+1);
					esteemedProfit = prevSellingPrice - prevBuyingPrice;
				} else if(price <= (prevBuyingPrice+esteemedProfit/2)) {
					if(maxProfitDaysBuilder.length() > 0) {
						maxProfitDaysBuilder.append(",");
					}
					maxProfitDaysBuilder.append(String.format(" ( Buying on day %d , Selling on %d ) ", prevBuyingPricePos+1, prevSellingPricePos+1));
					prevBuyingPrice = price;
					prevBuyingPricePos = positionList.get(loop);
					prevSellingPrice = marginalPriceList.get(loop+1);
					prevSellingPricePos = positionList.get(loop + 1);
					esteemedProfit = prevSellingPrice - prevBuyingPrice;
				}
			}
			maxProfitDaysBuilder.append(String.format(", ( Buying on day %d , Selling on day %d ) ", prevBuyingPricePos+1, prevSellingPricePos+1));
			if(maxProfitDaysBuilder.length() > 0) {
				System.out.println(maxProfitDaysBuilder.toString());
			} else {
				System.out.println("No Profit on the market price");
			}
		} else if (prices.length == 1 ) {
			System.out.println("Need more than one day market value to calculate profit");
		} else if (prices.length == 2 ) {
			if(prices[1] > prices[0]) {
				System.out.printf(" ( Buying on day %d , Selling on day %d ) ", 1, 2);
			} else {
				System.out.println("No Profit on the market price");
			}
		}
	}
	
	

}
