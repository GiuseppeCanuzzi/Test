package com.canuzzi.giuseppe.interview.view.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.canuzzi.giuseppe.interview.businesslogic.TaxedGood;
import com.canuzzi.giuseppe.interview.common.test.GoodCreator;
import com.canuzzi.giuseppe.interview.view.ReceiptPrinterView;
import com.google.common.collect.Lists;

public class ReceiptPrinterViewTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void printReceipt_VerifyFormatting_ReceiptPrinted() {
		
		//Setup
		ReceiptPrinterView receiptPrinter = new ReceiptPrinterView();
		
		TaxedGood nonImportedGenericGood = GoodCreator.getNonImportedGeneralTaxedGood(18.99);
		nonImportedGenericGood.setTaxedPrice(new BigDecimal(String.valueOf("20.89")));
		
		TaxedGood importedGenericGood = GoodCreator.getImportedGeneralTaxedGood(27.99);
		importedGenericGood.setTaxedPrice(new BigDecimal(String.valueOf("32.19")));
		
		TaxedGood nonImportedMedicalGood = GoodCreator.getNonImportedTaxedMedical(9.75);
		nonImportedMedicalGood.setTaxedPrice(new BigDecimal(String.valueOf("9.75")));
		
		TaxedGood importedFoodGood = GoodCreator.getImportedTaxedBook(11.25);
		importedFoodGood.setTaxedPrice(new BigDecimal(String.valueOf("11.85")));
		
		
		List<TaxedGood> taxedGoodToPrint = Lists.newArrayList(nonImportedGenericGood,nonImportedMedicalGood, importedFoodGood, importedGenericGood);
		
		//Exercise
		
		String receipt = receiptPrinter.printReceipt(taxedGoodToPrint);
		
		//Verify
		
		assertThat(receipt).isEqualTo("1 parfume : 20.89\n" + 
									  "1 medical : 9.75\n" + 
									  "1 imported book : 11.85\n" + 
									  "1 imported parfume : 32.19\n" + 
									  "Sales Taxes: 6.70\n" + 
									  "Total : 74.68\n");
		
			
	}
	
	@Test
	public void printReceipt_TotalFormatWhenTaxedPriceHavingMorePrecision_PrecisionIsMaintained() {
		//Setup
		ReceiptPrinterView receiptPrinter = new ReceiptPrinterView();
		
		TaxedGood nonImportedGenericGood = GoodCreator.getNonImportedGeneralTaxedGood(18.9999);
		nonImportedGenericGood.setTaxedPrice(new BigDecimal(String.valueOf("20.8999")));
		
		TaxedGood importedGenericGood = GoodCreator.getImportedGeneralTaxedGood(27.99);
		importedGenericGood.setTaxedPrice(new BigDecimal(String.valueOf("32.19")));
		
		TaxedGood nonImportedMedicalGood = GoodCreator.getNonImportedTaxedMedical(9.75);
		nonImportedMedicalGood.setTaxedPrice(new BigDecimal(String.valueOf("9.75")));
		
		TaxedGood importedFoodGood = GoodCreator.getImportedTaxedBook(11.25);
		importedFoodGood.setTaxedPrice(new BigDecimal(String.valueOf("11.85")));
		
		
		List<TaxedGood> taxedGoodToPrint = Lists.newArrayList(nonImportedGenericGood,nonImportedMedicalGood, importedFoodGood, importedGenericGood);
		
		//Exercise
		
		String receipt = receiptPrinter.printReceipt(taxedGoodToPrint);
		
		//Verify
		
		assertThat(receipt).isEqualTo("1 parfume : 20.8999\n" + 
									  "1 medical : 9.75\n" + 
									  "1 imported book : 11.85\n" + 
									  "1 imported parfume : 32.19\n" + 
									  "Sales Taxes: 6.70\n" + 
									  "Total : 74.6899\n");
		
	}
	

	
	
	@Test
	public void printReceipt_FormatWhenTaxedPriceIsSetWithoutBigDecimalStringConstructor_PrecisionMaintained() {
		// Setup
		ReceiptPrinterView receiptPrinter = new ReceiptPrinterView();

		TaxedGood nonImportedGenericGood = GoodCreator.getNonImportedGeneralTaxedGood(18.9999);
		nonImportedGenericGood.setTaxedPrice(new BigDecimal(20.8999));

		TaxedGood importedGenericGood = GoodCreator.getImportedGeneralTaxedGood(27.99);
		importedGenericGood.setTaxedPrice(new BigDecimal(32.19));

		TaxedGood nonImportedMedicalGood = GoodCreator.getNonImportedTaxedMedical(9.75);
		nonImportedMedicalGood.setTaxedPrice(new BigDecimal(9.75));

		TaxedGood importedFoodGood = GoodCreator.getImportedTaxedBook(11.25);
		importedFoodGood.setTaxedPrice(new BigDecimal(11.85));

		List<TaxedGood> taxedGoodToPrint = Lists.newArrayList(nonImportedGenericGood, nonImportedMedicalGood,
				importedFoodGood, importedGenericGood);

		// Exercise

		String receipt = receiptPrinter.printReceipt(taxedGoodToPrint);

		// Verify

		assertThat(receipt).isEqualTo("1 parfume : 20.8999\n" +
									 "1 medical : 9.75\n" +
									 "1 imported book : 11.85\n"+
									 "1 imported parfume : 32.19\n" +
									 "Sales Taxes: 6.70\n" + 
									 "Total : 74.6899\n");
	}
	
	
	@Test
	public void printReceipt_NullInput_ExceptionRaised(){
		//Setup
		ReceiptPrinterView receiptPrinter = new ReceiptPrinterView();
		List<TaxedGood> taxedGoodToPrint = null;
		
		
		
		//Verify
		assertThatThrownBy(//Exercise
							() -> receiptPrinter.printReceipt(taxedGoodToPrint)
						  ).isInstanceOf(NullPointerException.class)
						   .hasMessage("List of elements to print is null");
	}
	
	@Test
	public void printReceipt_EmptyList_PrintZeroTotalAndTaxes() {
		// Setup
		ReceiptPrinterView receiptPrinter = new ReceiptPrinterView();
		List<TaxedGood> taxedGoodToPrint = Lists.newArrayList();
		
		//Exercise
		String emptyReceipt = receiptPrinter.printReceipt(taxedGoodToPrint);
		
		//Verify
				assertThat(emptyReceipt).isEqualTo("Sales Taxes: 0.00\n" + 
												  "Total : 0.00\n");
	}
	
	@Test
	public void printReceipt_FormattingWhenNumbersEndingInZero_Formatted() {
		//Setup
				ReceiptPrinterView receiptPrinter = new ReceiptPrinterView();
				
				TaxedGood nonImportedGenericGood = GoodCreator.getNonImportedGeneralTaxedGood(10.00);
				nonImportedGenericGood.setTaxedPrice(new BigDecimal(String.valueOf("10.50")));
				
				TaxedGood importedGenericGood = GoodCreator.getImportedGeneralTaxedGood(47.50);
				importedGenericGood.setTaxedPrice(new BigDecimal(String.valueOf("54.70")));
				
				
				
				
				List<TaxedGood> taxedGoodToPrint = Lists.newArrayList(nonImportedGenericGood,importedGenericGood);
				
				//Exercise
				
				String receipt = receiptPrinter.printReceipt(taxedGoodToPrint);
				
				//Verify
				
				assertThat(receipt).isEqualTo("1 parfume : 10.50\n" + 
											  "1 imported parfume : 54.70\n" + 
											  "Sales Taxes: 7.70\n" + 
											  "Total : 65.20\n");
	}
	
	@Test
	public void printReceipt_WhenListContainsNullValues_SkippedNullValues() {
		
		// Setup
		ReceiptPrinterView receiptPrinter = new ReceiptPrinterView();

		TaxedGood nonImportedGenericGood = GoodCreator.getNonImportedGeneralTaxedGood(18.9999);
		nonImportedGenericGood.setTaxedPrice(new BigDecimal(20.8999));

		TaxedGood importedGenericGood = null;

		TaxedGood nonImportedMedicalGood = GoodCreator.getNonImportedTaxedMedical(9.75);
		nonImportedMedicalGood.setTaxedPrice(new BigDecimal(9.75));

		TaxedGood importedFoodGood = GoodCreator.getImportedTaxedBook(11.25);
		importedFoodGood.setTaxedPrice(new BigDecimal(11.85));

		List<TaxedGood> taxedGoodToPrint = Lists.newArrayList(nonImportedGenericGood, nonImportedMedicalGood,
				importedFoodGood, importedGenericGood);

		// Exercise

		String receipt = receiptPrinter.printReceipt(taxedGoodToPrint);

		// Verify
		assertThat(receipt).isEqualTo("1 parfume : 20.8999\n" + "1 medical : 9.75\n" + "1 imported book : 11.85\n"
				+ "Sales Taxes: 2.50\n" + "Total : 42.4999\n");
		
		
	}
	
	@Test
	public void printReceipt_WhenTaxedPriceHasMoreThanTwoZerosAsDecimals_FormattedWithTwoZeros() {
		//Setup
		ReceiptPrinterView receiptPrinter = new ReceiptPrinterView();

		TaxedGood nonImportedGenericGood = GoodCreator.getNonImportedGeneralTaxedGood(18.9900);
		nonImportedGenericGood.setTaxedPrice(new BigDecimal(20.8900));

		TaxedGood importedGenericGood = null;

		TaxedGood nonImportedMedicalGood = GoodCreator.getNonImportedTaxedMedical(9.7500);
		nonImportedMedicalGood.setTaxedPrice(new BigDecimal(9.7500));

		TaxedGood importedFoodGood = GoodCreator.getImportedTaxedBook(11.2500);
		importedFoodGood.setTaxedPrice(new BigDecimal(11.8500));

		List<TaxedGood> taxedGoodToPrint = Lists.newArrayList(nonImportedGenericGood, nonImportedMedicalGood,
				importedFoodGood, importedGenericGood);

		// Exercise

		String receipt = receiptPrinter.printReceipt(taxedGoodToPrint);

		// Verify
		assertThat(receipt).isEqualTo("1 parfume : 20.89\n" + "1 medical : 9.75\n" + "1 imported book : 11.85\n"
				+ "Sales Taxes: 2.50\n" + "Total : 42.49\n");
	}

}
