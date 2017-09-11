package com.canuzzi.giuseppe.interview.dal;

import java.util.List;

import org.assertj.core.util.Preconditions;

import com.canuzzi.giuseppe.interview.domain.entity.Good;
import com.google.common.collect.Lists;

/**
 * This class simulates a possible data source for the kind o data we need to calculate the tax.
 * 
 * @author giuseppecanuzzi
 *
 */
public class ShoppingCart implements IDataSource {
	
	private List<Good> productList;
	
	public List<Good> getAllProducts(){
		
		//TODO return a copy
		return productList;
			
	}
	
	public ShoppingCart() {
		this(Lists.newArrayList());
	}
	
	public ShoppingCart(List<Good> productList) {
		Preconditions.checkNotNull(productList, "Trying to add null products during initialization");
		
		this.productList = productList;
	}
	
	public boolean addProduct(Good product) {
		
		Preconditions.checkNotNull(product,"Trying to add null product");
		
		return productList.add(product);
	}
	
	public boolean addProductList(List<Good> products) {
		
		Preconditions.checkNotNull(products, "Trying to add null products");
		
		//TODO add a copy
		
		return productList.addAll(products);
	}
	

}
