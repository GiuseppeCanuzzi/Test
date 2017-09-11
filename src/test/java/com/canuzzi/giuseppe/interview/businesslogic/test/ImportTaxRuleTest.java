package com.canuzzi.giuseppe.interview.businesslogic.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.canuzzi.giuseppe.interview.businesslogic.TaxCalculationException;
import com.canuzzi.giuseppe.interview.businesslogic.TaxImportRule;
import com.canuzzi.giuseppe.interview.businesslogic.TaxedGood;
import com.canuzzi.giuseppe.interview.common.test.GoodCreator;

public class ImportTaxRuleTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void apply_NullInput_ExceptionRaised() {
		// Setup
		TaxImportRule taxImportRule = new TaxImportRule();
		TaxedGood taxedGood = null;

		// Verify
		assertThatThrownBy(
				// Exercise
				() -> taxImportRule.apply(taxedGood)).isInstanceOf(NullPointerException.class)
						.hasMessage("Cannot apply tax to a null good");

	}
	
	@Test
	public void apply_ImportedGenericGood_TaxPriceIncreased() throws TaxCalculationException {
		// Setup
		TaxImportRule TaxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedGeneralTaxedGood(11.25);
		
		assertThat(taxedGood.getTaxedPrice()).isEqualTo(BigDecimal.ZERO);
		assertThat(taxedGood.getBasePrice()).isEqualTo(new BigDecimal(String.valueOf(11.25)));

		//Exercise
		TaxImportRule.apply(taxedGood);
		
		//Verify
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(11.85);
		
		
	}
	

	
	@Test
	public void apply_ImportedGenericFoodBaseCostWithoutDecimalDigit_TaxPriceIncreased() throws TaxCalculationException {
		// Setup
		TaxImportRule TaxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedGeneralTaxedGood(10);


		// Exercise
		TaxImportRule.apply(taxedGood);

		// Verify
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(10.5);
	}
	
	
	@Test
	public void apply_ImportGenericGood_PercentageAppliedIncreased() throws TaxCalculationException {
		// Setup
		TaxImportRule TaxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedGeneralTaxedGood(18.99);
	

		//Exercise
		TaxImportRule.apply(taxedGood);
		
		//Verify
		
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(5);
	
	}
	
	@Test
	public void apply_ImportGenericGood_TotalTaxValueIncreased() throws TaxCalculationException {
		// Setup
		TaxImportRule TaxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedGeneralTaxedGood(11.25);
		

		//Exercise
		TaxImportRule.apply(taxedGood);
		
		//Verify
		
		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0.60);
	
	}
	
	@Test
	public void apply_ImportedBookGood_ImportTaxApplied() throws TaxCalculationException {
		// Setup
		TaxImportRule TaxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedTaxedBook(11.25);

		// Exercise
		TaxImportRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0.6);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(11.85);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(5);
	}
	
	@Test
	public void apply_ImportedMedicalGood_ImportTaxApplied() throws TaxCalculationException {
		// Setup
		TaxImportRule TaxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedTaxedMedical(11.25);

		// Exercise
		TaxImportRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0.6);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(11.85);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(5);
	}
	
	@Test
	public void apply_ImportedFoodGood_ImportTaxApplied() throws TaxCalculationException {
		// Setup
		TaxImportRule taxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedTaxedMedical(10);

		// Exercise
		taxImportRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0.5);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(10.5);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(5);
	}
	
	@Test
	public void apply_ImportedGenericGoodWithBasePriceWithMorePrecision_OriginalPriceIsNotTruncated() throws TaxCalculationException {
		// Setup
		TaxImportRule TaxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedGeneralTaxedGood(11.25555);

		// Exercise
		TaxImportRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0.60);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(11.85555);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(5);
	}
	
	@Test
	public void apply_ImportGenericGoodWithBasePriceAsZero_NoCalcsApplied() throws TaxCalculationException {
		// Setup
		TaxImportRule TaxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedGeneralTaxedGood(0);

		// Exercise
		TaxImportRule.apply(taxedGood);

		// Verify

		assertThat(taxedGood.getTotalTaxValue().doubleValue()).isEqualTo(0);
		assertThat(taxedGood.getTaxedPrice().doubleValue()).isEqualTo(0);
		assertThat(taxedGood.getTaxPercentageApplied().doubleValue()).isEqualTo(0);

	}
	
	@Test
	public void apply_ImportedGenericGoodWithBasePriceBelowZero_ExceptionRaised() {
		// Setup
		TaxImportRule TaxImportRule = new TaxImportRule();
		TaxedGood taxedGood = GoodCreator.getImportedGeneralTaxedGood(-1);

		// Verify
		assertThatThrownBy(
				// Exercise
				() -> TaxImportRule.apply(taxedGood)).isInstanceOf(TaxCalculationException.class);				

	}

}
