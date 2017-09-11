package com.canuzzi.giuseppe.interview.businesslogic;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.assertj.core.util.Preconditions;

public class TaxImportRule implements ITaxRule<TaxedGood> {

	//TODO Refactor on common code between rules 
	
	// TODO Retrieve its value from DB to inject inside the rule
	private final static BigDecimal importTaxRate = new BigDecimal(5);

	@Override
	public String getDescription() {
		
		//TODO move description inside a const
		return String.format("This rule is used to calculate tax on all imported goods");
		
	}
	
	@Override
	public void apply(TaxedGood taxableGood) throws TaxCalculationException {

		//TODO Check null input
		Preconditions.checkNotNull(taxableGood, "Cannot apply tax to a null good");

		BigDecimal basePrice = taxableGood.getBasePrice();

		//TODO Check operand sign
		if (basePrice.signum() == -1) {
			throw new TaxCalculationException(
					String.format("Negative base price is invalid for tax calculation of %s : %s",
							taxableGood.getName(), taxableGood.getDescription()));
		}

		if (taxableGood.getBasePrice().signum() == 0) {
			return;
		}

		BigDecimal currentTaxedPrice = taxableGood.getTaxedPrice();
		
		if(currentTaxedPrice.signum() == 0) {
			taxableGood.setTaxedPrice(new BigDecimal(basePrice.doubleValue()));
		}
		
		//TODO Tax logic for import
		if (taxableGood.isImport()) {
			
			BigDecimal previousTaxPercentageApplied = taxableGood.getTaxPercentageApplied();
			BigDecimal newPercentageToApply = previousTaxPercentageApplied.add(importTaxRate);
			taxableGood.setTaxPercentageApplied(newPercentageToApply);
			
			BigDecimal taxValue = basePrice.multiply(newPercentageToApply).divide(new BigDecimal(String.valueOf(100)));
			BigDecimal roundedTaxValue = TaxRoundHelper.roundUpNearest(taxValue, new BigDecimal(String.valueOf(RoundValue.NEAREST_TO_5_CENTS)),RoundingMode.UP);
			BigDecimal finalTaxedPrice = roundedTaxValue.add(basePrice);		
			taxableGood.setTaxedPrice(finalTaxedPrice);

		}
		

	}

}
