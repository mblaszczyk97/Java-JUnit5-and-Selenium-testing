package com.maventest.libraryRent;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParametrizedTest {

	@ParameterizedTest
	@ValueSource(doubles = {1.0, 3.2, 5.52432, 3.12, 15.9})
	public void testPrimeNumberChecker(double number) {
		Library li = new Library();
		 assertThat(li.days(number)).isInstanceOf(Integer.class);
	}
	
}
