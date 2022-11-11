package com.nology.java.consolidation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class ConsolidationApplicationTests {

	public int sum(int a, int b) {
		return a + b;
	}
	
	@Test
	void itShouldSumNumbers() {
		// given
		int numberOne = 20;
		int numberTwo = 30;
		//when
		int result = sum(numberOne, numberTwo);
		//then 
		int expected = 50;
		assertThat(result).isEqualTo(expected);
	}

}
