package com.canuzzi.giuseppe.interview.businesslogic.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.canuzzi.giuseppe.interview.businesslogic.BaseTaxRule;
import com.canuzzi.giuseppe.interview.businesslogic.TaxCalculationException;
import com.canuzzi.giuseppe.interview.businesslogic.TaxedGood;
import com.canuzzi.giuseppe.interview.common.test.GoodCreator;

public class BaseTaxRuleTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void apply_NullInput_ExceptionRaised() {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = null;

		// Verify
		assertThatThrownBy(
				// Exercise
				() -> baseTaxRule.apply(taxedGood)).isInstanceOf(NullPointerException.class)
						.hasMessage("Cannot apply tax to a null good");

	}
	
	@Test
	public void apply_GenericGood_TaxPriceIncreased() throws TaxCalculationException {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = GoodCreator.getNonImportedGeneralTaxedGood(14.99);
		
		assertThat(taxedGood.getTaxedPrice()).isEqualTo(BigDecimal.ZERO);
		assertThat(taxedGood.getBasePrice()).isEqualTo(new BigDecimal(String.valueOf(14.99)));

		//Exercise
		baseTaxRule.apply(taxedGood);
		
		//Verify
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(16.49);
		
	}
	

	
	@Test
	public void apply_GenericGood_PercentageAppliedIncreased() throws TaxCalculationException {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = GoodCreator.getNonImportedGeneralTaxedGood(18.99);
	

		//Exercise
		baseTaxRule.apply(taxedGood);
		
		//Verify
		
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(10);
	
	}
	
	@Test
	public void apply_GenericGood_TotalTaxValueIncreased() throws TaxCalculationException {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = GoodCreator.getNonImportedGeneralTaxedGood(18.99);
		

		//Exercise
		baseTaxRule.apply(taxedGood);
		
		//Verify
		
		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(1.9);
	
	}
	

	@Test
	public void apply_BookGood_NoTaxApplied() throws TaxCalculationException {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = GoodCreator.getNonImportedTaxedBook(12.49);

		// Exercise
		baseTaxRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(12.49);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(0);
	}
	
	@Test
	public void apply_MedicalGood_NoTaxApplied() throws TaxCalculationException {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = GoodCreator.getNonImportedTaxedMedical(9.75);

		// Exercise
		baseTaxRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(9.75);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(0);
	}
	
	@Test
	public void apply_FoodGood_NoTaxApplied() throws TaxCalculationException {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = GoodCreator.getNonImportedTaxedFood(0.85);

		// Exercise
		baseTaxRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(0.85);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(0);
	}
	
	@Test
	public void apply_GenericGoodWithBasePriceWithMorePrecision_OriginalPriceIsNotTruncated() throws TaxCalculationException {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = GoodCreator.getNonImportedGeneralTaxedGood(18.9999);

		// Exercise
		baseTaxRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(1.9);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(20.8999);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(10);
	}
	
	@Test
	public void apply_GenericGoodWithBasePriceAsZero_NoCalcsApplied() throws TaxCalculationException {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = GoodCreator.getNonImportedGeneralTaxedGood(0);

		// Exercise
		baseTaxRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(0);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(0);

	}
	
	@Test
	public void apply_GenericGoodWithBasePriceBelowZero_ExceptionRaised() {
		// Setup
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		TaxedGood taxedGood = GoodCreator.getNonImportedGeneralTaxedGood(-1);

		// Verify
		assertThatThrownBy(
				// Exercise
				() -> baseTaxRule.apply(taxedGood)).isInstanceOf(TaxCalculationException.class);				

	}

}
