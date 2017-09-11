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
import com.canuzzi.giuseppe.interview.businesslogic.TaxImportRule;
import com.canuzzi.giuseppe.interview.businesslogic.TaxRuleEngine;
import com.canuzzi.giuseppe.interview.businesslogic.TaxRuleException;
import com.canuzzi.giuseppe.interview.businesslogic.TaxRuleManager;
import com.canuzzi.giuseppe.interview.businesslogic.TaxedGood;
import com.canuzzi.giuseppe.interview.common.test.GoodCreator;
import com.canuzzi.giuseppe.interview.domain.entity.Category;
import com.canuzzi.giuseppe.interview.domain.entity.Good;

public class TaxRuleManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void taxGood_ImportBookToTax_TaxedBookCreatedWithPreviousGoodInfo() throws Exception {
		//Setup
		Good good = GoodCreator.getImportedBook(11.25);

		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = new TaxRuleEngine();
		
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		
		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();
		
		rules.add(importRule);
		rules.add(baseTaxRule);
		
		TaxRuleManager trm = new TaxRuleManager(taxRuleEngine, rules);
		
		//Exercise
		
		TaxedGood tg = trm.getTaxedGood(good);
		
		//Verify
		
		assertThat(tg).isNotNull();
		assertThat(tg.getTaxedPrice().doubleValue()).isEqualTo(Double.valueOf("11.85"));
		assertThat(tg.getTotalTaxValue().doubleValue()).isEqualTo(Double.valueOf("0.60"));
		assertThat(tg.getTaxPercentageApplied().doubleValue()).isEqualTo(Double.valueOf("5.00"));
		assertThat(tg.getBasePrice().doubleValue()).isEqualTo(Double.valueOf("11.25"));
		assertThat(tg.getCategory()).isEqualTo(Category.BOOK);
		assertThat(tg.getName()).isEqualTo(good.getName());
		assertThat(tg.getDescription()).isEqualTo(good.getDescription());
		
	}
	
	@Test
	public void taxGood_TaxFreeFoodGood_TaxedFoodCreatedWithNoTaxApplied() throws Exception {
		//Setup
		Good good = GoodCreator.getNonImportedFood(0.85);

		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = new TaxRuleEngine();
		
		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = new BaseTaxRule();
		
		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();
		
		rules.add(importRule);
		rules.add(baseTaxRule);
		
		TaxRuleManager trm = new TaxRuleManager(taxRuleEngine, rules);
		
		//Exercise
		
		TaxedGood tg = trm.getTaxedGood(good);
		
		//Verify
		
		assertThat(tg).isNotNull();
		assertThat(tg.getTaxedPrice().doubleValue()).isEqualTo(0.85);
		assertThat(tg.getTotalTaxValue().doubleValue()).isEqualTo(0);
		assertThat(tg.getTaxPercentageApplied().doubleValue()).isEqualTo(0);
		assertThat(tg.getBasePrice().doubleValue()).isEqualTo(Double.valueOf("0.85"));
		assertThat(tg.getCategory()).isEqualTo(Category.FOOD);
		assertThat(tg.getName()).isEqualTo(good.getName());
		assertThat(tg.getDescription()).isEqualTo(good.getDescription());
	}
	
	@Test
	public void taxGood_EmptyRulesListProvided_NoTaxApplied() throws Exception{
		// Setup
		Good good = GoodCreator.getImportedFood(47.50);

		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = new TaxRuleEngine();

		
		List<ITaxRule<TaxedGood>> rules = Lists.newArrayList();

		TaxRuleManager trm = new TaxRuleManager(taxRuleEngine, rules);

		// Exercise

		TaxedGood tg = trm.getTaxedGood(good);

		// Verify

		assertThat(tg).isNotNull();
		assertThat(tg.getTaxedPrice().doubleValue()).isEqualTo(0);
		assertThat(tg.getTotalTaxValue().doubleValue()).isEqualTo(0);
		assertThat(tg.getTaxPercentageApplied().doubleValue()).isEqualTo(0);
		assertThat(tg.getBasePrice().doubleValue()).isEqualTo(Double.valueOf("47.5"));
		assertThat(tg.getCategory()).isEqualTo(Category.FOOD);
		assertThat(tg.getName()).isEqualTo(good.getName());
		assertThat(tg.getDescription()).isEqualTo(good.getDescription());
	}
	
	@Test
	public void taxGood_NullEngineToInitTaxRuleManager_ExceptionRaised() {
		//Setup

		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = null;
		List<ITaxRule<TaxedGood>> rules = Lists.newArrayList();


		//Exercise
		//Verify
		assertThatThrownBy( 
							//Exercise
							() -> new TaxRuleManager(taxRuleEngine, rules)
							
						  ).isInstanceOf(NullPointerException.class).hasMessage("No valid engine provided for tax calculation");

	}
	
	@Test
	public void taxGood_NullListOfRulesToInitTaxRuleManager_ExceptionRaised() {
		//Setup

		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = new TaxRuleEngine();
		List<ITaxRule<TaxedGood>> rules = null;


		//Exercise
		//Verify
		assertThatThrownBy( 
							//Exercise
							() -> new TaxRuleManager(taxRuleEngine, rules)
							
						  ).isInstanceOf(NullPointerException.class).hasMessage("No valid rules provided for tax calculation");

	}
	
	@Test
	public void taxGood_OneRuleInsideListIsNull_ExceptionRaised() throws Exception {
		// Setup
		Good good = GoodCreator.getImportedBook(11.25);

		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = new TaxRuleEngine();

		TaxImportRule importRule = new TaxImportRule();
		BaseTaxRule baseTaxRule = null;

		List<ITaxRule<TaxedGood>> rules = new ArrayList<>();

		rules.add(importRule);
		rules.add(baseTaxRule);

		TaxRuleManager trm = new TaxRuleManager(taxRuleEngine, rules);

		// Exercise

		

		assertThatThrownBy(
							() -> trm.getTaxedGood(good)
				).isInstanceOf(TaxRuleException.class).hasMessage("One or more rules has null value.");

	}
	

}
