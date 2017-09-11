package com.canuzzi.giuseppe.interview.businesslogic;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.assertj.core.util.Preconditions;

public class TaxRoundHelper {

	public static BigDecimal roundUpNearest(BigDecimal value, BigDecimal increment, RoundingMode roundingMode) {
		
		Preconditions.checkNotNull(value, "Value to round is null");
		Preconditions.checkNotNull(increment, "Increment to round nearest to is null");
		Preconditions.checkNotNull(roundingMode, "Rounding mode necessary for the algorithm not specified");
		
		if (increment.signum() == 0) {
			return value;
		} else {
			BigDecimal divided = value.divide(increment, 0, roundingMode);
			BigDecimal result = divided.multiply(increment);
			return result;
		}
	}

}
