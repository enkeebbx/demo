package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		try (Scanner scanner = new Scanner(new File("/Users/oliver.kyw/Desktop/demo/src/test/resources/sample.csv"))) {
			List<String> nonNumbers = new ArrayList<>();
			Integer min = Integer.MAX_VALUE;
			Integer max = Integer.MIN_VALUE;
			Integer total = 0;
			Double average = 0.0;
			Integer stdDev = 0;
			Integer median = 0;
			Integer numLines = 0;
			Integer numValidLines = 0;
			while (scanner.hasNext()) {
				String[] values = scanner.nextLine().split(",");
				numLines++;
				boolean hasNonNumber = false;
				for (String s: values) {
					try {
						Integer.parseInt(s);
					} catch (NumberFormatException e) {
						nonNumbers.add(s);
						hasNonNumber = true;
					}
				}
				if (!hasNonNumber) {
					for (int i=0; i< values.length; i++) {
						String value = values[i];
						int number = Integer.parseInt(value);
						min = Math.min(min, number);
						max = Math.max(max, number);
						total += number;
						if (i == values.length - 1) {
							average = (double) total / values.length;
							numValidLines++;
							// TODO: 표준편차랑 중간값 구하기
						}
					}
					System.out.printf("%d, %d, %d, %.4e, %d, %d%n", min, max, total, average, stdDev, median);
				}
				// 라인을 다 읽음
				min = Integer.MAX_VALUE;
				max = Integer.MIN_VALUE;
				total = 0;
				average = 0.0;
				stdDev = 0;
				median = 0;
			}
			// no more lines
			System.out.println("-------------------------------------");
			System.out.println("The total number of lines: " + numLines);
			System.out.println("The calculated lines: " + numValidLines);
			System.out.println("The error values: " + nonNumbers);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}
}
