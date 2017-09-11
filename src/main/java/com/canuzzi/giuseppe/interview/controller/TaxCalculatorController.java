package com.canuzzi.giuseppe.interview.controller;



import java.util.List;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Preconditions;

import com.canuzzi.giuseppe.interview.businesslogic.TaxRuleManager;
import com.canuzzi.giuseppe.interview.businesslogic.TaxedGood;
import com.canuzzi.giuseppe.interview.dal.IDataSource;
import com.canuzzi.giuseppe.interview.domain.entity.Good;
import com.canuzzi.giuseppe.interview.view.ReceiptPrinterView;

public class TaxCalculatorController implements ITaxController{

	private final IDataSource model;
	private TaxRuleManager taxRuleManager;
	private ReceiptPrinterView taxView;
			
	public TaxCalculatorController(IDataSource model, ReceiptPrinterView taxView, TaxRuleManager taxRuleManager) {
		
		Preconditions.checkNotNull(model,"Invalid model");
		Preconditions.checkNotNull(taxView,"Invalid view");
		Preconditions.checkNotNull(taxRuleManager,"Invalid rule manager");
		
		this.model = model;
		this.taxRuleManager = taxRuleManager;
		this.taxView = taxView;
	}
	
	//TODO Tax calculation
	@Override
	public void calculateTax()  throws Exception{
				
		List<Good> goodsToTax = this.model.getAllProducts();
		
		List<TaxedGood> taxedProductList = getTaxedProducts(goodsToTax);
		
		updateUI(taxedProductList);

		
	}

	
	private void updateUI(List<TaxedGood> taxedProductList) {
		
		//TODO Can map the taxed object to View type object container instead of pass directly
		this.taxView.printReceipt(taxedProductList);
		
	}
	
	private List<TaxedGood> getTaxedProducts(List<Good> goodsToTax) throws Exception {
		
		List<TaxedGood> taxedProductList = Lists.newArrayList();
		
		try {
			
			for (Good good : goodsToTax) {			
				TaxedGood tg = taxRuleManager.getTaxedGood(good);
				taxedProductList.add(tg);
			}
			
		}catch(Exception ex) {
			//Log that an error occurred while taxing good 
			//and rethrow because i can't release a receipt with wrong data.
			//An ipothetical framework could handle the controller exception
			
			throw ex;
		}
		
		return taxedProductList;
	}

	@Override
	public void updateUI() throws Exception {
		// TODO Auto-generated method stub
		
	}


}
