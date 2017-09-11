package com.canuzzi.giuseppe.interview.dal;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.util.Lists;

import com.canuzzi.giuseppe.interview.domain.entity.Category;
import com.canuzzi.giuseppe.interview.domain.entity.Good;

/**
 * This class return a list of Product based on input type requested by this
 * exercise In a real scenario this products could be retrieved from a database
 * 
 * @author giuseppecanuzzi
 *
 */
public class FakeDataUtilities {

	public static List<Good> createProductList(InputType inputType) {

		List<Good> products = Lists.newArrayList();

		Good p1 = new Good();
		Good p2 = new Good();
		Good p3 = new Good();
		Good p4 = new Good();

		switch (inputType) {

		case FIRST:
			p1.setBasePrice(new BigDecimal(12.49));
			p1.setCategory(Category.BOOK);
			p1.setName("book");
			p1.setImport(false);
			p1.setDescription("A beautiful book of love");

			p2.setBasePrice(new BigDecimal(14.99));
			p2.setCategory(Category.OTHER);
			p2.setName("music CD");
			p2.setImport(false);
			p2.setDescription("A head full of dreams - Coldplay");

			p3.setBasePrice(new BigDecimal(0.85));
			p3.setCategory(Category.FOOD);
			p3.setName("chocolate bar");
			p3.setImport(false);
			p3.setDescription("Intense, dark chocolate");

			return Lists.newArrayList(p1, p2, p3);

		case SECOND:
			p1.setBasePrice(new BigDecimal(10.00));
			p1.setCategory(Category.FOOD);
			p1.setName("Box of Chocolate");
			p1.setImport(true);
			p1.setDescription("Intense, dark chocolate");

			p2.setBasePrice(new BigDecimal(47.50));
			p2.setCategory(Category.OTHER);
			p2.setName("bootle of parfume");
			p2.setImport(true);
			p2.setDescription("Chanel N 5");
			return Lists.newArrayList(p1, p2);

		case THIRD:
			p1.setBasePrice(new BigDecimal(27.99));
			p1.setCategory(Category.OTHER);
			p1.setName("bootle of parfume");
			p1.setImport(true);
			p1.setDescription("Chanel N 5");

			p2.setBasePrice(new BigDecimal(18.99));
			p2.setCategory(Category.OTHER);
			p2.setName("bottle of parfume");
			p2.setImport(false);
			p2.setDescription("Dior");

			p3.setBasePrice(new BigDecimal(9.75));
			p3.setCategory(Category.MEDICAL);
			p3.setName("packet of headache pills");
			p3.setImport(false);
			p3.setDescription("Pills for headache");

			p4.setBasePrice(new BigDecimal(11.25));
			p4.setCategory(Category.FOOD);
			p4.setName("Box of Chocolate");
			p4.setImport(true);
			p4.setDescription("Intense, dark chocolate");
			return Lists.newArrayList(p1, p2, p3, p4);
		default:
			return products;
		}
	}

}
