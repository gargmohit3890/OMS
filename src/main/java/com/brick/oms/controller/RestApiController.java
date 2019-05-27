package com.brick.oms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brick.oms.vo.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/customers")
public class RestApiController {
	/**
	 * This map is used to store placed orders and acts as database. We can replace
	 * this map with database implementation to store the placed orders.
	 */
	private static Map<UUID, Order> placedOrders = new HashMap<>();

	/**
	 * This api is used to place an order for a customer.
	 * 
	 * @param order
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/orders")
	public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) throws IOException {
		UUID order_reference = UUID.randomUUID();
		order.setOrder_reference(order_reference);
		placedOrders.put(order_reference, order);
		Order order1 = new Order();
		order1.setOrder_reference(order_reference);
		return new ResponseEntity<>(order1, HttpStatus.CREATED);
	}

	/**
	 * This api is used to get the details of an order from order_reference.
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/orders/{id}")
	public ResponseEntity<Order> findById(@PathVariable UUID id) throws JsonProcessingException {

		Order order = placedOrders.get(id);
		if (order != null) {
			new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(order);
	}

	/**
	 * This api is used to get details of all the placed orders
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> findByAllOrders() throws JsonProcessingException {

		List<Order> orders = placedOrders.values().stream().collect(Collectors.toList());
		if (orders.size() == 0) {

			ResponseEntity.ok(null);
		}

		return ResponseEntity.ok(orders);

	}
}
