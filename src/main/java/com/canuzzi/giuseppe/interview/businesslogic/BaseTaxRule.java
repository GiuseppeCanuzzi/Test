package com.canuzzi.giuseppe.interview.businesslogic;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.assertj.core.util.Preconditions;

import com.canuzzi.giuseppe.interview.domain.entity.Category;

public class BaseTaxRule implements ITaxRule<TaxedGood> {

	//TODO Retrieve its value from DB to inject inside the rule
	private final static BigDecimal baseTaxRate = new BigDecimal(10);
	
	@Override
	public String getDescription() {
		
		//TODO move description inside a const
		return String.format("This rule is used to calculate tax on all goods except food, medical and book");
		
	}
	
		
	@Override
	public void apply(TaxedGood taxableGood) throws TaxCalculationException {
		
		Preconditions.checkNotNull(taxableGood, "Cannot apply tax to a null good");
		
		BigDecimal basePrice = taxableGood.getBasePrice();
		
		if(basePrice.signum() == -1) {
			throw new TaxCalculationException(String.format(
														"Negative base price is invalid for tax calculation of %s : %s", 
														taxableGood.getName(),
														taxableGood.getDescription())
											);
		}

		if (basePrice.signum() == 0) {
			//log that the the object has a base cost of zero
			return;
		}
		
		BigDecimal currentTaxedPrice = taxableGood.getTaxedPrice();
		
		if(currentTaxedPrice.signum() == 0) {
			taxableGood.setTaxedPrice(new BigDecimal(basePrice.doubleValue()));
		}
		
		if(Category.OTHER.equals(taxableGood.getCategory())) {
			
			//BigDecimal for precision
			//Log that rule is being applied because the good evaluated is of generic type
			BigDecimal previousTaxPercentageApplied = taxableGood.getTaxPercentageApplied();	
			BigDecimal newPercentageToApply = previousTaxPercentageApplied.add(baseTaxRate);	
			taxableGood.setTaxPercentageApplied(newPercentageToApply);	
			
			BigDecimal taxValue = basePrice.multiply(newPercentageToApply).divide(new BigDecimal(100));
			BigDecimal roundedTaxValue = TaxRoundHelper.roundUpNearest(taxValue,new BigDecimal(String.valueOf(RoundValue.NEAREST_TO_5_CENTS)), RoundingMode.UP);		
			BigDecimal finalTaxedPrice = roundedTaxValue.add(basePrice);  
			taxableGood.setTaxedPrice(finalTaxedPrice);
	
		}	

	}


}
