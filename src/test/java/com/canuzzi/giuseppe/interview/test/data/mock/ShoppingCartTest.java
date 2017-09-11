package com.canuzzi.giuseppe.interview.test.data.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.canuzzi.giuseppe.interview.dal.ShoppingCart;
import com.canuzzi.giuseppe.interview.domain.entity.Good;
import com.google.common.collect.Lists;

public class ShoppingCartTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getAllProducts_ThreeElementsReturned() {
		
		//Setup
		Good product1 = new Good();
		Good product2 = new Good();
		Good product3 = new Good();
		
		List<Good> listOfStartingProduct = Lists.newArrayList(product1, product2, product3);
		
		ShoppingCart sc = new ShoppingCart(listOfStartingProduct);
		
		
		//Exercise
		List<Good> productInsideShoppingCart = sc.getAllProducts();
		
		//Verify
		assertThat(productInsideShoppingCart).isNotNull();
		assertThat(productInsideShoppingCart).isNotEmpty();
		assertThat(productInsideShoppingCart.size()).isEqualTo(3);
	}
	
	@Test
	public void getAllProducts_ZeroElementReturned() {
		// Setup

		ShoppingCart sc = new ShoppingCart();


		// Exercise
		List<Good> productInsideShoppingCart = sc.getAllProducts();

		// Verify
		assertThat(productInsideShoppingCart).isNotNull();
		assertThat(productInsideShoppingCart).isEmpty();
	}
	
	@Test
	public void init_NullInputLists_ExceptionRaised() {
		// Setup

		List<Good> products = null;

		// Verify
		assertThatThrownBy(
						// Exercise
							() -> {
									ShoppingCart sc = new ShoppingCart(products);
								  }
							
						).isInstanceOf(NullPointerException.class)
						   .hasMessage("Trying to add null products during initialization");
	}
	@Test
	public void addProduct_OneProductAdded() {

		//Setup
		Good product1 = new Good();
		
		
		ShoppingCart sc = new ShoppingCart();
	
		
		//Exercise
		boolean added = sc.addProduct(product1);
		
		//Verify
		assertThat(added).isEqualTo(true);
		assertThat(sc.getAllProducts()).isNotEmpty();
		assertThat(sc.getAllProducts().size()).isEqualTo(1);
	}
	
	@Test
	public void addProduct_NullInputValue_ExceptionRaised() {

		//Setup
		
		ShoppingCart sc = new ShoppingCart();
		Good p = null;
		
		//Verify
		assertThatThrownBy(
				//Exercise
				() -> sc.addProduct(p)).isInstanceOf(NullPointerException.class)
									   .hasMessage("Trying to add null product");
	}
	
	@Test
	public void addProductList_ProductListAdded() {

		// Setup
		Good product1 = new Good();
		Good product2 = new Good();
		Good product3 = new Good();

		List<Good> listOfProducts = Lists.newArrayList(product1, product2, product3);

		ShoppingCart sc = new ShoppingCart();

		// Exercise
		boolean added = sc.addProductList(listOfProducts);

		// Verify
		
		assertThat(added).isEqualTo(true);
		assertThat(sc.getAllProducts()).isNotNull();
		assertThat(sc.getAllProducts().size()).isEqualTo(3);

	}
	
	@Test
	public void addProductList_NullInputList_ExceptionRaised() {

		//Setup
		
		ShoppingCart sc = new ShoppingCart();
		List<Good> products = null;
		
		//Verify
		assertThatThrownBy(
				//Exercise
				() -> sc.addProductList(products)).isInstanceOf(NullPointerException.class)
									   .hasMessage("Trying to add null products");
	}

}
