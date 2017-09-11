package com.canuzzi.giuseppe.interview.controller.test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.canuzzi.giuseppe.interview.businesslogic.BaseTaxRule;
import com.canuzzi.giuseppe.interview.businesslogic.IRuleEngine;
import com.canuzzi.giuseppe.interview.businesslogic.ITaxRule;
import com.canuzzi.giuseppe.interview.businesslogic.TaxImportRule;
import com.canuzzi.giuseppe.interview.businesslogic.TaxRuleEngine;
import com.canuzzi.giuseppe.interview.businesslogic.TaxRuleException;
import com.canuzzi.giuseppe.interview.businesslogic.TaxRuleManager;
import com.canuzzi.giuseppe.interview.businesslogic.TaxedGood;
import com.canuzzi.giuseppe.interview.common.test.GoodCreator;
import com.canuzzi.giuseppe.interview.controller.TaxCalculatorController;
import com.canuzzi.giuseppe.interview.dal.IDataSource;
import com.canuzzi.giuseppe.interview.dal.ShoppingCart;
import com.canuzzi.giuseppe.interview.domain.entity.Good;
import com.canuzzi.giuseppe.interview.view.ReceiptPrinterView;

public class TaxCalculatorControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void calculateTax_VerifyCorrectWorkflowForValidInput() throws Exception {
		
		//Setup
		Good nonImportedGenericGood = GoodCreator.getNonImportedBook(18.99);
		Good importedGenericGood = GoodCreator.getImportedGeneralGood(27.99);
		Good nonImportedMedicalGood = GoodCreator.getNonImportedMedical(9.75);
		Good importedFoodGood = GoodCreator.getImportedFood(11.25);
		
		List<Good> goodList = Lists.newArrayList(nonImportedGenericGood,nonImportedMedicalGood, importedFoodGood, importedGenericGood);
		IDataSource model = Mockito.mock(ShoppingCart.class);
		Mockito.when(model.getAllProducts()).thenReturn(goodList);
		
		ReceiptPrinterView taxView = Mockito.spy(new ReceiptPrinterView());
		
		List<ITaxRule<TaxedGood>> rulesList = Lists.newArrayList(new BaseTaxRule(), new TaxImportRule());
		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = Mockito.spy(new TaxRuleEngine());
		TaxRuleManager taxRuleManager = Mockito.spy(new TaxRuleManager(taxRuleEngine, rulesList));	
			
		TaxCalculatorController taxCalculatorController = new TaxCalculatorController(model, taxView, taxRuleManager);
				
		//Exercise
		taxCalculatorController.calculateTax();
		
		//Verify
		Mockito.verify(taxRuleEngine, Mockito.times(4)).applyRules(Mockito.anyList(), Mockito.any(TaxedGood.class));
		Mockito.verify(taxRuleManager, Mockito.times(1)).getTaxedGood(nonImportedGenericGood);
		Mockito.verify(taxRuleManager, Mockito.times(1)).getTaxedGood(importedGenericGood);
		Mockito.verify(taxRuleManager, Mockito.times(1)).getTaxedGood(nonImportedMedicalGood);
		Mockito.verify(taxRuleManager, Mockito.times(1)).getTaxedGood(importedFoodGood);
		Mockito.verify(taxView, Mockito.times(1)).printReceipt(Mockito.anyList());
		
	}
	
	@Test
	public void calculateTax_ThrowsExceptionWhenOneRuleIsNull() {
		// Setup
		Good nonImportedGenericGood = GoodCreator.getNonImportedBook(18.99);
		Good importedGenericGood = GoodCreator.getImportedGeneralGood(27.99);
		Good nonImportedMedicalGood = GoodCreator.getNonImportedMedical(9.75);
		Good importedFoodGood = GoodCreator.getImportedFood(11.25);

		List<Good> goodList = Lists.newArrayList(nonImportedGenericGood, nonImportedMedicalGood, importedFoodGood,
				importedGenericGood);
		IDataSource model = Mockito.mock(ShoppingCart.class);
		Mockito.when(model.getAllProducts()).thenReturn(goodList);

		ReceiptPrinterView taxView = Mockito.spy(new ReceiptPrinterView());

		List<ITaxRule<TaxedGood>> rulesList = Lists.newArrayList(new BaseTaxRule(), null);
		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = Mockito.spy(new TaxRuleEngine());
		TaxRuleManager taxRuleManager = Mockito.spy(new TaxRuleManager(taxRuleEngine, rulesList));

		TaxCalculatorController taxCalculatorController = new TaxCalculatorController(model, taxView, taxRuleManager);

		// Verify
		assertThatThrownBy(() -> taxCalculatorController.calculateTax()).isInstanceOf(TaxRuleException.class);

	}
	
	@Test
	public void calculateTax_RuleManagerIsNull_ExceptionRaised() {
		//Setup
		
		Good nonImportedGenericGood = GoodCreator.getNonImportedBook(18.99);
		Good importedGenericGood = GoodCreator.getImportedGeneralGood(27.99);
		Good nonImportedMedicalGood = GoodCreator.getNonImportedMedical(9.75);
		Good importedFoodGood = GoodCreator.getImportedFood(11.25);

		List<Good> goodList = Lists.newArrayList(nonImportedGenericGood, nonImportedMedicalGood, importedFoodGood,
				importedGenericGood);
		IDataSource model = Mockito.mock(ShoppingCart.class);
		Mockito.when(model.getAllProducts()).thenReturn(goodList);

		ReceiptPrinterView taxView = Mockito.spy(new ReceiptPrinterView());
		TaxRuleManager taxRuleManager = null;


		// Verify
		assertThatThrownBy(() -> {new TaxCalculatorController(model, taxView, taxRuleManager);})
		.isInstanceOf(NullPointerException.class)
		.hasMessage("Invalid rule manager");
		
	}
	
	@Test
	public void calculateTax_ModelIsNull_ExceptionRaised() {
		// Setup
		
		IDataSource model = null;

		ReceiptPrinterView taxView = Mockito.spy(new ReceiptPrinterView());

		List<ITaxRule<TaxedGood>> rulesList = Lists.newArrayList(new BaseTaxRule(), null);
		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = Mockito.spy(new TaxRuleEngine());
		TaxRuleManager taxRuleManager = Mockito.spy(new TaxRuleManager(taxRuleEngine, rulesList));

		
		//Verify
		assertThatThrownBy(() -> {new TaxCalculatorController(model, taxView, taxRuleManager);})
		.isInstanceOf(NullPointerException.class)
		.hasMessage("Invalid model");
	}
	
	@Test
	public void calculateTax_ViewIsNull_ExceptionRaised() {
		// Setup
		Good nonImportedGenericGood = GoodCreator.getNonImportedBook(18.99);
		Good importedGenericGood = GoodCreator.getImportedGeneralGood(27.99);
		Good nonImportedMedicalGood = GoodCreator.getNonImportedMedical(9.75);
		Good importedFoodGood = GoodCreator.getImportedFood(11.25);

		List<Good> goodList = Lists.newArrayList(nonImportedGenericGood, nonImportedMedicalGood, importedFoodGood,
				importedGenericGood);
		IDataSource model = Mockito.mock(ShoppingCart.class);
		Mockito.when(model.getAllProducts()).thenReturn(goodList);

		ReceiptPrinterView taxView = null;

		List<ITaxRule<TaxedGood>> rulesList = Lists.newArrayList(new BaseTaxRule(), null);
		IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine = Mockito.spy(new TaxRuleEngine());
		TaxRuleManager taxRuleManager = Mockito.spy(new TaxRuleManager(taxRuleEngine, rulesList));

		//Verify
				assertThatThrownBy(() -> {new TaxCalculatorController(model, taxView, taxRuleManager);})
				.isInstanceOf(NullPointerException.class)
				.hasMessage("Invalid view");

	}
	

}
