package com.canuzzi.giuseppe.interview.dal;

import java.util.List;

import com.canuzzi.giuseppe.interview.domain.entity.Good;

/**
 * This interface is a simple representation of a data source used to retrieve our data from a repository
 * @author giuseppecanuzzi
 *
 */
public interface IDataSource {
	
	public List<Good> getAllProducts();

}
