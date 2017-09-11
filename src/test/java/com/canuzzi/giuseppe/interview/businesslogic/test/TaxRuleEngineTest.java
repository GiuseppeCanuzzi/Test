package com.canuzzi.giuseppe.interview.businesslogic.test;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.canuzzi.giuseppe.interview.businesslogic.BaseTaxRule;
import com.canuzzi.giuseppe.interview.businesslogic.IRuleEngine;
import com.canuzzi.giuseppe.interview.businesslogic.ITaxRule;
import com.canuzzi.giuseppe.interview.businesslogic.TaxCalculationException;
import com.canuzzi.giuseppe.interview.businesslogic.TaxImportRule;
import com.canuzzi.giuseppe.interview.businesslogic.TaxRuleEngine;
import com.canuzzi.giuseppe.interview.businesslogic.TaxedGood;
import com.canuzzi.giuseppe.interview.common.test.GoodCreator;

public class TaxRuleEngineTest {

	IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine;
	@Before
	public void setUp() throws Exception {
		this.taxRuleEngine = new TaxRuleEngine();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApplyRules_ImportedGenericGood_AllRulesApplied() throws Exception {
		//Setup
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		
		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();
		
		rules.add(importRule);
		rules.add(baseTaxRule);
		
		
		TaxedGood good = GoodCreator.getImportedGeneralTaxedGood(47.5);
		
		//Exercise
		taxRuleEngine.applyRules(rules, good);
		
		//Verify
		
		assertThat(good.getTaxedPrice().doubleValue()).isEqualTo(54.65);
		assertThat(good.getBasePrice().doubleValue()).isEqualTo(47.5);
		assertThat(good.getTaxPercentageApplied().doubleValue()).isEqualTo(15);
		assertThat(good.getTotalTaxValue().doubleValue()).isEqualTo(7.15);
	}
	
	
	@Test
	public void testApplyRules_CheckFloatingPointBehaviour_ValueCorrectlyConverted() throws Exception {
		//Setup
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		
		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();
		
		rules.add(importRule);
		rules.add(baseTaxRule);
		
		double basePriceToVerifyFloatingPointBehaviour = 27.99;
		
		TaxedGood good = GoodCreator.getImportedGeneralTaxedGood(basePriceToVerifyFloatingPointBehaviour);
		
		//Exercise
		taxRuleEngine.applyRules(rules, good);
		
		//Verify
		
		assertThat(good.getTaxedPrice().doubleValue()).isEqualTo(32.19);
		assertThat(good.getBasePrice().doubleValue()).isEqualTo(27.99);
		assertThat(good.getTaxPercentageApplied().doubleValue()).isEqualTo(15);
		assertThat(good.getTotalTaxValue().doubleValue()).isEqualTo(4.2);
	}
	
	@Test
	public void testApplyRules_EmptyListOfRules_NoRulesApplied() throws Exception {
		// Setup

		List<ITaxRule<TaxedGood>> rules = Lists.newArrayList();

		TaxedGood good = GoodCreator.getImportedTaxedFood(11.25);

		// Exercise

		taxRuleEngine.applyRules(rules, good);

		// Verify

		assertThat(good.getTaxedPrice().doubleValue()).isEqualTo(0);
		assertThat(good.getBasePrice().doubleValue()).isEqualTo(11.25);
		assertThat(good.getTaxPercentageApplied().doubleValue()).isEqualTo(0);
		assertThat(good.getTotalTaxValue().doubleValue()).isEqualTo(0);
	}

	@Test
	public void testApplyRules_ImportedFoodGood_OnlyImportRuleApplied() throws Exception {
		
		// Setup
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();

		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();

		rules.add(importRule);
		rules.add(baseTaxRule);

		TaxedGood good = GoodCreator.getImportedTaxedFood(11.25);
		
		//Exercise
		
		taxRuleEngine.applyRules(rules, good);

		// Verify

		assertThat(good.getTaxedPrice().doubleValue()).isEqualTo(11.85);
		assertThat(good.getBasePrice().doubleValue()).isEqualTo(11.25);
		assertThat(good.getTaxPercentageApplied().doubleValue()).isEqualTo(5);
		assertThat(good.getTotalTaxValue().doubleValue()).isEqualTo(0.6);

	}
	
	@Test
	public void testApplyRules_ImportedMedicalGood_OnlyImportRuleApplied() throws Exception {
		
		// Setup
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();

		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();

		rules.add(importRule);
		rules.add(baseTaxRule);

		TaxedGood good = GoodCreator.getImportedTaxedMedical(11.25);
		
		//Exercise
		
		taxRuleEngine.applyRules(rules, good);

		// Verify

		assertThat(good.getTaxedPrice().doubleValue()).isEqualTo(11.85);
		assertThat(good.getBasePrice().doubleValue()).isEqualTo(11.25);
		assertThat(good.getTaxPercentageApplied().doubleValue()).isEqualTo(5);
		assertThat(good.getTotalTaxValue().doubleValue()).isEqualTo(0.6);

	}
	
	
	@Test
	public void testApplyRules_ImportedBookGood_OnlyImportRuleApplied() throws Exception {
		
		// Setup
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();

		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();

		rules.add(importRule);
		rules.add(baseTaxRule);

		TaxedGood good = GoodCreator.getImportedTaxedBook(11.25);
		
		//Exercise
		
		taxRuleEngine.applyRules(rules, good);

		// Verify

		assertThat(good.getTaxedPrice().doubleValue()).isEqualTo(11.85);
		assertThat(good.getBasePrice().doubleValue()).isEqualTo(11.25);
		assertThat(good.getTaxPercentageApplied().doubleValue()).isEqualTo(5);
		assertThat(good.getTotalTaxValue().doubleValue()).isEqualTo(0.6);

	}
	
	@Test
	public void testApplyRules_NonImportedGenericGood_OnlyBaseRuleApplied() throws Exception {
		
		// Setup
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();

		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();

		rules.add(importRule);
		rules.add(baseTaxRule);

		TaxedGood good = GoodCreator.getNonImportedGeneralTaxedGood(18.99);
		
		//Exercise
		
		taxRuleEngine.applyRules(rules, good);

		// Verify

		assertThat(good.getTaxedPrice().doubleValue()).isEqualTo(20.89);
		assertThat(good.getBasePrice().doubleValue()).isEqualTo(18.99);
		assertThat(good.getTaxPercentageApplied().doubleValue()).isEqualTo(10);
		assertThat(good.getTotalTaxValue().doubleValue()).isEqualTo(1.9);

	}
	
	@Test
	public void testApplyRules_NullRules_ExceptionRaised() throws Exception {

		// Setup
		List<ITaxRule<TaxedGood>> rules = null;

		TaxedGood good = GoodCreator.getImportedGeneralTaxedGood(47.5);

		// Exercise
		//Verify
		assertThatThrownBy(() -> taxRuleEngine.applyRules(rules,good)).isInstanceOf(NullPointerException.class)
															   .hasMessage("Cannot apply rules if there are any");
																	

		
	}
	
	@Test
	public void testApplyRules_NullEvaluable_ExceptionRaised() throws Exception {

		// Setup
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		
		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();
		
		rules.add(importRule);
		rules.add(baseTaxRule);

		TaxedGood good = null;

		// Exercise
		//Verify
		assertThatThrownBy(() -> taxRuleEngine.applyRules(rules,good)).isInstanceOf(NullPointerException.class)
															   .hasMessage("Cannot apply rules if no evaluable subject is specified");															

	}
	
	@Test
	public void testApplyRules_ThrowsException_WhenSubjectEvaluatedHasNegativeValue() {
		// Setup
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();

		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();

		rules.add(importRule);
		rules.add(baseTaxRule);

		TaxedGood good = GoodCreator.getImportedGeneralTaxedGood(-0.99);
		
		

		// Exercise
		// Verify
		assertThatThrownBy(() -> taxRuleEngine.applyRules(rules, good)).isInstanceOf(TaxCalculationException.class)
																	  .hasMessageContaining("Negative base price is invalid for tax calculation");
	}


}
