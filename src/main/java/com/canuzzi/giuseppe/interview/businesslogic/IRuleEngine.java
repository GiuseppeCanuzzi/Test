package com.canuzzi.giuseppe.interview.businesslogic;

import java.util.List;

public interface IRuleEngine<T extends IRule<E>, E extends IEvaluable> {

	public void applyRules(List<T> rules, E evaluable) throws Exception;
	
}
