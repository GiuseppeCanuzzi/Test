package com.canuzzi.giuseppe.interview.domain.entity;

import java.math.BigDecimal;

public class Good {
	
	protected boolean isImport;
	protected String description;
	protected String name;
	protected BigDecimal basePrice;
	protected Category category;
	
	public Good() {
	}
	
	public boolean isImport() {
		return isImport;
	}
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(BigDecimal basePrice) {
		BigDecimal exactBasePriceRepresentation = BigDecimal.valueOf(basePrice.doubleValue());
		this.basePrice = exactBasePriceRepresentation;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

}
