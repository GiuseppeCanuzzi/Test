package com.canuzzi.giuseppe.interview.businesslogic;

import java.util.List;

import org.assertj.core.util.Preconditions;

public class TaxRuleEngine implements IRuleEngine<ITaxRule<TaxedGood>, TaxedGood> {

	@Override
	public void applyRules(List< ITaxRule<TaxedGood> > rules, TaxedGood evaluable) throws Exception {
		
		Preconditions.checkNotNull(rules, "Cannot apply rules if there are any");
		Preconditions.checkNotNull(evaluable, "Cannot apply rules if no evaluable subject is specified");
		
		
		 if(rules.contains(null)) {
			 //Log that there is one or more null with null value, maybe something went wrong during initialization
			 //and some rules have been lost
			 throw new TaxRuleException("One or more rules has null value.");
		 }

		
		if(rules.isEmpty()) {
			//Log that no rules has been provided
			return;
		}
		
		for (ITaxRule<TaxedGood> rule : rules) {
			//Log rule description being applied
			rule.apply(evaluable);
		}
	
	}

}
