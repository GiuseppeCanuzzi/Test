package com.canuzzi.giuseppe.interview.businesslogic;



import java.util.List;

import org.assertj.core.util.Preconditions;

import com.canuzzi.giuseppe.interview.domain.entity.Good;


public class TaxRuleManager{
	
	private List<ITaxRule<TaxedGood>> taxRuleList;
	private IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine;
	
	public TaxRuleManager(IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> taxRuleEngine, List<ITaxRule<TaxedGood>> rules) {
		
		Preconditions.checkNotNull(taxRuleEngine, "No valid engine provided for tax calculation");
		Preconditions.checkNotNull(rules, "No valid rules provided for tax calculation");
		
		this.taxRuleList = rules; //FluentIterable.from(rules).filter(Predicates.notNull()).toList();
		this.taxRuleEngine = taxRuleEngine;
		
	}
	

	public TaxedGood getTaxedGood(Good good) throws Exception {
		
		Preconditions.checkNotNull(good);
		
		TaxedGood taxedGood = new TaxedGood();
		
		taxedGood.setBasePrice(good.getBasePrice());
		taxedGood.setCategory(good.getCategory());
		taxedGood.setDescription(good.getDescription());
		taxedGood.setImport(good.isImport());
		taxedGood.setName(good.getName());
		
		//Log start applying rules for given element type with name and description
		taxRuleEngine.applyRules(taxRuleList, taxedGood);
		//Log end applying rules

		return taxedGood;
		
	}
	

}
