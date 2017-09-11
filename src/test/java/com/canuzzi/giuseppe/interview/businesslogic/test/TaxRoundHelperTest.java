package com.canuzzi.giuseppe.interview.businesslogic.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.canuzzi.giuseppe.interview.businesslogic.RoundValue;
import com.canuzzi.giuseppe.interview.businesslogic.TaxRoundHelper;

public class TaxRoundHelperTest {

	private final RoundingMode roundedMode = RoundingMode.UP;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void roundUpNearest_NearestTo5Cents_CorrectlyRounded1() {
		//Setup
		double valueToRound = 1.499;
		//Execise
		BigDecimal roundedResult = TaxRoundHelper.roundUpNearest(new BigDecimal(valueToRound),
																 new BigDecimal(String.valueOf(RoundValue.NEAREST_TO_5_CENTS)), 
																 roundedMode);
		
		//Verify
		assertThat(roundedResult.doubleValue()).isEqualTo(Double.valueOf("1.5"));
	}
	
	@Test
	public void roundUpNearest_NearestTo5Cents_CorrectlyRounded2() {
		//Setup
		double valueToRound = 7.125;
		//Execise
		BigDecimal roundedResult = TaxRoundHelper.roundUpNearest(new BigDecimal(valueToRound),
																 new BigDecimal(String.valueOf(RoundValue.NEAREST_TO_5_CENTS)), 
																 roundedMode);
		
		//Verify
		assertThat(roundedResult.doubleValue()).isEqualTo(Double.valueOf("7.15"));
	}
	
	@Test
	public void roundUpNearest_NearestTo5Cents_CorrectlyRounded3() {
		//Setup
		double valueToRound = 4.1985;
		
		//Execise
		BigDecimal roundedResult = TaxRoundHelper.roundUpNearest(new BigDecimal(valueToRound),
																 new BigDecimal(String.valueOf(RoundValue.NEAREST_TO_5_CENTS)), 
																 roundedMode);
		
		//Verify
		assertThat(roundedResult.doubleValue()).isEqualTo(Double.valueOf("4.2"));
	}
	
	@Test
	public void roundUpNearest_NearestTo5Cents_ValueToRoundNull_ExceptionRaised() {
		//Setup
		BigDecimal valueToRound = null;
		
		//Verify
		assertThatThrownBy(
					//Exercise
					() -> TaxRoundHelper.roundUpNearest(valueToRound,new BigDecimal(String.valueOf(RoundValue.NEAREST_TO_5_CENTS)),roundedMode)
				).isInstanceOf(NullPointerException.class).hasMessage("Value to round is null");

	}
	
	@Test
	public void roundUpNearest_NearestTo5Cents_IncrementForRoundIsNull_ExceptionRaised() {
		//Setup
		double valueToRound = 4.1985;
		BigDecimal increment = null;
		
		//Verify
		assertThatThrownBy(
					//Exercise
					() -> TaxRoundHelper.roundUpNearest(new BigDecimal(valueToRound),increment,roundedMode)
				).isInstanceOf(NullPointerException.class).hasMessage("Increment to round nearest to is null");

	}
	
	@Test
	public void roundUpNearest_NearestTo5Cents_RoundingModeIsNull_ExceptionRaised() {
		//Setup
		double valueToRound = 4.1985;
		RoundingMode roundMode = null;
		
		//Verify
		assertThatThrownBy(
					//Exercise
					() -> TaxRoundHelper.roundUpNearest(new BigDecimal(valueToRound),new BigDecimal(String.valueOf(RoundValue.NEAREST_TO_5_CENTS)),roundMode)
				).isInstanceOf(NullPointerException.class).hasMessage("Rounding mode necessary for the algorithm not specified");

	}
	
	@Test
	public void roundUpNearest_NearestTo5Cents_ValueToRoundIsZero_ZeroReturned() {
		
		// Setup
		double valueToRound = 0;
		
		// Execise
		BigDecimal roundedResult = TaxRoundHelper.roundUpNearest(new BigDecimal(valueToRound),
				new BigDecimal(String.valueOf(RoundValue.NEAREST_TO_5_CENTS)), roundedMode);

		// Verify
		assertThat(roundedResult.doubleValue()).isEqualTo(0);
		
	}
	
	@Test
	public void roundUpNearest_NearestTo5Cents_IncrementIsZero_NoRoundPerformed() {
		
		// Setup
		double valueToRound = 1.499;
		
		// Execise
		BigDecimal roundedResult = TaxRoundHelper.roundUpNearest(new BigDecimal(valueToRound),
				BigDecimal.ZERO, roundedMode);

		// Verify
		assertThat(roundedResult.doubleValue()).isEqualTo(valueToRound);
		
	}
	

}
