package com.brick.oms.vo;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Order {
	
	private UUID order_reference;
	private int number_of_bricks;
	
	public UUID getOrder_reference() {
		return order_reference;
	}
	public void setOrder_reference(UUID order_reference) {
		this.order_reference = order_reference;
	}
	public int getNumber_of_bricks() {
		return number_of_bricks;
	}
	public void setNumber_of_bricks(int number_of_bricks) {
		this.number_of_bricks = number_of_bricks;
	}
}
