package com.canuzzi.giuseppe.interview.common.test;

import java.math.BigDecimal;

import com.canuzzi.giuseppe.interview.businesslogic.TaxedGood;
import com.canuzzi.giuseppe.interview.domain.entity.Category;
import com.canuzzi.giuseppe.interview.domain.entity.Good;

public class GoodCreator {

	public static Good getNonImportedBook(double basePrice) {

		Good good = new Good();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.BOOK);
		good.setImport(false);
		good.setName("book");
		good.setDescription("some description");

		return good;
	}
	
	public static Good getImportedBook(double basePrice) {

		Good good = new Good();
	
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.BOOK);
		good.setImport(true);
		good.setName("book");
		good.setDescription("some description");

		return good;
	}

	public static Good getImportedFood(double basePrice) {

		Good good = new Good();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.FOOD);
		good.setImport(true);
		good.setName("food");
		good.setDescription("some description");

		return good;
	}

	public static Good getNonImportedFood(double basePrice) {

		Good good = new Good();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.FOOD);
		good.setImport(false);
		good.setName("food");
		good.setDescription("some description");

		return good;
	}

	public static Good getNonImportedMedical(double basePrice) {

		Good good = new Good();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.MEDICAL);
		good.setImport(false);
		good.setName("medical");
		good.setDescription("some description");

		return good;
	}

	public static Good getNonImportedGeneralGood(double basePrice) {

		Good good = new Good();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.OTHER);
		good.setImport(false);
		good.setName("parfume");
		good.setDescription("some description");

		return good;
	}

	public static Good getImportedGeneralGood(double basePrice) {

		Good good = new Good();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.OTHER);
		good.setImport(true);
		good.setName("parfume");
		good.setDescription("some description");

		return good;
	}
	
	public static TaxedGood getNonImportedGeneralTaxedGood(double basePrice) {

		TaxedGood good = new TaxedGood();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.OTHER);
		good.setImport(false);
		good.setName("parfume");
		good.setDescription("some description");

		return good;
	}
	
	public static TaxedGood getNonImportedTaxedMedical(double basePrice) {

		TaxedGood good = new TaxedGood();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.MEDICAL);
		good.setImport(false);
		good.setName("medical");
		good.setDescription("some description");

		return good;
	}
	
	public static TaxedGood getNonImportedTaxedFood(double basePrice) {

		TaxedGood good = new TaxedGood();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.MEDICAL);
		good.setImport(false);
		good.setName("medical");
		good.setDescription("some description");

		return good;
	}
	
	public static TaxedGood getNonImportedTaxedBook(double basePrice) {

		TaxedGood good = new TaxedGood();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.BOOK);
		good.setImport(false);
		good.setName("book");
		good.setDescription("some description");

		return good;
	}
	
	public static TaxedGood getImportedGeneralTaxedGood(double basePrice) {

		TaxedGood good = new TaxedGood();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.OTHER);
		good.setImport(true);
		good.setName("parfume");
		good.setDescription("some description");

		return good;
	}
	
	public static TaxedGood getImportedTaxedMedical(double basePrice) {

		TaxedGood good = new TaxedGood();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.MEDICAL);
		good.setImport(true);
		good.setName("medical");
		good.setDescription("some description");

		return good;
	}
	
	public static TaxedGood getImportedTaxedFood(double basePrice) {

		TaxedGood good = new TaxedGood();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.MEDICAL);
		good.setImport(true);
		good.setName("medical");
		good.setDescription("some description");

		return good;
	}
	
	public static TaxedGood getImportedTaxedBook(double basePrice) {

		TaxedGood good = new TaxedGood();

		
		good.setBasePrice(new BigDecimal(basePrice));
		good.setCategory(Category.BOOK);
		good.setImport(true);
		good.setName("book");
		good.setDescription("some description");

		return good;
	}
	

}
