package com.cms.model;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {
	private double price;
	private String currency;
	private String method;
	private String intent;
	private String description;

}
