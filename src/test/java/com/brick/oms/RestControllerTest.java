package com.brick.oms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.ResponseEntity;

import com.brick.oms.controller.RestApiController;
import com.brick.oms.vo.Order;

/**
 * Unit test for simple App.
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerTest
{
	
	/**
	 * test case for creating an order
	 * @throws Exception
	 */
	@Test
	public void createOrderForAcustomer_1() throws Exception {
		
		Order order = new Order();
		order.setNumber_of_bricks(5);
		RestApiController restApiController = new RestApiController();
		ResponseEntity<Order> response = restApiController.createOrder(order);
		
		assertTrue(response.getBody().getOrder_reference() != null);

	}
	
	/**
	 * test case to find the craeted order
	 * @throws Exception
	 */
	@Test
	public void find_2() throws Exception {
		
		Order order = new Order();
		order.setNumber_of_bricks(5);
		RestApiController restApiController = new RestApiController();
		ResponseEntity<Order> response = restApiController.createOrder(order);
		Order obj = response.getBody();
		UUID uuid = obj.getOrder_reference();
		
		ResponseEntity<Order> responseOfGet = restApiController.findById(uuid);
		assertEquals(responseOfGet.getBody().getNumber_of_bricks(), 5);

	}
	
	/**
	 * test case to find all the orders.
	 * @throws Exception
	 */
	@Test
	public void find_3() throws Exception {
		RestApiController restApiController = new RestApiController();
		Order order = new Order();
		order.setNumber_of_bricks(5);
		restApiController.createOrder(order);
		Order order1 = new Order();
		order1.setNumber_of_bricks(2);
		restApiController.createOrder(order1);
	
		ResponseEntity<List<Order>> responseOfGet = restApiController.findByAllOrders();
		
		List<Order> jsonArray = responseOfGet.getBody();
		assertEquals(jsonArray.size(), 4);

	}
	
	/**
	 * test case to find the order with invalid order_reference
	 * @throws Exception
	 */
	@Test
	public void find_4() throws Exception {
		RestApiController restApiController = new RestApiController();
		ResponseEntity<Order> responseOfGet = restApiController.findById(UUID.fromString("844006cd-60c8-4fe3-b498-23ae6d8b75e6"));
		Order order = responseOfGet.getBody();
		assertEquals(order, null);

	}
	
	/**
	 * test case to find all the orders without placing an order
	 * @throws Exception
	 */
	@Test
	public void a1_find() throws Exception {
		RestApiController restApiController = new RestApiController();
		ResponseEntity<List<Order>> responseOfGet = restApiController.findByAllOrders();
		List<Order> jsonArray = responseOfGet.getBody();
		assertEquals(jsonArray.size(), 0);

	}
	
	/**
	 * test case to update number of bricks for an invalid order reference.
	 * @throws Exception
	 */
	@Test
	public void put_5() throws Exception {
		RestApiController restApiController = new RestApiController();
		Order order = new Order();
		order.setOrder_reference(UUID.fromString("844006cd-60c8-4fe3-b498-23ae6d8b75e6"));
		order.setNumber_of_bricks(5);
		ResponseEntity<Order> responseOfGet = restApiController.update(order);
		Order order1 = responseOfGet.getBody();
		assertEquals(order1, null);

	}

	/**
	 * This method is used to update the number of bricks for valid order reference
	 * @throws Exception
	 */
	@Test
	public void put_6() throws Exception {
		RestApiController restApiController = new RestApiController();
		Order order = new Order();
		order.setNumber_of_bricks(5);
		ResponseEntity<Order> response = restApiController.createOrder(order);
		Order obj = response.getBody();
		UUID uuid = obj.getOrder_reference();
		order.setOrder_reference(uuid);
		order.setNumber_of_bricks(10);
		restApiController.update( order);
		ResponseEntity<Order> responseOfGet = restApiController.findById(uuid);
		System.out.println(responseOfGet.toString());
		//assertEquals(responseOfGet.getBody().getNumber_of_bricks(), 10);

	}
	

}
